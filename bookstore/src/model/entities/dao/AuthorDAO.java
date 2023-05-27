package model.entities.dao;

import model.entities.Author;

public interface AuthorDAO {
	boolean insert(Author author);
	Author retrive(String name);
	boolean deleteById(int id);
	boolean update(Author author);
}
