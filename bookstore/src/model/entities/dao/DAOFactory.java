package model.entities.dao;

import java.sql.Connection;

import model.entities.dao.impl.AddressDaoJdbc;
import model.entities.dao.impl.ClientDaoJdbc;

public interface DAOFactory {
	
	static ClientDAO getClientDAO(Connection conn) {
		return new ClientDaoJdbc(conn);
	}
	
	static AddressDAO getAddressDAO(Connection conn) {
		return new AddressDaoJdbc(conn);
	}
}
