package model.entities.dao;

import java.util.List;

import model.entities.Client;
import model.entities.Seller;

public interface SellerDAO {
	
	boolean insert(Seller seller);
	Seller retrieve(String name);
	boolean deleteById(int id);
	boolean update(Seller seller);
	List<Seller> listAll();
	Client listOneClient();
	int retrieveSellerId(String email);
}
