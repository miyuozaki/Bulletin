package bulletin.service;

import static bulletin.utils.CloseableUtil.*;
import static bulletin.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletin.beans.Posts;
import bulletin.beans.UserPosts;
import bulletin.dao.PostsDao;
import bulletin.dao.UserPostsDao;

public class PostsService {

	public void register(Posts posts) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostsDao postsDao = new PostsDao();
			postsDao.insert(connection, posts);

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

	public List<UserPosts> getPosts(String startDate,String endDate,String category) {

		Connection connection = null;

		try {
			connection = getConnection();

			UserPostsDao postsDao = new UserPostsDao();
			List<UserPosts> ret = postsDao.getUserPosts(connection, LIMIT_NUM,startDate,endDate,category);

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



	public void deletePosts(String del) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostsDao UserPostsDao = new UserPostsDao();
			UserPostsDao.DeleteUserPosts(connection, del);

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

