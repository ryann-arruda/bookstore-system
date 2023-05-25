package model.entities.dao;

import model.entities.Book;

public interface BookDAO {
	boolean insert(Book book);
	Book retrieve(String title);
	boolean deleteById(int id);
	boolean update(Book book);
	Book listOneBook();
}
