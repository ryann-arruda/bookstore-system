package model.entities.dao;

import db.Database;
import model.entities.dao.impl.AddressDaoJdbc;
import model.entities.dao.impl.BookDaoJdbc;
import model.entities.dao.impl.ClientDaoJdbc;

public class DAOFactory {
	
	public static ClientDAO getClientDAO() {
		return new ClientDaoJdbc(Database.getConnection());
	}
	
	public static AddressDAO getAddressDAO() {
		return new AddressDaoJdbc(Database.getConnection());
	}
	
	public static BookDAO getBookDAO() {
		return new BookDaoJdbc(Database.getConnection());
	}
}
