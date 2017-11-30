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

@WebFilter(urlPatterns = {"/userManagement","/UserSettings","/signup"})
public class UserFilter implements Filter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest)request).getSession();
		User user = (User)session.getAttribute("loginUser");

		if(user.getDepartment_id() != 1){

			List<String> messages = new ArrayList<String>();
			messages.add("アクセス権限がありません");
			session.setAttribute("errorMessages", messages);

			((HttpServletResponse)response).sendRedirect("./index.jsp");
			return;
		}

		chain.doFilter(request, response); // サーブレットを実行
	}

	@Override
	public void destroy() {
	}
	@Override
	public void init(FilterConfig confing) throws ServletException {
	}

}
