package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;
import model.entities.Address;
import model.entities.Client;
import model.entities.dao.AddressDAO;
import model.entities.dao.ClientDAO;
import model.entities.dao.DAOFactory;

public class ClientDaoJdbc implements ClientDAO{
	private Connection conn;
	
	public ClientDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Client client) {
		AddressDAO addressDao = DAOFactory.getAddressDAO();
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			int address_id = addressDao.insert(client.getAddress());
			
			ps = conn.prepareStatement("INSERT INTO Client_t (client_t_name, age, email, isOnePiece, team, client_t_password, " + 
									    "address_id) VALUES (?,?,?,?,?,?,?)");
			
			ps.setString(1, client.getName());
			ps.setInt(2, client.getAge());
			ps.setString(3, client.getEmail());
			ps.setBoolean(4, client.getIsOnePiece());
			ps.setString(5, client.getTeam());
			ps.setString(6, client.getPassword());
			ps.setInt(7, address_id);
			
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
	public Client retrieve(String name) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Client client = null;
		
		try {
			client = new Client();
			addressDao = DAOFactory.getAddressDAO();
			ps =  conn.prepareStatement("SELECT * FROM Client_t WHERE client_t_name = ?");
			
			ps.setString(1, name);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int addressId = rs.getInt("address_id");
				
				Address addr = addressDao.retrive(addressId);
				
				client.setName(rs.getString("client_t_name"));
				client.setAge(rs.getInt("age"));
				client.setEmail(rs.getString("email"));
				client.setIsOnePiece(rs.getBoolean("isOnePiece"));
				client.setTeam(rs.getString("team"));
				client.setPassword(rs.getString("client_t_password"));
				client.setAddress(addr);
			}
			
			return client;
		}
		
		catch(SQLException e ) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

	@Override
	public boolean deleteById(int id) {
		return false;
	}

	@Override
	public boolean update(Client client, String email) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			addressDao = DAOFactory.getAddressDAO();
			int clientId = retrieveClientId(email);
			
			if(clientId != -1) {
				int addressId = addressDao.update(client.getAddress(), client.getAddress().getZipCode());
				
				if(addressId == -1) {
					addressId = addressDao.insert(client.getAddress());
				}
				
				ps = conn.prepareStatement("UPDATE Client_t SET client_t_name = ?, age = ?, email = ?, isOnePiece = ?, "+
										   "team = ?, client_t_password = ?, address_id = ? WHERE email = ?");
				
				ps.setString(1, client.getName());
				ps.setInt(2, client.getAge());
				ps.setString(3, client.getEmail());
				ps.setBoolean(4, client.getIsOnePiece());
				ps.setString(5, client.getTeam());
				ps.setString(6, client.getPassword());
				ps.setInt(7, addressId);
				ps.setString(8, email);
				
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
	public int retrieveClientId(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int clientId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT client_t_id FROM Client_t WHERE email = ? ");
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				clientId = rs.getInt("client_t_id");
			}
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return clientId;
	}
	
	
}
