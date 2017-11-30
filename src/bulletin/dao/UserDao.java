package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.User;
import bulletin.exception.NoRowsUpdatedRuntimeException;
import bulletin.exception.SQLRuntimeException;
public class UserDao {

	public User getUsers(Connection connection, String login_id,
			String password) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE (login_id = ?) AND password = ? and is_stopped = 0";

			ps = connection.prepareStatement(sql);
			ps.setString(1, login_id);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}





	public List<User> getUsers(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);

			return userList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String login_id = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id = rs.getInt("branch_id");
				int department_id = rs.getInt("department_id");
				int is_stopped = rs.getInt("is_stopped");

				User users = new User();
				users.setId(id);
				users.setLogin_id(login_id);
				users.setPassword(password);
				users.setName(name);
				users.setBranch_id(branch_id);
				users.setDepartment_id(department_id);
				users.setIs_stopped(is_stopped);

				ret.add(users);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, User users) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", is_stopped");
			sql.append(") VALUES (");
			sql.append("?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // department_id
			sql.append(", 0"); // is_stopped
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, users.getLogin_id());
			ps.setString(2, users.getPassword());
			ps.setString(3, users.getName());
			ps.setInt(4, users.getBranch_id());
			ps.setInt(5, users.getDepartment_id());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User users) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append("  login_id = ?");
			if(StringUtils.isEmpty(users.getPassword())){

			}else{
				sql.append(", password = ?");
			}
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");



			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, users.getLogin_id());

			if(StringUtils.isEmpty(users.getPassword())){
				ps.setString(2, users.getName());
				ps.setInt(3, users.getBranch_id());
				ps.setInt(4, users.getDepartment_id());
				ps.setInt(5, users.getId());

			}else{
				ps.setString(2, users.getPassword());
				ps.setString(3, users.getName());
				ps.setInt(4, users.getBranch_id());
				ps.setInt(5, users.getDepartment_id());
				ps.setInt(6, users.getId());

			}


			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User Overlap(Connection connection,String loginId){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void userStopped(Connection connection,String is_stopped,String id) {

		PreparedStatement ps = null;
		try {
			String sql = "update users set is_stopped = ? where id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, is_stopped);
			ps.setString(2, id);

			ps.executeUpdate();

			return;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		}finally{
			close(ps);
		}
	}

}
