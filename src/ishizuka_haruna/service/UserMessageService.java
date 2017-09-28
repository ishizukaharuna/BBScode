package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ishizuka_haruna.beans.UserMessage;
import ishizuka_haruna.dao.UserMessageDao;

public class UserMessageService {



	public List<UserMessage> getUserMessage(String start, String end, String selectCategory) {
		Connection connection = null;
		try{

			connection = getConnection();
			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret = userMessageDao.getUserMessages(connection,start,end,selectCategory);

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
