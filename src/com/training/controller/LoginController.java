package com.training.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.training.data.UserData;
import com.training.form.UserForm;
import com.training.service.UserService;

@Controller
public class LoginController {
	@Resource
	private Validator validator;
	@Resource
	private UserService userService;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserForm userForm, Model model, BindingResult bindingResult,HttpSession session, HttpServletResponse response) {
		
		validator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("userForm", userForm);
			return "login/login";
		}
		UserData userData = userService.queryUser(userForm);
		if (null == userData) {
			return "login/login";
		}
		session.setAttribute("userData", userData);
		if (userForm.isRememberMe()) {
			Cookie cookie = new Cookie("rememberMe", userForm.getName() + "(-)" + userForm.getPassword());
			cookie.setMaxAge(60 * 60 * 24 * 3);
			response.addCookie(cookie);

		}
		return "redirect:loadStudentsByFields";
	}

	public String logout(Model model, HttpSession session) {
		model.addAttribute("userForm", new UserForm());
		session.invalidate();
		return "login/login";
	}

}
