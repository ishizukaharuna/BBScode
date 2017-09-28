package ishizuka_haruna.dao;

import static ishizuka_haruna.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.txw2.IllegalSignatureException;

import ishizuka_haruna.beans.User;
import ishizuka_haruna.exception.NoRowsUpdatedRuntimeException;
import ishizuka_haruna.exception.SQLRuntimeException;

public class UserDao {

	//ユーザーの復活と停止
	public void getStopUser(Connection connection, int id, int isWorking) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_working = ?");

			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, isWorking);
			ps.setInt(2, id);


			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	//ユーザー編集＜ユーザーの指定＞
	public User getUser(Connection connection, int id){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);

			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalSignatureException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	//ユーザー編集
	public void update(Connection connection, User user, String password){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" account = ?");
			if(!(password.length() == 0  || password == null)){
				sql.append(", password = ?");
				sql.append(", name = ?");
				sql.append(", branch_id = ?");
				sql.append(", position_id =?");
				sql.append(", update_date = CURRENT_TIMESTAMP");
			}else{
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", position_id =?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			}

			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getAccount());
			if(!(password.length() == 0  || password == null)){
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getName());
				ps.setInt(4, user.getBranch_id());
				ps.setInt(5, user.getPosition_id());
				ps.setInt(6, user.getId());
			}else{
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranch_id());
			ps.setInt(4, user.getPosition_id());
			ps.setInt(5, user.getId());
			}

			System.out.println(ps);
			int count =ps.executeUpdate();

			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	//ユーザー情報の表示（管理）
	public List<User> getUser(Connection connection) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM users");
			sql.append(" ORDER BY branch_id ,position_id");
			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = tosignUpUserList(rs);

			return ret;

		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<User> tosignUpUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try{
			while(rs.next()){
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				int isWorking = rs.getInt("is_working");


				User user = new User();

				user.setId(id);
				user.setAccount(account);
				user.setName(name);
				user.setBranch_id(branchId);
				user.setPosition_id(positionId);
				user.setIs_working(isWorking);

				ret.add(user);
			}
			return ret;
		}finally{
			close(rs);
		}
	}



	//ログイン
	public User getUser(Connection connection, String account, String password) {
		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE account = ? AND password = ? AND is_working = 0";
			ps = connection.prepareStatement(sql);
			ps.setString(1, account);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			List<User> userList = toUserList(rs);

			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String account = rs.getString("account");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branch_id = rs.getInt("branch_id");
				int position_id = rs.getInt("position_id");
				int isWorking = rs.getInt("is_working");
				Timestamp insert_date = rs.getTimestamp("insert_date");
				Timestamp update_date = rs.getTimestamp("update_date");

				User user = new User();

				user.setId(id);
				user.setAccount(account);
				user.setPassword(password);
				user.setName(name);
				user.setBranch_id(branch_id);
				user.setPosition_id(position_id);
				user.setIs_working(isWorking);
				user.setInsert_date(insert_date);
				user.setUpdate_date(update_date);

				ret.add(user);
			}
			return ret;
		}finally{
			close(rs);
		}
	}

	//ユーザー登録
	public void insert(Connection connection, User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users (");
			sql.append("account");
			sql.append(", password");
			sql.append(", name");
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

			ps.setString(1, user.getAccount());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranch_id());
			ps.setInt(5, user.getPosition_id());

			ps.executeUpdate();

		}catch(SQLException e){
			throw new SQLRuntimeException(e);

		}finally{
			close(ps);
		}
	}

	public User getAccount(Connection connection, String account){
		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE account = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, account);


			ResultSet rs = ps.executeQuery();

			List<User> userList = toUserList(rs);

			if(userList.isEmpty() == true){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{
				return userList.get(0);
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

}
