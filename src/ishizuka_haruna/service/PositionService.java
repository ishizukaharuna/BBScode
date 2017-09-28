package ishizuka_haruna.service;

import static ishizuka_haruna.utils.CloseableUtil.*;
import static ishizuka_haruna.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ishizuka_haruna.beans.Position;
import ishizuka_haruna.dao.PositionDao;

public class PositionService {

	public List<Position> getPosition(){

		Connection connection = null;
		try{
			connection = getConnection();

			PositionDao positionDao = new PositionDao();
			List<Position> ret = positionDao.getPosition(connection);

			commit(connection);
			return ret;
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
}
