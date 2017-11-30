package bulletin.service;

import static bulletin.utils.CloseableUtil.*;
import static bulletin.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.User;
import bulletin.dao.UserDao;
import bulletin.utils.CipherUtil;

public class UserService {

	public void register(User users) {

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(users.getPassword());
			users.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, users);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public void update(User users) {

		Connection connection = null;
		try {
			connection = getConnection();

			if(!StringUtils.isEmpty(users.getPassword())){
				String encPassword = CipherUtil.encrypt(users.getPassword());
				users.setPassword(encPassword);

			}

			UserDao userDao = new UserDao();
			userDao.update(connection, users);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public User getUsers(int userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User users = userDao.getUser(connection, userId);

			commit(connection);

			return users;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public static User getOverlap(String loginId){


		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			User users = userDao.Overlap(connection,loginId);

			commit(connection);

			return users;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<User> getUsers(){

		Connection connections = null;
		try {
			connections = getConnection();

			UserDao userDao = new UserDao();

			List<User>users = userDao.getUsers(connections);
			commit(connections);

			return users;
		} catch (RuntimeException e) {
			rollback(connections);
			throw e;
		} catch (Error e) {
			rollback(connections);
			throw e;
		} finally {
			close(connections);
		}
	}

	public void stoppedUsers(String is_stopped,String id) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao UserDao = new UserDao();
			UserDao.userStopped(connection,is_stopped,id);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}