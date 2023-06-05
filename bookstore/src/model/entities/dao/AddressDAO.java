package model.entities.dao;

import model.entities.Address;

public interface AddressDAO {
	int insert(Address address);
	Address retrive(int address_id);
	boolean deleteById(int id);
	int update(Address address, String zipCode);
	int retrieveAddressId(String zipCode);
}