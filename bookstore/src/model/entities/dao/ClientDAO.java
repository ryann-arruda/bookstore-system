package model.entities.dao;

import model.entities.Client;

public interface ClientDAO {
	
	boolean insert(Client client);
	Client retrieve(String name);
	Client retrieve(int id);
	boolean deleteById(int id);
	boolean update(Client client, String email);
	int retrieveClientId(String email);
}
