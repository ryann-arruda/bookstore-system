package model.entities.dao;

import model.entities.Address;

public interface AddressDAO {
	boolean insert(Address address);
	Address retrive(String name);
	boolean deleteById(int id);
	boolean update(Address address);
	
}
