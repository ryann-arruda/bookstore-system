package model.entities.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.entities.Book;
import model.entities.dao.BookDAO;

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
