package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletin.beans.Branches;
import bulletin.exception.SQLRuntimeException;


public class BranchDao {
	public List<Branches> getBranches(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Branches> branches = toBranchList(rs);

			return branches;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	private List<Branches> toBranchList(ResultSet rs) throws SQLException {

		List<Branches> ret = new ArrayList<Branches>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branches branches = new Branches();

				branches.setId(id);
				branches.setName(name);

				ret.add(branches);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
