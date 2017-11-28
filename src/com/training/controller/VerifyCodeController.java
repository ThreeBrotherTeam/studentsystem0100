package com.training.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.training.util.VerifyCodeUtils;

@Controller
public class VerifyCodeController {
	
	@RequestMapping("/verifyCode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		System.out.println(verifyCode);
		request.getSession().setAttribute("verifyCode", verifyCode);
		VerifyCodeUtils.outputImage(140, 50, response.getOutputStream(), verifyCode);
	}
}
