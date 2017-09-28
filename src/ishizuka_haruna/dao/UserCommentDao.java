package ishizuka_haruna.dao;

import static ishizuka_haruna.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ishizuka_haruna.beans.UserComment;
import ishizuka_haruna.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComment(Connection connection){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_comments");
			sql.append(" ORDER BY insert_date DESC");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);

			return ret;

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try{
			while(rs.next()){
				int id = rs.getInt("id");
				String text = rs.getString("text");
				int messageId = rs.getInt("message_id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String name = rs.getString("name");

				UserComment userComment = new UserComment();

				userComment.setId(id);
				userComment.setText(text);
				userComment.setMessage_id(messageId);
				userComment.setUser_id(userId);
				userComment.setBranch_id(branchId);
				userComment.setPosition_id(positionId);
				userComment.setInsert_date(insertDate);
				userComment.setName(name);

				ret.add(userComment);
			}
			return ret;
		}finally{
			close(rs);
		}


	}

}
