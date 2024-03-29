package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Database;
import db.DatabaseException;
import model.entities.Address;
import model.entities.Book;
import model.entities.Client;
import model.entities.Manager;
import model.entities.Seller;
import model.entities.dao.AddressDAO;
import model.entities.dao.AuthorDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.SellerDAO;

public class SellerDaoJdbc implements SellerDAO{
	private Connection conn;
	
	public SellerDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Seller seller) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Seller (seller_name, age, email, seller_password) " +
										"VALUES (?,?,?,?)");
			
			ps.setString(1, seller.getName());
			ps.setInt(2, seller.getAge());
			ps.setString(3, seller.getEmail());
			ps.setString(4,  seller.getPassword());
			
			rowsAffected = ps.executeUpdate();
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(ps);
		}
		
		if (rowsAffected > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public Seller retrieve(String name) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Seller seller = null;
		
		try {
			addressDao = DAOFactory.getAddressDAO();
			
			ps = conn.prepareStatement("SELECT * FROM Seller WHERE seller_name = ?");
			
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Address addr = addressDao.retrive(rs.getInt("address_id"));
				
				seller = new Seller(rs.getString("seller_name"), rs.getInt("age"), rs.getString("email"), addr, 
						  rs.getString("seller_password"));
				
			}
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return seller;
	}

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("DELETE FROM Seller WHERE seller_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}
		
		if(rowsAffected != -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean update(Seller seller, String email) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			addressDao = DAOFactory.getAddressDAO();
			int sellerId = retrieveSellerId(email);
			
			if(sellerId != - 1) {
				int addressId = addressDao.update(seller.getAddress(), seller.getAddress().getZipCode());
				
				if(addressId == -1) {
					addressId = addressDao.insert(seller.getAddress());
				}
				
				ps = conn.prepareStatement("UPDATE Seller SET seller_name = ?, age = ?, email = ?, seller_password = ?, "+
										   "address_id = ? WHERE seller_id = ?");
				
				ps.setString(1, seller.getName());
				ps.setInt(2, seller.getAge());
				ps.setString(3, seller.getEmail());
				ps.setString(4, seller.getPassword());
				ps.setInt(5, addressId);
				ps.setInt(6, sellerId);
				
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
	public List<Seller> listAll() {
		AddressDAO addressDao = DAOFactory.getAddressDAO();
		List<Seller> sellers = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM Seller");
			
			while(rs.next()) {
				
				Address addr = addressDao.retrive(rs.getInt("address_id"));
				
				Seller seller = new Seller(rs.getString("seller_name"), rs.getInt("age"), rs.getString("email"), addr, 
						  rs.getString("seller_password"));
				
				sellers.add(seller);
				
			}
			
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(st);
		}
		
		return sellers;
	}

	@Override
	public List<Client> listAllClients() {
		AddressDAO addressDao = DAOFactory.getAddressDAO();
		List<Client> clients = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM Client_t");
			
			while(rs.next()) {
				Address addr = addressDao.retrive(rs.getInt("address_id"));
				
				Client client = new Client(rs.getString("client_t_name"), rs.getInt("age"), rs.getString("email"),
										   rs.getBoolean("isOnePiece"),rs.getString("team") ,rs.getString("client_t_password"), addr);
				
				clients.add(client);
				
			}
			
		}
		
		catch(SQLException e ) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(st);
		}
		
		return clients;
	}

	@Override
	public int retrieveSellerId(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int sellerId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM Seller WHERE email = ?");
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				sellerId = rs.getInt(1);
			}
		}
		
		catch(SQLException e ) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		
		return sellerId;
	}

}
