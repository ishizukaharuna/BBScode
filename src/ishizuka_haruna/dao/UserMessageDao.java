package ishizuka_haruna.dao;

import static ishizuka_haruna.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ishizuka_haruna.beans.UserMessage;
import ishizuka_haruna.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, String start, String end, String selectCategory) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM user_messages");
			sql.append(" where insert_date >= ? AND insert_date <= ? ");
			if(selectCategory != null){
				sql.append(" AND category = ?");
			}
			sql.append(" ORDER BY insert_date DESC");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, start + " 00:00:00");
			ps.setString(2, end + " 23:59:59");

			if(selectCategory != null){
				ps.setString(3, selectCategory);
			}

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);

			return ret;
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try{
			while(rs.next()){
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				String name = rs.getString("name");

				UserMessage userMessage = new UserMessage();

				userMessage.setId(id);
				userMessage.setTitle(title);
				userMessage.setText(text);
				userMessage.setCategory(category);
				userMessage.setInsert_date(insertDate);
				userMessage.setUser_id(userId);
				userMessage.setBranch_id(branchId);
				userMessage.setPosition_id(positionId);
				userMessage.setName(name);

				ret.add(userMessage);

			}
			return ret;
		}finally{
			close(rs);
		}
	}

}
