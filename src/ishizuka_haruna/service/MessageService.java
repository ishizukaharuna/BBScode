package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ishizuka_haruna.beans.Message;
import ishizuka_haruna.dao.MessageDao;

public class MessageService {

	public List<Message> getCategory(){
		Connection connection = null;
		try{
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			List<Message> ret = messageDao.getCategory(connection);

			commit(connection);
			return ret;

		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}




	//投稿削除
	public void deletMessage(int id) {
		Connection connection = null;
		try{
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.deleteMessage(connection, id);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	//投稿登録
	public void register(Message message) {
		Connection connection = null;
		try{
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}


}
