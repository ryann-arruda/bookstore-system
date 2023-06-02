package model.entities.dao;

import java.util.List;

import model.entities.Author;

public interface AuthorDAO {
	int insert(Author author);
	Author retrive(String email);
	boolean deleteById(int id);
	boolean update(Author author);
	List<Author> retrieveAllAuthorsBook(String bookTitle);
	int retrieveAuthorId(String email);
}
