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
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Author(author_name, age, email, address_id) VALUES (?,?,?,?)");
			
			ps.setString(1, author.getName());
			ps.setInt(2, author.getAge());
			ps.setString(3, author.getEmail());
			
			int addressId = addressDJ.insert(author.getAddress());
			
			ps.setInt(4, addressId);
			
			rowsAffected = ps.executeUpdate();
		}
		
		catch (SQLException e) {
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

	@Override
	public List<Author> retrieveAllAuthorsBook(String bookTitle) {
		AddressDAO addrdao = DAOFactory.getAddressDAO();
		List<Author> authors = new ArrayList<Author>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM Author A, Book B INNER JOIN Book_Author BA ON B.book_id = BA.book_id " + 
		   			 					"WHERE BA.author_id = A.author_id AND B.title = ?");
			
			ps.setString(1, bookTitle);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Address addr = addrdao.retrive(rs.getInt("address_id"));
				
				authors.add(new Author(rs.getString("author_name"), rs.getInt("age"), rs.getString("email"), addr));
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return authors;
	}
}
