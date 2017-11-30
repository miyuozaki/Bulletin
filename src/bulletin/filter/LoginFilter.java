package bulletin.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletin.beans.User;

@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String requestPath = ((HttpServletRequest)request).getServletPath();


		if(!requestPath.equals("/login") && !requestPath.matches(".*css.*")){

			HttpSession session = ((HttpServletRequest)request).getSession();

			User user = (User)session.getAttribute("loginUser");

			if(user == null){

				List<String> messages = new ArrayList<String>();
				messages.add("ログインして下さい");
				session.setAttribute("errorMessages", messages);

				 ((HttpServletResponse)response).sendRedirect("./login");
				return;
			}

		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void destroy() {

	}



}
