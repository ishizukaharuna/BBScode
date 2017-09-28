package ishizuka_haruna.dao;

import static ishizuka_haruna.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ishizuka_haruna.beans.Message;
import ishizuka_haruna.exception.SQLRuntimeException;

public class MessageDao {

	public List<Message> getCategory(Connection connection){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT DISTINCT category FROM messages";
			ps = connection.prepareStatement(sql);


			ResultSet rs = ps.executeQuery();
			List<Message> ret = toCategoryList(rs);

			return ret;

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

	private List<Message> toCategoryList(ResultSet rs) throws SQLException {
		List<Message> ret = new ArrayList<Message>();
		try{
			while(rs.next()){
				String category = rs.getString("category");
				Message messageCategory = new Message();

				messageCategory.setCategory(category);
				ret.add(messageCategory);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

	public void deleteMessage(Connection connection, int id) {

		PreparedStatement ps = null;
		try{
			String sql = "DELETE FROM messages WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();


		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

	public void insert(Connection connection, Message message){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages (");
			sql.append("user_id");
			sql.append(", title");
			sql.append(", text");
			sql.append(", category");
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
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getUser_id());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());
			ps.setInt(5, message.getBranch_id());
			ps.setInt(6, message.getPosition_id());

			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}


	}



}
