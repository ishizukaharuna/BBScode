package ishizuka_haruna.dao;

import static ishizuka_haruna.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ishizuka_haruna.beans.Comment;
import ishizuka_haruna.exception.SQLRuntimeException;

public class CommentDao {
	public void insert(Connection connection, Comment comment ) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments (");
			sql.append("user_id");
			sql.append(", message_id");
			sql.append(", text");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getUser_id());
			ps.setInt(2, comment.getMessage_id());
			ps.setString(3, comment.getText());
			ps.setInt(4, comment.getBranch_id());
			ps.setInt(5, comment.getPosition_id());



			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void deleteComment(Connection connection, int id) {

		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM comments WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

}
