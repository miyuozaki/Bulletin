package bulletin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.Posts;
import bulletin.beans.User;
import bulletin.service.PostsService;

@WebServlet(urlPatterns = { "/newPosts" })
public class NewPostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{

		request.getRequestDispatcher("newposts.jsp").forward(request,response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

			HttpSession session = request.getSession();
			List<String> posts = new ArrayList<String>();

			User user = (User) session.getAttribute("loginUser");

			Posts post = new Posts();
			post.setText(request.getParameter("text"));
			post.setUser_id(user.getId());
			post.setSubject(request.getParameter("subject"));
			post.setCategory(request.getParameter("category"));

			if (isValid(request,posts) == true) {
				new PostsService().register(post);
				response.sendRedirect("./");
			} else {
				session.setAttribute("errorMessages", posts);
				request.setAttribute("post", post);

				request.getRequestDispatcher("newposts.jsp").forward(request,response);
			}
		}

	private boolean isValid(HttpServletRequest request, List<String>posts )
			throws IOException, ServletException{

		String category = request.getParameter("category");
		if(StringUtils.isEmpty(category)){
			posts.add("カテゴリーを入力してください");
		}
		if(10 < category.length()){
			posts.add("カテゴリーは10文字以下で入力してください");
		}

		String subject = request.getParameter("subject");
		if(StringUtils.isEmpty(subject)){
			posts.add("件名を入力してください");
		}
		if(30 < subject.length()){
			posts.add("件名は30文字以下で入力してください");
		}

		String text = request.getParameter("text");
		if (StringUtils.isEmpty(text)) {
			posts.add("本文を入力してください");
		}
		if (1000 < text.length()) {
			posts.add("本文は1000文字以下で入力してください");
		}
		if (posts.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
