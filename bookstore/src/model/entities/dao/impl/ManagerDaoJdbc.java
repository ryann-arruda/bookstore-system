package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;

import model.entities.Address;
import model.entities.Manager;
import model.entities.dao.AddressDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.ManagerDAO;

public class ManagerDaoJdbc implements ManagerDAO{
	private Connection conn;
	
	public ManagerDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Manager retrieve(String name) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Manager manager = null;
		
		try {
			addressDao = DAOFactory.getAddressDAO();
			ps = conn.prepareStatement("SELECT * FROM Manager WHERE manager_name = ?");
			
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Address addr = addressDao.retrive(rs.getInt("address_id"));
				
				manager = new Manager(rs.getString("manager_name"), rs.getInt("age"), rs.getString("email"), addr, 
									  rs.getString("manager_password"));
			}			
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return manager;
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
}
