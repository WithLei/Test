package cn.javaee.dao.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.javaee.bean.Position;
import cn.javaee.bean.Toilet;
import cn.javaee.bean.User;
import cn.javaee.dao.dao.ToiletDAO;

public class ToiletDAOImpl extends BaseDAOImpl implements ToiletDAO{

	@Override
	public boolean save(Toilet entity) {
		String sql = "insert into toilet(name, type,cleanerid,last_clanned_time,next_clanned_time,isService,floorid) value(?,?,?,?,?,?,?)";
		try (Connection connection =  ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {
			 ps.setString(1, entity.getName());
			 ps.setInt(2, entity.getType());
			 ps.setInt(3, entity.getCleaner().getId());
			 ps.setTimestamp(4,null);
			 ps.setTimestamp(5,null);
			 ps.setBoolean(6, entity.isService());
		   //ps.setInt(7, entity.getFloor.getId());  楼层项写的有点问题。
			 ps.executeUpdate();
				return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from toilet where id=\"" + id + "\"";
		return false;
	}

	@Override
	public boolean update(Toilet entity) {
		String sql = "UPDATE toilet SET name=?,type=?,cleanerid=?,last_clanned_time=?,next_clanned_time=?,isService=?,floorid=? "
				+ "WHERE id=?";
		try (Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
		    ps.setString(1, entity.getName());
			ps.setInt(2, entity.getType());
			ps.setInt(3, entity.getCleaner().getId());
			ps.setDate(4, (Date) entity.getLast_cleaned_time());
			ps.setDate(5,(Date) entity.getNext_clean_time());
			ps.setBoolean(6, entity.isService());
		  //ps.setInt(7, entity.getFloor.getId());  楼层项写的有点问题。
			ps.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public Toilet getById(int id) {
		String sql = "select * from toilet where id=" + id;
		try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return getToiletInSql(rs);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }
		return null;
	}
	

	

	private Toilet getToiletInSql(ResultSet rs) throws SQLException {
    	Toilet toilet = new Toilet();
    	toilet.setId(rs.getInt(1));
    	toilet.setName(rs.getString(2));
    	toilet.setType(rs.getInt(3));
    	CleanerDAOImpl cleanerDAOImpl = new CleanerDAOImpl();
    	toilet.setCleaner(cleanerDAOImpl.getById(rs.getInt(4)));
    	toilet.setLast_cleaned_time(rs.getDate(5));
    	toilet.setNext_clean_time(rs.getDate(6));
    	toilet.setService(rs.getBoolean(7));
        return toilet;
    }

	public List<Toilet> getById(int[] ids) {
		
		// 不做
		return null;
	}
	
	public List<Toilet> getToiletByFloor(int id) {
		String sql = "select * from toilet where floorid=" + id;
		return getToiletList(sql);
	}

	@Override
	public List<Toilet> getAll() {
		
		String sql = "select * from toilet";
		
		return getToiletList(sql);
		
	}

	private List<Toilet> getToiletList(String sql) {
		List<Toilet>toilets = new ArrayList<>();
		
		try(Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
			while(rs.next()) {
				toilets.add(getToiletInSql(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return toilets;
	}

}