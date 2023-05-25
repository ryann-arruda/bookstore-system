package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import db.DatabaseException;
import model.entities.Book;
import model.entities.dao.BookDAO;

public class BookDaoJdbc implements BookDAO{
	
private Connection conn;
	
	public BookDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Book book) {
		PreparedStatement ps = null;
		int rowsAffected = 0;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Book (title,main_genre,place_publication,year_publication,price)" +
									   "VALUES (?,?,?,?,?)");
			
			ps.setString(1, book.getTitle());
			ps.setString(2,book.getMainGenre());
			ps.setString(3, book.getPlacePublication());
			ps.setInt(4, book.getYearPublication());
			ps.setFloat(5, book.getPrice());
			
			rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				return true;
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closePreparedStatement(ps);
		}
		
		return false;
	}

	@Override
	public Book retrieve(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book listOneBook() {
		// TODO Auto-generated method stub
		return null;
	}

}
