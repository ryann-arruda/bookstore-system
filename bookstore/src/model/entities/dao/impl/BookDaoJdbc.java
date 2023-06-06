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
	
	private Book instantiateBook(ResultSet rs, List<Author> authors) throws SQLException{
		Book book = new Book();
		
		book.setTitle(rs.getString("title"));
		book.setPrice(rs.getFloat("price"));
		book.setMainGenre(rs.getString("main_genre"));
		book.setPlacePublication(rs.getString("place_publication"));
		book.setYearPublication(rs.getInt("year_publication"));
		book.setAuthors(authors);
		
		return book;
	}

	@Override
	public boolean insert(Book book) {
		AuthorDAO authorDao = DAOFactory.getAuthorDAO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsAffected = -1;
		int bookId = -1;
		
		try {
			bookId = retrieveBookId(book.getTitle());
			
			if(bookId != -1) {
				int amount = retrieveAmountBooks(book.getTitle());
				
				ps = conn.prepareStatement("UPDATE Book SET amount_books = ? WHERE title = ?");
				
				ps.setInt(1, amount+1);
				ps.setString(2, book.getTitle());
				
				rowsAffected = ps.executeUpdate();
			}
			else {
				ps = conn.prepareStatement("INSERT INTO Book(title,main_genre,place_publication,year_publication,price,amount_books) " +
										   "VALUES (?,?,?,?,?,1)", Statement.RETURN_GENERATED_KEYS);
				
				ps.setString(1, book.getTitle());
				ps.setString(2, book.getMainGenre());
				ps.setString(3, book.getPlacePublication());
				ps.setInt(4, book.getYearPublication());
				ps.setFloat(5, book.getPrice());
				
				rowsAffected = ps.executeUpdate();
				
				bookId = -1;
				
				if(rowsAffected > 0) {
					rs = ps.getGeneratedKeys();
					
					while(rs.next()) {
						bookId = rs.getInt(1);
						
					}
					
					rowsAffected = -1;
					
					for(Author a : book.getAuthors()) {
						int authorId = authorDao.insert(a);
						
						ps = conn.prepareStatement("INSERT INTO Book_Author(book_id,author_id) " +
												   "VALUES (?,?)");
						
						ps.setInt(1, bookId);
						ps.setInt(2, authorId);
						
						rowsAffected = ps.executeUpdate();
					}
				}
				
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		if(rowsAffected > 0) {
			return true;
		}
		
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
				book = instantiateBook(rs, authors);
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

	@SuppressWarnings("resource")
	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			int amountBooks = retrieveAmountBooks(id);
			
			if(amountBooks == 1) {
				ps = conn.prepareStatement("DELETE FROM Book WHERE book_id = ?");
				
				ps.setInt(1, id);
				
				rowsAffected = ps.executeUpdate();
				
				if(rowsAffected > 0) {
					rowsAffected = -1;
					
					ps = conn.prepareStatement("DELETE FROM Book_Author WHERE book_id = ?");
					
					ps.setInt(1, id);
					
					rowsAffected = ps.executeUpdate();
				}
			}
			
			else {
				ps = conn.prepareStatement("UPDATE Book SET amount_books = ? WHERE book_id = ?");
				
				ps.setInt(1, amountBooks - 1);
				ps.setInt(2, id);
				
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
	public boolean update(Book book, String currentTitle) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			int bookId = retrieveBookId(currentTitle);
			
			if(bookId != -1) {
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
				
				books.add(instantiateBook(rs, authors));
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
	public Book listFirstBook() {
		AuthorDAO authorDao = null;
		Statement st = null;
		ResultSet rs = null;
		Book book = null;
		
		try {
			authorDao = DAOFactory.getAuthorDAO();
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM Book WHERE book_id = 1");
			
			while(rs.next()) {
				String title = rs.getString("title");
				
				List<Author> authors = authorDao.retrieveAllAuthorsBook(title);
				book = instantiateBook(rs, authors);
			}
		}
		
		catch(SQLException e) {
			
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(st);
		}
		
		return book;
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
				authors = authorDao.retrieveAllAuthorsBook(rs.getString("title"));

				books.add(instantiateBook(rs, authors));
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

	@Override
	public int retrieveAmountBooks(int id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int amountBooks = -1;
		
		try {
			ps = conn.prepareStatement("SELECT amount_books FROM Book WHERE book_id = ?");
			
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				amountBooks = rs.getInt(1);
			}
			
			return amountBooks;
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
	}
	
}
