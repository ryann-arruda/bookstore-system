package model.entities.dao.impl;

import java.sql.Connection;

import model.entities.Author;
import model.entities.dao.AuthorDAO;

public class AuthorDaoJdbc implements AuthorDAO {
	
private Connection conn;
	
	public AuthorDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Author retrive(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Author author) {
		// TODO Auto-generated method stub
		return false;
	}

}
