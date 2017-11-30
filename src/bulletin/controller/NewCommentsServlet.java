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

import bulletin.beans.Comments;
import bulletin.beans.User;
import bulletin.service.CommentsService;

@WebServlet(urlPatterns = { "/newComments" })
public class NewCommentsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws IOException, ServletException{


		request.getRequestDispatcher("top.jsp").forward(request,response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> comment = new ArrayList<String>();

		if (isValid(request, comment) == true) {

			User user = (User) session.getAttribute("loginUser");

			Comments comments = new Comments();

			comments.setText(request.getParameter("text"));
			comments.setUser_id(user.getId());
			comments.setPost_id(Integer.parseInt(request.getParameter("post_id")));

			new CommentsService().register(comments);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", comment);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String comment = request.getParameter("text");

		if (StringUtils.isEmpty(comment) == true) {
			comments.add("コメントを入力してください");
		}
		if (500 < comment.length()) {
			comments.add("500文字以下で入力してください");
		}
		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
