package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ishizuka_haruna.beans.User;
import ishizuka_haruna.dao.UserDao;

public class ManagementService  {

	public List<User> getUser(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<User>ret = userDao.getUser(connection);

			commit(connection);
			return ret;

		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}



	}

}
