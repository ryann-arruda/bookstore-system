package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.Database;
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
		PreparedStatement ps = null;
		int rowsAffected = 0;
		
		try {
			ps = conn.prepareStatement("INSERT INTO address (thoroughfare, neighborhood, complement, house_number, zip_code) " +
										"VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, address.getThoroughfare());
			ps.setString(2, address.getNeighborhood());
			ps.setString(3, address.getComplement());
			ps.setInt(4, address.getNumber());
			ps.setString(5, address.getZipCode());
			
			rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				
				while(rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closePreparedStatement(ps);
		}
		
		return -1;
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
