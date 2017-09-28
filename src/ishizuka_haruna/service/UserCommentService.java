package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ishizuka_haruna.beans.UserComment;
import ishizuka_haruna.dao.UserCommentDao;

public class UserCommentService {

	public List<UserComment> getUserComment(){

		Connection connection = null;
		try{
			connection = getConnection();
			UserCommentDao userCommentDao = new UserCommentDao();
			List<UserComment> ret = userCommentDao.getUserComment(connection);

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
