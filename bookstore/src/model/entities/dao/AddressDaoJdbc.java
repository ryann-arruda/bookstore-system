package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DatabaseException;
import model.entities.Address;
import model.entities.dao.AddressDAO;

public class AddressDaoJdbc implements AddressDAO{
	private Connection conn;
	
	public AddressDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int insert(Address address) {
		PreparedStatement ps;
		int result = 0;
		
		try {
			ps = conn.prepareStatement("INSERT INTO address VALUES (default, ?,?,?,?,?)");
			
			ps.setString(1, address.getThoroughfare());
			ps.setString(2, address.getNeighborhood());
			ps.setString(3, address.getComplement());
			ps.setInt(4, address.getNumber());
			ps.setString(5, address.getZipCode());
			
			result = ps.executeUpdate();
			
			if(result > 0) {
				return true;
			}
		}
		
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		return false;
	}

	@Override
	public Address retrive(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Address address) {
		// TODO Auto-generated method stub
		return false;
	}

}
