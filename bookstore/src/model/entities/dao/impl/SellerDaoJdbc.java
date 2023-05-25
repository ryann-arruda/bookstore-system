package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DatabaseException;
import model.entities.Client;
import model.entities.Seller;
import model.entities.dao.SellerDAO;

public class SellerDaoJdbc implements SellerDAO{
	private Connection conn;
	
	public SellerDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Seller seller) {
		PreparedStatement ps;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Seller (seller_name, age, email, seller_password) " +
										"VALUES (?,?,?,?)");
			
			ps.setString(1, seller.getName());
			ps.setInt(2, seller.getAge());
			ps.setString(3, seller.getEmail());
			ps.setString(4,  seller.getPassword());
			
			int rowsAffected = ps.executeUpdate();
			
			if (rowsAffected > 0) {
				return true;
			}
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		return false;
	}

	@Override
	public Seller retrieve(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Seller seller) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Seller> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client listOneClient() {
		// TODO Auto-generated method stub
		return null;
	}

}
