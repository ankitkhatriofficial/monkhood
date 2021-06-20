package com.monkhood6.controller.access;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {

	@GetMapping("/404")
	public String show404Page() {
		return "access/404.jsp";
	}

	@GetMapping("/500")
	public String show500Page() {
		return "access/500.jsp";
	}

	@GetMapping("/403")
	public String show403Page() {
		return "access/403.jsp";
	}
}
