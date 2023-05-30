package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		
		try {
			
			Book book;
			
			ps =  conn.prepareStatement("SELECT * FROM Book WHERE title = ?");
			
			ps.setString(1, title);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				authors = authorDao.retrieveAllAuthorsBook(title);
				book = new Book(rs.getString("title"),rs.getFloat("price"), rs.getString("main_genre"),
								rs.getString("place_publication"), rs.getInt("year_publication"), authors);
				
				return book;
			}
		}
		catch(SQLException e){
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
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
	public List<Book> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book listOneBook() {
		// TODO Auto-generated method stub
		return null;
	}
}
