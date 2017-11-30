package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletin.beans.UserComments;
import bulletin.exception.SQLRuntimeException;

public class UserCommentsDao {

	public List<UserComments> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM users_comments ");
			sql.append("ORDER BY created_at DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComments> ret = toUserCommentsList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComments> toUserCommentsList(ResultSet rs)
			throws SQLException {

		List<UserComments> ret = new ArrayList<UserComments>();
		try {
			while (rs.next()) {

				String name = rs.getString("name");
				int id = rs.getInt("id");
				int post_id = rs.getInt("post_id");
				int userId = rs.getInt("user_id");
				String text = rs.getString("text");
				Timestamp created_at = rs.getTimestamp("created_at");

				UserComments comments = new UserComments();

				comments.setName(name);
				comments.setId(id);
				comments.setPost_id(post_id);
				comments.setUser_id(userId);
				comments.setText(text);
				comments.setCreated_at(created_at);

				ret.add(comments);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void DeleteUserComments(Connection connection, String del) {

		PreparedStatement ps = null;
		try {
			String sql = "delete from comments where id = ?";

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
