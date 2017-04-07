package vn.framgia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeContrller {
	@RequestMapping(value = { "/", "index" })
	public String index() {
		return "index";
	}
}
