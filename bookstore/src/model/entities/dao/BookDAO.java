package model.entities.dao;

import java.util.List;

import model.entities.Book;

public interface BookDAO {
	boolean insert(Book book);
	Book retrieve(String title);
	Book retrieve(int id);
	boolean deleteById(int id);
	boolean update(Book book, String currentTitle);
	List<Book> listAll();
	Book listFirstBook();
	List<Book> retrieveAllBooksAuthor(String authorName);
	int retrieveBookId(String title);
	int retrieveAmountBooks(String title);
	int retrieveAmountBooks(int id);
	List<Book> listBooksbyCity(String city);
	List<Book> listBooksByCategory(String category);
	List<Book> listBooksByPriceRange(float menor, float maior);
}