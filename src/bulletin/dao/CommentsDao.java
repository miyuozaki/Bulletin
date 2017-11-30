package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bulletin.beans.Comments;
import bulletin.exception.SQLRuntimeException;

public class CommentsDao {

	public void insert(Connection connection, Comments comments) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("user_id");
			sql.append(",post_id");
			sql.append(", text");
			sql.append(", created_at");
			sql.append(") VALUES (");
			sql.append("?"); // user_id
			sql.append(", ?");//post_id
			sql.append(", ?"); // text
			sql.append(", CURRENT_TIMESTAMP"); // created_at
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());



			ps.setInt(1, comments.getUser_id());
			ps.setInt(2,comments.getPost_id());
			ps.setString(3, comments.getText());


			System.out.println(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

}
