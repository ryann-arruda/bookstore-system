package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		PreparedStatement ps_author = null;
		int rowsAffected = 0;
		
		try {
			ps = conn.prepareStatement("INSERT INTO Book (title,main_genre,place_publication,year_publication,price)" +
									   "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, book.getTitle());
			ps.setString(2,book.getMainGenre());
			ps.setString(3, book.getPlacePublication());
			ps.setInt(4, book.getYearPublication());
			ps.setFloat(5, book.getPrice());
			
			rowsAffected = ps.executeUpdate();
			ResultSet rs_book = ps.getGeneratedKeys();
			
			ps_author = conn.prepareStatement("INSERT INTO Author (author_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			
			int rowsAffectedAuthors = 0;
			for(String author: book.getAuthors()) {
				ps_author.setString(1, author);
				
				rowsAffectedAuthors = ps_author.executeUpdate();
				if(rowsAffectedAuthors > 0) {
					ResultSet rs_author = ps_author.getGeneratedKeys();
					int author_id = 0;
					int book_id = 0;
					
					while(rs_author.next()) {
						author_id = rs_author.getInt(1);
						
						rs_book.next();
						book_id = rs_book.getInt(1);
					}
					
					PreparedStatement psBook_Author = conn.prepareStatement("INSERT INTO Book_Author (book_id, author_id) VALUES " + 
																			"(?,?)");
					
					psBook_Author.setInt(1, author_id);
					psBook_Author.setInt(2, book_id);
				}
			}
			
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
