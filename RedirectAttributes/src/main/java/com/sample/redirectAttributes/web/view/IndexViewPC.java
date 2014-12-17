package com.sample.redirectAttributes.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mixer2.jaxb.xhtml.Html;

/**
 * 【PC用】
 * index.htmlを処理するための基本処理
 *
 */
public abstract class IndexViewPC extends IndexViewSuper {

	@Override
	protected Html rewriteContentModel(Html html, Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		return html;
	}

}
