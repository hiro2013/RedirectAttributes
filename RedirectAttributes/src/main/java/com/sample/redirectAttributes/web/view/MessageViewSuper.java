package com.sample.redirectAttributes.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mixer2.jaxb.xhtml.Div;
import org.mixer2.jaxb.xhtml.H1;
import org.mixer2.jaxb.xhtml.H3;
import org.mixer2.jaxb.xhtml.Html;
import org.mixer2.jaxb.xhtml.P;
import org.springframework.context.annotation.Scope;

/**
 * 【PC,SP,MB共通】
 * message.htmlを処理するための基本処理
 *
 */
@Scope("session")
public abstract class MessageViewSuper extends AbstractView {

	@Override
	protected Html rewriteContent(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {

		// 開始トレース
		System.out.println("start");

		try {
			// TODO RedirectAttributesから値を取得したい
			//			RedirectAttributes ｒedirectAttributes；
			//			Map<String, String> attributes = (Map<String, String>) redirectAttributes.getFlashAttributes();
			//			String title = attributes.get("title");
			//			String message = attributes.get("title");

			String title = request.getParameter("title");
			String message = request.getParameter("message");

			// デバッグ
			System.out.println("title=" + title);
			System.out.println("message=" + message);

			if (title == null) {
				// 終了トレース
				System.out.println("return");
				return error("タイトルがありません");
			}

			if (message == null) {
				// 終了トレース
				System.out.println("return");
				return error("メッセージがありません");
			}

			return html;
		} catch (Exception e) {
			setExceptionMessage(html, e);
			return error(e.getMessage());
		} finally {
			// 終了トレース
			System.out.println("return");
		}

	}

	public void setInfoMessage(Html html, String message, String[] messageDetail) {

		setMessage(html, message, messageDetail);
	}

	public void setWarnMessage(Html html, String message, String[] messageDetail) {

		setMessage(html, message, messageDetail);
	}

	public void setErrorMessage(Html html, String message, String[] messageDetail) {

		setMessage(html, message, messageDetail);
	}

	public void setFatalMessage(Html html, String message, String[] messageDetail) {

		setMessage(html, message, messageDetail);
	}

	public void setMessage(Html html, String message, String[] messageDetail) {

		// 開始トレース
		System.out.println("start");

		try {
			// messageTitle 設定
			H1 messageTitle = html.getById("messageTitle");
			messageTitle.getContent().clear();
			messageTitle.getContent().add(message);

			// messageTitle 設定
			Div replaceContent = html.getById("replaceContent");
			replaceContent.getContent().clear();

			P detail = new P();
			detail.setId("detail");
			detail.setStyle("white-space: pre-line;" + "padding-left: 50px;" + "font-size: 80%;");
			if (messageDetail != null) {
				for (String msg : messageDetail) {
					detail.getContent().add(msg + "\n");
				}
			}

			replaceContent.getContent().add(detail);

		} catch (Exception e) {
			e.printStackTrace();
			setExceptionMessage(html, e);
			// 終了トレース
			System.out.println("return");
			return;
		} finally {
			// 終了トレース
			System.out.println("return");
		}
	}

	public void setExceptionMessage(Html html, Exception ex) {

		// 開始トレース
		System.out.println("start");

		try {
			// messageTitle 設定
			H1 messageTitle = html.getById("messageTitle");
			messageTitle.getContent().clear();

			StackTraceElement[] elements;
			if (ex != null) {
				if (ex.getMessage() != null) {
					messageTitle.getContent().add(ex.getMessage());
				} else {
					messageTitle.getContent().add("Systemエラーが発生しました");
				}
				elements = ex.getStackTrace();
			} else {
				elements = Thread.currentThread().getStackTrace();
			}

			// messageTitle 設定
			Div replaceContent = html.getById("replaceContent");
			replaceContent.getContent().clear();

			H3 stackTraceTitle = new H3();
			stackTraceTitle.getContent().add("【Stack Trace】");
			replaceContent.getContent().add(stackTraceTitle);

			P stackTrace = new P();
			stackTrace.setStyle("white-space: pre-line;" + "padding-left: 50px;" + "font-size: 80%;");
			for (StackTraceElement msg : elements) {
				stackTrace.getContent().add(msg.toString() + "\n");
			}
			replaceContent.getContent().add(stackTrace);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			// 終了トレース
			System.out.println("return");
		}

	}

}
