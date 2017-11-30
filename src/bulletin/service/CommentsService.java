package bulletin.service;

import static bulletin.utils.CloseableUtil.*;
import static bulletin.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletin.beans.Comments;
import bulletin.beans.UserComments;
import bulletin.dao.CommentsDao;
import bulletin.dao.UserCommentsDao;

public class CommentsService {

	public void register(Comments comments) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentsDao commentsDao = new CommentsDao();
			commentsDao.insert(connection, comments);

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

	private static final int LIMIT_NUM = 1000;

	public List<UserComments> getComments() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentsDao commentsDao = new UserCommentsDao();
			List<UserComments> ret = commentsDao.getUserComments(connection, LIMIT_NUM);

			commit(connection);

			return ret;
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


	public void deleteComments(String del) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentsDao UserCommentsDao = new UserCommentsDao();
			UserCommentsDao.DeleteUserComments(connection, del);

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
