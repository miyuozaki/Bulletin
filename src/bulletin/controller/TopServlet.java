package bulletin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.UserComments;
import bulletin.beans.UserPosts;
import bulletin.service.CommentsService;
import bulletin.service.PostsService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException {

		Date date = new Date();

		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		String category = request.getParameter("category");
		request.setAttribute("startDate",start);
		request.setAttribute("endDate",end);
		request.setAttribute("category",category);


		if(start == null || start.isEmpty()){
			start = "2017-11-01";
		}
		if(end == null || end.isEmpty()){
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			end = sDateFormat.format(date);
		}
		if(category == null || category.isEmpty()){

			List<UserPosts> posts = new PostsService().getPosts(start,end,category);
			List<UserComments>comments = new CommentsService().getComments();

			request.setAttribute("posts", posts);
			request.setAttribute("comments", comments);

			request.getRequestDispatcher("/top.jsp").forward(request, response);

		}else{

		List<UserPosts> posts = new PostsService().getPosts(start,end,category);
		List<UserComments>comments = new CommentsService().getComments();

		request.setAttribute("posts", posts);
		request.setAttribute("comments", comments);

		request.getRequestDispatcher("/top.jsp").forward(request, response);

		}

	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {



	}


	@SuppressWarnings("unused")
	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String comment = request.getParameter("text");

		if (StringUtils.isEmpty(comment) == true) {
			messages.add("コメントを入力してください");
		}
		if (500 < comment.length()) {
			messages.add("500文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

