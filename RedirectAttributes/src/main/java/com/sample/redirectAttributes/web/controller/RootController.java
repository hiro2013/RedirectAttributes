package com.sample.redirectAttributes.web.controller;

import java.security.Principal;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class RootController extends RootControllerSuper {

	@Override
	public HttpStatus indexImplements(Locale cLocale, Model cModel, Principal cPrincipal, String cViewname) {
		// return HttpStatus.CONTINUE; // index.html
		return HttpStatus.BAD_REQUEST; // Error発生
	}

}
