package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;
import model.entities.Author;
import model.entities.dao.AddressDAO;
import model.entities.dao.AuthorDAO;
import model.entities.dao.DAOFactory;

public class AuthorDaoJdbc implements AuthorDAO {
	
private Connection conn;
	
	public AuthorDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Author author) {
		AddressDAO addressDJ = DAOFactory.getAddressDAO();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Author(author_name, age, email, address_id) VALUES (?,?,?,?)");
			
			ps.setString(1, author.getName());
			ps.setInt(2, author.getAge());
			ps.setString(3, author.getEmail());
			
			int addressId = addressDJ.insert(author.getAddress());
			
			ps.setInt(4, addressId);
			
			int rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				return true;
			}
		}
		
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closePreparedStatement(ps);
		}
		
		return false;
	}

	@Override
	public Author retrive(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

}
