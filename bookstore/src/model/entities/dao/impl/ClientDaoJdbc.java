package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;
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
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeStatement(ps);
		}
		
		return false;
	}

	@Override
	public Client retrieve(String name) {
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		return false;
	}

	@Override
	public boolean update(Client client) {
		return false;
	}
}
