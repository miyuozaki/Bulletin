package bulletin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletin.service.CommentsService;
import bulletin.service.PostsService;


@WebServlet(urlPatterns = { "/delete" })
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		new PostsService().deletePosts(request.getParameter("id"));
		new CommentsService().deleteComments(request.getParameter("id"));

		response.sendRedirect("./");

	}

}
