package com.sample.redirectAttributes.web.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Scope("session")
public abstract class MessageControllerSuper extends ControllerSuper {

	private final String basePath = "";

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	public String index(Locale locale, Principal principal) {
		System.out.println("##### MessageControllerSuper->index");

		/* pathとviewnameの設定は必須 */
		String path = basePath;
		String viewname = "message";

		String view = path + viewname;
		System.out.println(
				"##### MessageControllerSuper->index return view=" + view);
		return view;
	}

}
