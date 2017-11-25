package com.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.training.common.config.Config;

@Controller
public class ControllerTest {

	@RequestMapping("/test")
	@ResponseBody
	public String test() {
		String value = Config.getStringProperty("training.test.chinese");
		System.out.println("value:" + value);
		return "test success...";
	}
}
