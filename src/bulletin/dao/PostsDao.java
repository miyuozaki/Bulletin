package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletin.beans.Posts;
import bulletin.exception.SQLRuntimeException;

public class PostsDao {


	public void insert(Connection connection, Posts posts) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO posts ( ");
			sql.append("user_id");
			sql.append(",subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", created_at");
			sql.append(") VALUES (");
			sql.append("?"); // user_id
			sql.append(", ?");//subject
			sql.append(", ?"); // text
			sql.append(", ?");//category
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, posts.getUser_id());
			ps.setString(2,posts.getSubject());
			ps.setString(3, posts.getText());
			ps.setString(4,posts.getCategory());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}

