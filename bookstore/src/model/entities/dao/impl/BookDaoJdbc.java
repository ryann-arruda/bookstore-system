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
import model.entities.Author;
import model.entities.Book;
import model.entities.dao.AuthorDAO;
import model.entities.dao.BookDAO;
import model.entities.dao.DAOFactory;

public class BookDaoJdbc implements BookDAO{
	private Connection conn;
	
	public BookDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Book retrieve(String title) {
		List<Author> authors = null;
		AuthorDAO authorDao = DAOFactory.getAuthorDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Book book = null;
		
		try {
			ps =  conn.prepareStatement("SELECT * FROM Book WHERE title = ?");
			
			ps.setString(1, title);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				authors = authorDao.retrieveAllAuthorsBook(title);
				book = new Book(rs.getString("title"),rs.getFloat("price"), rs.getString("main_genre"),
								rs.getString("place_publication"), rs.getInt("year_publication"), authors);
			}
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return book;
	}

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("DELETE FROM Book WHERE book_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
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
	public boolean update(Book book, String currentTitle) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			int bookId = retrieveBookId(currentTitle);
			
			ps = conn.prepareStatement("UPDATE Book SET title = ?, main_genre = ?, place_publication = ?, " + 
									   "year_publication = ?, price = ? WHERE book_id = ?");
			
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getMainGenre());
			ps.setString(3, book.getPlacePublication());
			ps.setInt(4, book.getYearPublication());
			ps.setFloat(5, book.getPrice());
			ps.setInt(6, bookId);
			
			rowsAffected = ps.executeUpdate();
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
	public List<Book> listAll() {
		AuthorDAO authorDao = DAOFactory.getAuthorDAO();
		List<Book> books = new ArrayList<>();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM Book");
			
			while(rs.next()) {
				String bookTitle = rs.getString("title");
				
				List<Author> authors = authorDao.retrieveAllAuthorsBook(bookTitle);
				
				books.add(new Book(bookTitle, rs.getFloat("price"), rs.getString("main_genre"),
						 rs.getString("place_publication"), rs.getInt("year_publication"), authors));
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(st);
		}
		
		return books;
	}

	@Override
	public Book listOneBook() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> retrieveAllBooksAuthor(String authorName) {
		AuthorDAO authorDao = DAOFactory.getAuthorDAO();
		List<Author> authors = new ArrayList<>();
		List<Book> books = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM Book B, Author A INNER JOIN Book_Author BA ON " +
									   "A.author_id = BA.author_id WHERE BA.book_id = B.book_id AND A.author_name = ?");
			
			
			ps.setString(1, authorName);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				String bookTitle = rs.getString("title");
				
				authors = authorDao.retrieveAllAuthorsBook(bookTitle);

				books.add(new Book(bookTitle, rs.getFloat("price"), rs.getString("main_genre"),
						  rs.getString("place_publication"), rs.getInt("year_publication"), authors));
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return books;
	}

	@Override
	public int retrieveBookId(String title) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int bookId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT book_id FROM Book WHERE title = ?");
			
			ps.setString(1, title);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bookId = rs.getInt("book_id");
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return bookId;
	}

	@Override
	public int retrieveAmountBooks(String title) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int amountBooks = -1;
		
		try {
			ps = conn.prepareStatement("SELECT amount_books FROM Book WHERE title = ?");
			
			ps.setString(1, title);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				amountBooks = rs.getInt("amount_books");
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return amountBooks;
	}
	
}
