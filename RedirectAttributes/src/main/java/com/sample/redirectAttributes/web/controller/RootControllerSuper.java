package com.sample.redirectAttributes.web.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@Controller
@Scope("session")
public abstract class RootControllerSuper extends ControllerSuper {

	private String basePath = "";

	/**
	 * /index を呼ばれた時の独自処理
	 * @param locale
	 * @param model
	 * @param principal
	 * @param viewname
	 * @return HttpStatus.CONTINUE以外はエラー処理を行う
	 */
	public abstract HttpStatus indexImplements(Locale locale, Model model, Principal principal, String viewname);

	@RequestMapping(value = { "/",
			"/JP/", "/US/", "/CN/", "/BR/", "/PH/"
	}, method = RequestMethod.GET)
	public String root(@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "lang", defaultValue = "") String lang, Locale locale,
			Model model, Principal principal,
			HttpServletRequest req, HttpServletResponse res) {

		System.out.println("##### RootControllerSuper->root");
		return index(country, lang, locale, model, principal, req, res);
	}

	@RequestMapping(value = { "/index",
			"/JP/index",
			"/US/index",
			"/CN/index",
			"/BR/index",
			"/PH/index"
	}, method = RequestMethod.GET)
	public String index(@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "lang", defaultValue = "") String lang, Locale locale,
			Model model, Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		// 開始トレース
		System.out.println("start");

		// コントローラーの初期化（多言語化等の設定を行う）
		String path = basePath;

		/* pathとviewnameの設定は必須 */
		String viewname = "index";

		model.addAttribute("path", path);
		model.addAttribute("viewname", viewname);

		try {
			HttpStatus status = indexImplements(locale, model, principal, viewname);
			if (status == null || !status.equals(HttpStatus.CONTINUE)) {
				// 派生クラスでエラー検出
				// redirect:/messageが返る   ※エラー画面(/message.html)に遷移する
				return errorMessageView(locale, request, "エラー", "ああああ");
			}
			// デバッグ
			System.out.println("Language = " + locale.getLanguage()
					+ " path = " + path
					+ " viewname = " + viewname);

			String view = path + viewname;
			System.out.println("##### RootControllerSuper->index return view=" + view);
			return view;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// 終了トレース
			System.out.println("return");
		}

	}

}
