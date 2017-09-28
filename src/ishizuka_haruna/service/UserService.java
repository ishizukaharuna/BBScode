package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;

import ishizuka_haruna.beans.User;
import ishizuka_haruna.dao.UserDao;
import ishizuka_haruna.utils.CipherUtil;

public class UserService {

	public void register(User user) {

		Connection connection = null;
		try{
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao =new UserDao();
			userDao.insert(connection, user);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	public void update(User user, String password){
		Connection connection = null;
		try{
			connection = getConnection();

			if(!(password.length() == 0  || password == null)){
				String encPassword = CipherUtil.encrypt(password);
				user.setPassword(encPassword);
			}
			;
			UserDao userDao = new UserDao();
			userDao.update(connection, user, password);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	//ユーザー編集時
	public User getUser(int userId){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUser(connection, userId);

			commit(connection);
			return user;
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}



	}

	public void stop(int id, int isWorking) {

		Connection connection = null;
		try{

			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.getStopUser(connection,id,isWorking);

			commit(connection);

		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	public User getAccount(String account){
		Connection connection = null;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			User ret = userDao.getAccount(connection,account);
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
