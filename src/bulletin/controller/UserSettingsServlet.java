package bulletin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.Branches;
import bulletin.beans.Departments;
import bulletin.beans.User;
import bulletin.exception.NoRowsUpdatedRuntimeException;
import bulletin.service.BranchService;
import bulletin.service.DepartmentService;
import bulletin.service.UserService;

@WebServlet(urlPatterns = { "/UserSettings" })
@MultipartConfig(maxFileSize = 100000)
public class UserSettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if(userId == null){
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("userManagement");
			return;
		}

		if(!userId.matches("^[0-9]+$")){
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("userManagement");
			return;
		}

		int intUserId = Integer.parseInt(userId);

		User users = new UserService().getUsers(intUserId);

		if(users == null){
			messages.add("不正なIDです");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("userManagement");
			return;
		}

		request.setAttribute("editUser",users);

		List<Branches> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);
		List<Departments> departments = new DepartmentService().getDepartments();
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("userSettings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User editUser = getEditUser(request);

		if (isValid(request, messages)) {

			try {
				new UserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("UserManagement");
			}

			session.setAttribute("editUser", editUser);
			session.removeAttribute("editUser");

			response.sendRedirect("http://localhost:8080/Ozaki_miyu/userManagement");



		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("editUser", editUser);

			List<Branches> branches = new BranchService().getBranches();
			request.setAttribute("branches", branches);
			List<Departments> departments = new DepartmentService().getDepartments();
			request.setAttribute("departments", departments);

			request.getRequestDispatcher("userSettings.jsp").forward(request, response);
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		User editUser = new User();

		String id = request.getParameter("id");
		int intId = Integer.parseInt(id);
		editUser.setId(intId);

		editUser.setName(request.getParameter("name"));
		editUser.setLogin_id(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));

		String branchId = request.getParameter("branch_id");
		int intBranchId = Integer.parseInt(branchId);
		editUser.setBranch_id(intBranchId);
		String departmentId = request.getParameter("department_id");
		int intDepartmentId = Integer.parseInt(departmentId);
		editUser.setDepartment_id(intDepartmentId);

		return editUser;

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
		User user = UserService.getOverlap(loginId);

		String id = request.getParameter("id");
		int intId = Integer.parseInt(id);

		if(user != null && user.getId() != intId){
			messages.add("このログインIDはすでに使用されています");
		}

		if(StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		}
		if(!loginId.matches("^[0-9a-zA-Z]{6,20}")){
			messages.add("ログインIDは半角英数字6～20文字以内で入力してください");
		}
		String password = request.getParameter("password");
		String passwordConfirm = request.getParameter("passwordConfirm");

		if(!password.equals(passwordConfirm)){
			messages.add("パスワードと確認用パスワードが一致しません");
		}

		if(!StringUtils.isEmpty(password)) {

			if(!password.matches("^[-_@+*;:#$!%&A-Za-z0-9]{6,20}$")){
				messages.add("パスワードは記号含む半角英数字6～20文字以内で入力してください");
			}
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
