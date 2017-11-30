package bulletin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletin.beans.Branches;
import bulletin.beans.Departments;
import bulletin.beans.User;
import bulletin.service.BranchService;
import bulletin.service.DepartmentService;
import bulletin.service.UserService;

@WebServlet(urlPatterns = { "/userManagement" })
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException {

		List<User> users = new UserService().getUsers();
		List<Branches>branches = new BranchService().getBranches();
		List<Departments>departments = new DepartmentService().getDepartments();


		request.setAttribute("users",users);
		request.setAttribute("branches",branches);
		request.setAttribute("departments",departments);

		System.out.println(request.getParameter("branches"));

		request.getRequestDispatcher("userManagement.jsp").forward(request,response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException {

	}
}


