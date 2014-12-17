package com.sample.redirectAttributes.web.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mixer2.Mixer2Engine;
import org.mixer2.jaxb.xhtml.A;
import org.mixer2.jaxb.xhtml.Address;
import org.mixer2.jaxb.xhtml.Applet;
import org.mixer2.jaxb.xhtml.Area;
import org.mixer2.jaxb.xhtml.B;
import org.mixer2.jaxb.xhtml.Base;
import org.mixer2.jaxb.xhtml.Basefont;
import org.mixer2.jaxb.xhtml.Body;
import org.mixer2.jaxb.xhtml.Br;
import org.mixer2.jaxb.xhtml.ButtonContent;
import org.mixer2.jaxb.xhtml.Col;
import org.mixer2.jaxb.xhtml.Colgroup;
import org.mixer2.jaxb.xhtml.Div;
import org.mixer2.jaxb.xhtml.H1;
import org.mixer2.jaxb.xhtml.H2;
import org.mixer2.jaxb.xhtml.H3;
import org.mixer2.jaxb.xhtml.H4;
import org.mixer2.jaxb.xhtml.H5;
import org.mixer2.jaxb.xhtml.H6;
import org.mixer2.jaxb.xhtml.Head;
import org.mixer2.jaxb.xhtml.Hr;
import org.mixer2.jaxb.xhtml.Html;
import org.mixer2.jaxb.xhtml.Img;
import org.mixer2.jaxb.xhtml.Inline;
import org.mixer2.jaxb.xhtml.Input;
import org.mixer2.jaxb.xhtml.Li;
import org.mixer2.jaxb.xhtml.Link;
import org.mixer2.jaxb.xhtml.Ol;
import org.mixer2.jaxb.xhtml.Option;
import org.mixer2.jaxb.xhtml.P;
import org.mixer2.jaxb.xhtml.Script;
import org.mixer2.jaxb.xhtml.Select;
import org.mixer2.jaxb.xhtml.Span;
import org.mixer2.jaxb.xhtml.Table;
import org.mixer2.jaxb.xhtml.Tbody;
import org.mixer2.jaxb.xhtml.Td;
import org.mixer2.jaxb.xhtml.Textarea;
import org.mixer2.jaxb.xhtml.Tfoot;
import org.mixer2.jaxb.xhtml.Thead;
import org.mixer2.jaxb.xhtml.Title;
import org.mixer2.jaxb.xhtml.Tr;
import org.mixer2.jaxb.xhtml.Ul;
import org.mixer2.spring.webmvc.AbstractMixer2XhtmlView;
import org.mixer2.xhtml.AbstractJaxb;
import org.mixer2.xhtml.PathAdjuster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

/**
 * テンプレートを使ってHTMLを生成するための基本クラス<br/>
 *
 * ＜考え方＞ <br/>
 * １．全体の構造はテンプレートの構造を採用する <br/>
 * ２．HEADタグの内容はTITLEのみ変更可能。<br/>
 * ※適用するCSSやJSを変更する場合はテンプレートを増やす。（SEO向けにMETAタグをどうする？）<br/>
 * ３．BODYタグにはpage,header,content,footerがある。 ※pageは1ファイルにつき、ひとつとする<br/>
 * ４．ヘッダーコンテンツはソースの内容を使用する <br/>
 * ５．メインコンテンツはソースの内容を使用する<br/>
 * ６．フッターコンテンツはテンプレートの内容を使用する<br/>
 * ７．言語コードに対応するファイル（言語ファイル）が存在する場合はそのファイルで置換する。<br/>
 * ８．言語ファイルのファイル名はファイル名_言語コード.htmlとする。<br/>
 * ９．言語コードはセッション情報で管理する。初回アクセス時等、セッション情報が存在しない場合はブラウザの言語コードを採用する。<br/>
 *
 * 細かい調整は派生クラスに任せる
 *
 */
public abstract class AbstractView extends AbstractMixer2XhtmlView {

	private final String dafaultTemplatePath = "m2mockup/m2template/";

	private final String templatePath = null;

	@Autowired
	protected Mixer2Engine mixer2Engine;

	@Autowired
	protected ResourceLoader resourceLoader;

	public enum Replace {
		ALL, /* 子孫のタグも含めて完全に置き換える */
		MeOnly, /* 自分自身のタグだけを置き換える */
		MySelf /* 自分自身のタグを消し去って内包されたタグに置き換える */
		/*
		 * 例） <div> <div id="a"> xxxx </div> </div>
		 *
		 * ↓ tagName=div id=a deleteTag=true
		 *
		 * <div> yyy </div>
		 */
	}

	protected class ListObject {

		// protected class ListObject extends AbstractJaxb {
		public String id;
		public Class<?> tagClass;
		public Replace replace;

		/**
		 * コンストラクタ
		 *
		 * @param tagType
		 *            変更対象のクラス（AbstractJaxbの派生クラス）
		 * @param id
		 *            変更するCSS ID
		 * @param replace
		 *            置換方法
		 */
		public ListObject(Class<?> tagClass, String id, Replace replace) {

			// public ListObject(String tagName, String id,
			// Class<AbstractJaxb> tagType, Replace replace) {
			this.id = id;
			this.tagClass = tagClass;
			this.replace = replace;
		}

	}

	protected List<ListObject> replaceID = new ArrayList<ListObject>(); // 置き換えるIDの配列

	/**
	 * 派生クラスでHTMLを書き換えるためのメソッド
	 *
	 * @param html
	 * @param model
	 * @param request
	 * @param response
	 */
	abstract protected Html rewriteContent(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response);

	@Override
	protected Html renderHtml(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 開始トレース
		System.out.println("start");

		// 本体
		try {
			System.out.println("##### AbstractView.renderHtml Start\n" + htmlToString(html));

			/* 中身のないDIVタグをそのまま出力させるために空白を追加 */
			for (Div tag : html.getBody().getDescendants(Div.class)) {
				if (tag.getContent().size() == 0) {
					tag.getContent().add(" "); // 空白ひとつ追加
				}
			}

			// 派生クラスでHTMLを書き換えるためのメソッド呼び出し
			Html rewritedHtml = rewriteContent(html, model, request, response);

			/* パス書き換え */
			this.pathAdjuster(rewritedHtml);

			System.out.println(htmlToString(rewritedHtml));
			System.out.println("##### AbstractView.renderHtml End\n" + htmlToString(rewritedHtml));
			return rewritedHtml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 終了トレース
			System.out.println("return");
		}
	}

	/**
	 * パス書き換え
	 * ※m2static、m2templateが異なる時は派生クラスでこのメソッドをオーバーライドする
	 *
	 * @param html
	 *            書き換えるHTMLオブジェクト
	 */
	protected void pathAdjuster(Html html) {

		System.out.println("##### AbstractView.pathAdjuster Start");
		pathAdjuster(html, null, "m2static", "/m2static");
		pathAdjuster(html, null, "m2template", "");
		System.out.println("##### AbstractView.pathAdjuster End");
	}

	/**
	 * パス書き換え
	 *
	 * @param html
	 *            書き換えるHTMLオブジェクト
	 * @param regexp
	 *            変更するための正規表現（NULLの時はシステムデフォルトを使用）
	 * @param src
	 *            変更前文字列
	 * @param dst
	 *            変更後文字列
	 */
	protected void pathAdjuster(Html html, String regexp, String src, String dst) {

		String reg = regexp;
		if ((reg == null) || reg.isEmpty()) {
			// reg = "^(\\.[\\./])+/m2static/(.*)$";
			// reg = "^(\\.[\\./])+/" + src + "/(.*)$";
			// reg = "^m|.*/(.*)/" + src + "/(.*)$";
			// reg = "(\\.\\./)+/" + src + "/(.*)$";
			// reg = "(\\.\\/)+" + src + "/(.*)$";
			reg = "^\\.+/.*" + src + "/(.*)$";

			// Pattern.compile("^(\\.[\\./])+/m2static/(.*)$");
			// Pattern.compile("^(\\.[\\./])+/m2temple/(.*)$");
		}
		/* コンテキストパス取得 */
		//String ctx = RequestUtil.getContextPath();
		String ctx = ""; // 本来はコンテキストパスを含めるのが正しいと思われるが実際の運用ではかえって困るので何も設定しない
		System.out.println("コンテキストパス=" + ctx + " reg=" + reg);
		System.out.println("src=" + src + " dst=" + dst);

		Pattern pattern = Pattern.compile(reg);
		PathAdjuster.replacePath(html, pattern, ctx + dst + "/$1");
		// PathAdjuster.replacePath(html, pattern, ctx + "/m2static/$2");
		// PathAdjuster.replacePath(html, pattern, ctx + "/$2");
	}

	/*
	 * View生成時の予期しない障害用のエラー画面作成 テンプレートを読み込めない可能性を考慮し、すべてのHTMLコードをこのメソッドで生成する。
	 */
	protected Html error(String message) {

		Html html = new Html();
		html.setLangCode("UTF-8");
		html.setLang("ja");
		Head head = new Head();
		Title title = new Title();
		title.setContent("Error");
		head.getContent().add(title);
		html.setHead(head);
		Body body = new Body();
		H1 h1 = new H1();
		h1.getContent().add("Error");
		body.getContent().add(h1);
		for (String msg : message.split("\n")) {
			// System.out.println(msg);
			P p = new P();
			p.getContent().add(msg);
			body.getContent().add(p);
		}
		html.setBody(body);
		return html;
	}

	protected static AbstractJaxb cast(final java.lang.Object template) {

		if (template instanceof A) {
			return (A) template;
		} else if (template instanceof Address) {
			return (Address) template;
		} else if (template instanceof Applet) {
			return (Applet) template;
		} else if (template instanceof Area) {
			return (Area) template;
		} else if (template instanceof Base) {
			return (Base) template;
		} else if (template instanceof Basefont) {
			return (Basefont) template;
		} else if (template instanceof Br) {
			return (Br) template;
		} else if (template instanceof ButtonContent) {
			return (ButtonContent) template;
		} else if (template instanceof Col) {
			return (Col) template;
		} else if (template instanceof Colgroup) {
			return (Colgroup) template;
		} else if (template instanceof Ul) {
			return (Ul) template;
		} else if (template instanceof Ol) {
			return (Ol) template;
		} else if (template instanceof Li) {
			return (Li) template;
		} else if (template instanceof Option) {
			return (Option) template;
		} else if (template instanceof Table) {
			return (Table) template;
		} else if (template instanceof Tbody) {
			return (Tbody) template;
		} else if (template instanceof Thead) {
			return (Thead) template;
		} else if (template instanceof Tfoot) {
			return (Tfoot) template;
		} else if (template instanceof Tr) {
			return (Tr) template;
		} else if (template instanceof Td) {
			return (Td) template;
		} else if (template instanceof Select) {
			return (Select) template;
		} else if (template instanceof Textarea) {
			return (Textarea) template;
		} else if (template instanceof Input) {
			return (Input) template;
		} else if (template instanceof Hr) {
			return (Hr) template;
		} else if (template instanceof Link) {
			return (Link) template;
		} else if (template instanceof Script) {
			return (Script) template;
		} else if (template instanceof Img) {
			return (Img) template;
		} else if (template instanceof org.mixer2.jaxb.xhtml.Map) {
			return (org.mixer2.jaxb.xhtml.Map) template;
		} else if (template instanceof Inline) {
			return (Inline) template;
		} else if (template instanceof Div) {
			return (Div) template;
		} else if (template instanceof P) {
			return (P) template;
		} else if (template instanceof Span) {
			return (Span) template;
		} else if (template instanceof B) {
			return (B) template;
		} else if (template instanceof H1) {
			return (H1) template;
		} else if (template instanceof H2) {
			return (H2) template;
		} else if (template instanceof H3) {
			return (H3) template;
		} else if (template instanceof H4) {
			return (H4) template;
		} else if (template instanceof H5) {
			return (H5) template;
		} else if (template instanceof H6) {
			return (H6) template;
		}
		System.out.println("★★★ create template.getClass()=" + template.getClass().toString());

		return null;
	}

	/*
	 * 生成されるHtmlドキュメントを取得する
	 */
	protected String htmlToString(AbstractJaxb tag) {

		String doc = mixer2Engine.saveToString(tag);
		return doc;
	}

	/**
	 * HTMLドキュメントの変換を始めるための初期化。 例） setRewriteHead(true);　←
	 * HEADタグはソースのまま（デフォルトはテンプレート） setRewriteHeader(true);　←
	 * BODY(Header)はソースのまま（デフォルトはソースのまま） setRewriteContent(true);←
	 * BODY(Content)はソースのまま（デフォルトはソースのまま） setRewriteFooter(true);　← ←
	 * BODY(Footer)はソースのまま（デフォルトはテンプレート）
	 */
	// protected void initialize() {
	// replaceID = new ArrayList<ListObject>();
	// }

	/**
	 * ソースHTMLのフルパスを取得する
	 *
	 * @return ソースHTMLのフルパス
	 */
	// public abstract String getSourceHtmlPath();

	/*
	 * ページタイトル。 １ソース、２テンプレートの順に設定された値を設定する
	 * TODO 先にconfigから取得するような変更を追加する
	 */
	protected String getTitle() {

		System.out.println("##### setTitle");
		// List<Title> list = masterHtml.getHead().getDescendants(Title.class);
		// if (list != null) {
		// System.out.println( "##### masterHtmlにTITLEタグがあった");
		// for (Title t : list) {
		// System.out.println(
		// "##### masterHtml：TITLEタグ=" + t.getContent());
		// return t.getContent();
		// }
		// }
		System.out.println("##### masterHtmlにはTITLEタグがなかった");
		return null;
	}

	/*
	 * ページタイトル。 １ソース、２テンプレートの順に設定された値を設定する
	 * TODO 先にconfigから取得するような変更を追加する
	 */
	protected void setTitle() {

		System.out.println("##### setTitle");
		// List<Title> list = masterHtml.getHead().getDescendants(Title.class);
		// if (list != null) {
		// System.out.println( "##### masterHtmlにTITLEタグがあった");
		// for (Title t : list) {
		// System.out.println(
		// "##### masterHtml：TITLEタグ=" + t.getContent());
		// return t.getContent();
		// }
		// }
		System.out.println("##### masterHtmlにはTITLEタグがなかった");
	}

	/**
	 * Scriptタグを追加する. （js追加を想定）
	 *
	 * @param html
	 *            対象となるhtml
	 * @param cSrc
	 *            src
	 * @param cType
	 *            　type
	 */
	protected void addScript(Html html, String cSrc, String cType) {

		Script aScript = new Script();

		aScript.setType(cType);
		aScript.setSrc(cSrc);
		aScript.setContent(" ");

		html.getHead().getContent().add(aScript);
	}

	/**
	 * Linkタグを追加する. （css追加を想定）
	 *
	 * @param html
	 * @param cHref
	 * @param cType
	 * @param cRel
	 */
	protected void addLink(Html html, String cHref, String cType, String cRel) {

		Link aLink = new Link();

		aLink.setHref(cHref);
		aLink.setType(cType);
		aLink.getRel().add(cRel);

		html.getHead().getContent().add(aLink);
	}

	/**
	 * @return replaceID
	 */
	public List<ListObject> getReplaceID() {

		return replaceID;
	}

	/**
	 * @param replaceID
	 *            セットする replaceID
	 */
	public void setReplaceID(List<ListObject> replaceID) {

		this.replaceID = replaceID;
	}

}
