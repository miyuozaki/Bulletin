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

import bulletin.beans.Branches;
import bulletin.beans.Departments;
import bulletin.beans.User;
import bulletin.service.BranchService;
import bulletin.service.DepartmentService;
import bulletin.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<Branches> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);
		List<Departments> departments = new DepartmentService().getDepartments();
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User user = new User();
		user.setLogin_id(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		String branchId = request.getParameter("branch_id");
		int intBranchId = Integer.parseInt(branchId);
		user.setBranch_id(intBranchId);
		String departmentId = request.getParameter("department_id");
		int intDepartmentId = Integer.parseInt(departmentId);
		user.setDepartment_id(intDepartmentId);

		if (isValid(request, messages) == true) {

			new UserService().register(user);


			response.sendRedirect("http://localhost:8080/Ozaki_miyu/userManagement");
		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("user", user);

			List<Branches> branches = new BranchService().getBranches();
			request.setAttribute("branches", branches);
			List<Departments> departments = new DepartmentService().getDepartments();
			request.setAttribute("departments", departments);

			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String name = request.getParameter("name");

		if(StringUtils.isEmpty(name)){
			messages.add("名前を入力してください");
		}
		if(10 < name.length()){
			messages.add("名前は10文字以下で入力してください");
		}

		String loginId = request.getParameter("login_id");

		User loginUsers = UserService.getOverlap(loginId);

		if(loginUsers != null){
			messages.add("このログインIDはすでに使用されています");
		}
		if(StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		}
		if(!loginId.matches("^[0-9a-zA-Z]{6,20}")){
			messages.add("ログインIDは半角英数字6～20文字以下で入力してください");
		}

		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");

		if(!password.equals(passwordConfirm)){
			messages.add("パスワードと確認用パスワードが一致しません");
		}
		if(StringUtils.isEmpty(password)) {
			messages.add("パスワードを入力してください");

		}else if(!password.matches("^[-_@+*;:#$%!&A-Za-z0-9]{6,20}$")){
			messages.add("パスワードは記号含む半角英数字6～20文字以下で入力してください");
		}

		String branchId = request.getParameter("branch_id");
		int intBranchId = Integer.parseInt(branchId);
		String departmentId = request.getParameter("department_id");
		int intDepartmentId = Integer.parseInt(departmentId);



		if(intBranchId == 1 && intDepartmentId == 3){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 1 && intDepartmentId == 4){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 2 && intDepartmentId == 1){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 2 && intDepartmentId == 2){
			messages.add("支店名と部署名が不正な組み合わせです");
		}

		if(intBranchId == 3 && intDepartmentId == 1){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 3 && intDepartmentId == 2){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 4 && intDepartmentId == 1){
			messages.add("支店名と部署名が不正な組み合わせです");
		}
		if(intBranchId == 4 && intDepartmentId == 2){
			messages.add("支店名と部署名が不正な組み合わせです");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
