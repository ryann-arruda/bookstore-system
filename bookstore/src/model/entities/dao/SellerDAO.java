package model.entities.dao;

import java.util.List;

import model.entities.Client;
import model.entities.Seller;

public interface SellerDAO {
	
	boolean insert(Seller seller);
	Seller retrieve(String name);
	boolean deleteById(int id);
	boolean update(Seller seller, String email);
	List<Seller> listAll();
	List<Client> listAllClients();
	int retrieveSellerId(String email);
}
