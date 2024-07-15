
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class WebController {
	public static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping("/")
	String home(ModelMap modal) {
		modal.addAttribute("title", "Paynet ");

		return "index";
	}

	@RequestMapping("/partials/{page}")
	String partialHandler(@PathVariable("page") final String page) {
		return page;
	}
	
	@RequestMapping(value = "/Paynet", method = RequestMethod.GET)
	public ModelAndView redirectToPaynet() {
	    return new ModelAndView("redirect:http://www.domain.my" );
	}
	
	

}
