package model.entities.dao;

import db.Database;
import model.entities.dao.impl.AddressDaoJdbc;
import model.entities.dao.impl.AuthorDaoJdbc;
import model.entities.dao.impl.BookDaoJdbc;
import model.entities.dao.impl.ClientDaoJdbc;
import model.entities.dao.impl.ManagerDaoJdbc;
import model.entities.dao.impl.OrderDaoJdbc;
import model.entities.dao.impl.PaymentDaoJdbc;
import model.entities.dao.impl.SellerDaoJdbc;

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
	
	public static AuthorDAO getAuthorDAO() {
		return new AuthorDaoJdbc(Database.getConnection());
	}
	
	public static ManagerDAO getManagerDAO() {
		return new ManagerDaoJdbc(Database.getConnection());
	}
	
	public static PaymentDAO getPaymentDAO() {
		return new PaymentDaoJdbc(Database.getConnection());
	}
	
	public static SellerDAO getSellerDAO() {
		return new SellerDaoJdbc(Database.getConnection());
	}
	
	public static OrderDAO getOrderDAO() {
		return new OrderDaoJdbc(Database.getConnection());
	}
	
	
}
