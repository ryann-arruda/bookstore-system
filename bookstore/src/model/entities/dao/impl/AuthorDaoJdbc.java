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
	public int insert(Author author) {
		AddressDAO addressDJ = DAOFactory.getAddressDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsAffected = -1;
		int authorId = -1;
		
		try {
			authorId = retrieveAuthorId(author.getEmail()); 
			
			if(authorId == -1) {
				ps = conn.prepareStatement("INSERT INTO Author(author_name, age, email, address_id) VALUES (?,?,?,?)", 
											Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, author.getName());
				ps.setInt(2, author.getAge());
				ps.setString(3, author.getEmail());
				
				int addressId = addressDJ.insert(author.getAddress());
				
				ps.setInt(4, addressId);
				
				rowsAffected = ps.executeUpdate();
				
				if(rowsAffected > 0) {
					rs = ps.getGeneratedKeys();
					
					while(rs.next()) {
						authorId = rs.getInt(1);
					}
				}
			}
		}
		
		catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return authorId;
	}

	@Override
	public Author retrive(String email) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Author author = null;
		
		try {
			addressDao = DAOFactory.getAddressDAO();
			ps = conn.prepareStatement("SELECT * FROM Author WHERE email = ?");
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Address addr = addressDao.retrive(rs.getInt("address_id"));
				
				author = new Author(rs.getString("author_name"), rs.getInt("age"), rs.getString("email"), addr);
			}
			
			return author;
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("DELETE FROM Author WHERE author_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
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
	public boolean update(Author author, String email) {
		AddressDAO addressDao = null;
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			
			int authorId =  retrieveAuthorId(email);
			 
			if(authorId != -1) {
				
				addressDao = DAOFactory.getAddressDAO();
				
				int addressId = addressDao.update(author.getAddress(), author.getAddress().getZipCode());
				
				
				ps = conn.prepareStatement("UPDATE Author SET author_name=?, age=?, email=?, address_id=? "+
											"WHERE email= ?");
				
				ps.setString(1, author.getName());
				ps.setInt(2, author.getAge());
				ps.setString(3, author.getEmail());
				ps.setInt(4, addressId);
				ps.setString(5, email);
				
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

	@Override
	public int retrieveAuthorId(String email) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int authorId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT author_id FROM Author WHERE email = ?");
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				authorId = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return authorId;
	}
}
