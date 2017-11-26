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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

		boolean hasLogin = true;
		if (!(uri.contains("/login") || uri.contains("/js") || uri.contains("/logout")||uri.contains("/verifyCode")
				|| uri.contains("/js")))
		{
			HttpSession session = req.getSession();
			UserData user = (UserData) session.getAttribute("userData");
			if (user == null)
			{
				Cookie[] cookie = req.getCookies();
				Cookie rememberMe = null;
				for (Cookie c : cookie)
				{
					if ("rememberMe".equals(c.getName()))
					{
						rememberMe = c;
						break;
					}
				}

				UserData userData = null;
				if (rememberMe != null)
				{
					String token = rememberMe.getValue();
					String[] str = token.split("\\(\\-\\)");
					String name = str[0];
					String password = str[1];
					WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
					UserService userService = ctx.getBean(UserService.class);
					userData = userService.queryUserByNameAndPassword(name, password);
				}

				if (userData == null)
				{
					hasLogin = false;
				}
				else
				{
					session.setAttribute("userModel", userData);
				}
			}

		}

		if (hasLogin)
		{
			chain.doFilter(req, resp);
		}
		else
		{
			resp.sendRedirect("login");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
