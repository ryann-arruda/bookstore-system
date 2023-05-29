package model.entities.dao;

import java.util.List;

import model.entities.Author;

public interface AuthorDAO {
	boolean insert(Author author);
	Author retrive(String name);
	boolean deleteById(int id);
	boolean update(Author author);
	List<Author> retrieveAllAuthorsBook(String bookTitle);
}
