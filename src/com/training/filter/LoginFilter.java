package com.training.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.training.common.config.Config;
import com.training.data.UserData;
import com.training.service.UserService;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();

		System.out.println("uri:" + uri);
		String excludeUri = Config.getStringProperty("login.exclude.uri");
		String[] excludeUriArray = StringUtils.split(excludeUri, ",");

		if (null == excludeUriArray) {
			// redirect
			//req.getRequestDispatcher("login").forward(req, resp);
			//说明都需要过滤
			
			
			//这里要加逻辑
			//1.判断sesison
			//这里的代码重复了,

			HttpSession session = req.getSession();
			UserData data = (UserData) session.getAttribute("userData");
			if (data == null) {
				
				//redirect 不要forward
				resp.sendRedirect("login");
			}else{
				chain.doFilter(req, resp);
			}
			
		} else {

			boolean access = false;
			for (String exuri : excludeUriArray) {
				if (uri.contains(exuri)) {
					access = true;
					break;
				}
			}

			if (access) {
				chain.doFilter(req, resp);
			} else {
				HttpSession session = req.getSession();
				UserData data = (UserData) session.getAttribute("userData");
				if (data == null) {
					
					//redirect 不要forward
					resp.sendRedirect("login");
				}else{
					chain.doFilter(req, resp);
				}
			}

		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
