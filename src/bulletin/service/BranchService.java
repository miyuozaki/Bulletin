package bulletin.service;

import static bulletin.utils.CloseableUtil.*;
import static bulletin.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletin.beans.Branches;
import bulletin.dao.BranchDao;

public class BranchService {

	public List<Branches> getBranches(){

		Connection connections = null;
		try {
			connections = getConnection();

			BranchDao branchDao = new BranchDao();

			List<Branches>branches = branchDao.getBranches(connections);
			commit(connections);

			return branches;

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
