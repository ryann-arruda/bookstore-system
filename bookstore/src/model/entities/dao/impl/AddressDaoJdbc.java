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
	
	private Address instantiateAddress(ResultSet rs) throws SQLException{
		Address address = new Address();
		
		address.setThoroughfare(rs.getString("thoroughfare"));
		address.setNeighborhood(rs.getString("neighborhood"));
		address.setComplement(rs.getString("complement"));
		address.setNumber(rs.getInt("house_number"));
		address.setZipCode(rs.getString("zip_code"));
		
		return address;
	}

	@Override
	public int insert(Address address) {
		PreparedStatement ps = null;
		int rowsAffected = 0;
		int addressId = -1;
		
		try {
			addressId = retrieveAddressId(address.getZipCode());
			
			if(addressId == -1) {
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
						addressId = rs.getInt(1);
					}
				}
			}
		}
		
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(ps);
		}
		
		return addressId;
	}

	@Override
	public Address retrive(int address_id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Address address = null;
		
		try {			
			ps = conn.prepareStatement("SELECT * FROM Address WHERE address_id = ?");
			
			ps.setInt(1, address_id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				address = instantiateAddress(rs);
			}
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return address;
	}

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps =  conn.prepareStatement("DELETE FROM Address WHERE address_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
		}
		
		catch(SQLException e) {
			throw new DatabaseException("Não é possível deletar o endereço! Existem indivíduos com vínculo nesse endereço");
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
	public int update(Address address, String zipCode) {
		PreparedStatement ps = null;
		int addressId = -1;
		
		try {
			addressId = retrieveAddressId(zipCode);
			
			if(addressId != -1) {
				ps = conn.prepareStatement("UPDATE Address SET thoroughfare=?, neighborhood=?, complement=?, house_number=?, zip_code=?"+
											"WHERE address_id = ?");
				
				ps.setString(1, address.getThoroughfare());
				ps.setString(2, address.getNeighborhood());
				ps.setString(3, address.getComplement());
				ps.setInt(4, address.getNumber());
				ps.setString(5, address.getZipCode());
				ps.setInt(6, addressId);
				
				ps.executeUpdate();
			}	
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}
		
		return addressId;
	}

	@Override
	public int retrieveAddressId(String zipCode) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int addressId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT address_id FROM Address WHERE zip_code = ?");
			
			ps.setString(1, zipCode);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				addressId = rs.getInt(1);
			}
			
			return addressId;
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

}
