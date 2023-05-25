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
	public Address retrive(int address_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Address address = new Address();
			
			ps = conn.prepareStatement("SELECT * FROM Address WHERE address_id = ?");
			
			ps.setInt(1, address_id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				address.setThoroughfare(rs.getString("thoroughfare"));
				address.setNeighborhood(rs.getString("neighborhood"));
				address.setComplement(rs.getString("complement"));
				address.setNumber(rs.getInt("house_number"));
				address.setZipCode(rs.getString("zip_code"));
			}
			
			return address;
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closePreparedStatement(ps);
		}
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
