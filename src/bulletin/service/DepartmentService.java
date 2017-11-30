package bulletin.service;

import static bulletin.utils.CloseableUtil.*;
import static bulletin.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletin.beans.Departments;
import bulletin.dao.DepartmentDao;

public class DepartmentService {

	public List<Departments> getDepartments(){

		Connection connections = null;
		try {
			connections = getConnection();

			DepartmentDao departmentDao = new DepartmentDao();

			List<Departments>departments = departmentDao.getDepartments(connections);
			commit(connections);

			return departments;

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

}
