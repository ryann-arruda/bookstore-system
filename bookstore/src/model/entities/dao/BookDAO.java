package model.entities.dao;

import java.util.List;

import model.entities.Book;

public interface BookDAO {
	boolean insert(Book book);
	Book retrieve(String title);
	boolean deleteById(int id);
	boolean update(Book book);
	List<Book> listAll();
	Book listOneBook();
	List<Book> retrieveAllBooksAuthor(String authorName);
}
