package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletin.beans.UserPosts;
import bulletin.exception.SQLRuntimeException;

public class UserPostsDao {

	public List<UserPosts> getUserPosts(Connection connection, int num,String startDate,String endDate,String category) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM users_posts WHERE created_at >= ? AND created_at < ?");
			if(!StringUtils.isEmpty(category)){
				sql.append(" and category = ? ");
			}
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, startDate + " 00:00:00");
			ps.setString(2, endDate + " 23:59:59");

			if(!StringUtils.isEmpty(category)){
				ps.setString(3, category);
			}

			System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			List<UserPosts> ret = toUserPostsList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserPosts> toUserPostsList(ResultSet rs)
			throws SQLException {

		List<UserPosts> ret = new ArrayList<UserPosts>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");
				String subject = rs.getString("subject");
				String name = rs.getString("name");
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String text = rs.getString("text");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserPosts posts = new UserPosts();
				posts.setCategory(category);
				posts.setSubject(subject);
				posts.setName(name);
				posts.setId(id);
				posts.setUser_id(userId);
				posts.setText(text);
				posts.setCreated_at(created_at);

				ret.add(posts);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void DeleteUserPosts(Connection connection, String del) {

		PreparedStatement ps = null;
		try {
			String sql = "delete from posts where id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, del);

			ps.executeUpdate();

			return;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);

		}finally{
			close(ps);
		}
	}
}

