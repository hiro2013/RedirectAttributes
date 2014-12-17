package com.sample.redirectAttributes.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mixer2.jaxb.xhtml.Html;

/**
 * 【PC,SP,MB共通】
 * index.htmlを処理するための基本処理
 *
 */
public abstract class IndexViewSuper extends AbstractView {

	/**
	 * 機種ごとの派生クラスでHTMLを書き換えるためのメソッド。
	 * 共通の書き換えが終わった後で呼び出される。
	 * パスの書き換えは本メソッド終了後に一括で行われる。
	 *
	 * @param html
	 * @param model
	 * @param request
	 * @param response
	 */
	abstract protected Html rewriteContentModel(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response);

	@Override
	protected Html rewriteContent(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		// 開始トレース
		System.out.println("start");

		try {
			return rewriteContentModel(html, model, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 終了トレース
			System.out.println("return");
		}

	}

}
