package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;

import ishizuka_haruna.beans.Comment;
import ishizuka_haruna.dao.CommentDao;

public class CommentService {

	public void register(Comment comment) {
		Connection connection = null;
		try{
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

			commit(connection);

		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}

	}

	public void deleteComment(int id) {
		Connection connection = null;
		try{
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.deleteComment(connection, id);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}

	}



}
