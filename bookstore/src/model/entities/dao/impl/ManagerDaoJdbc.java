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
	public boolean update(Manager manager, String email) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			
			addressDao = DAOFactory.getAddressDAO();
			int managerId = retrieveManagerId(email);
			
			if(managerId != -1) {
				int addressId = addressDao.update(manager.getAddress(), manager.getAddress().getZipCode());
				
				if(addressId == -1) {
					addressId = addressDao.insert(manager.getAddress());
				}
				
				ps= conn.prepareStatement("UPDATE Manager SET manager_name = ?, age = ?, email = ?, manager_password = ?, "+
										  "address_id = ? WHERE manager_id = ?");
				
				ps.setString(1, manager.getName());
				ps.setInt(2, manager.getAge());
				ps.setString(3, manager.getEmail());
				ps.setString(4, manager.getPassword());
				ps.setInt(5, addressId);
				ps.setInt(6, managerId);
				
				rowsAffected = ps.executeUpdate();
			}
			
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}
		
		if(rowsAffected > 0) {
			return true;
		}
		
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
