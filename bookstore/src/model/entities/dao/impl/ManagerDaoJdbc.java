package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;
import model.entities.dao.ManagerDAO;

public class ManagerDaoJdbc implements ManagerDAO{
	private Connection conn;
	
	public ManagerDaoJdbc(Connection coon) {
		this.conn = conn;
	}

	@Override
	public ManagerDAO retrieve(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("DELETE FROM Manager WHERE manager_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
		}
		
		catch(SQLException e ) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}
		
		if (rowsAffected != -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean update(ManagerDAO manager) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int retrieveManagerId(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int managerId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT manager_id FROM Manager WHERE email = ?");
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				managerId = rs.getInt(1);
			}
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		return managerId;
	}
}
