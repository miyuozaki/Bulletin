package bulletin.dao;

import static bulletin.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletin.beans.Departments;
import bulletin.exception.SQLRuntimeException;

public class DepartmentDao {

	public List<Departments> getDepartments(Connection connection){

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM departments";

			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			List<Departments> departments = toDepartmentList(rs);

			return departments;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Departments> toDepartmentList(ResultSet rs) throws SQLException {

		List<Departments> ret = new ArrayList<Departments>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Departments department = new Departments();

				department.setId(id);
				department.setName(name);

				ret.add(department);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
