package com.sample.redirectAttributes.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mixer2.jaxb.xhtml.Html;

/**
 * index.htmlを処理するための基本処理
 *
 */
public class IndexView extends IndexViewPC {

	@Override
	protected Html rewriteContentModel(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		return html;
	}

}
