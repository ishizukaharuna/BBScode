package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;

import ishizuka_haruna.beans.User;
import ishizuka_haruna.dao.UserDao;
import ishizuka_haruna.utils.CipherUtil;

public class LoginService {

	public User login(String account, String password) {

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection,account,encPassword);

			commit(connection);
			return user;
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}


	}

}
