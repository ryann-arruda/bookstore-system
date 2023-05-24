package model.entities.dao;

import model.entities.Client;

public interface ClientDAO {
	
	boolean insert(Client client);
	Client retrieve(String name);
	boolean deleteById(int id);
	boolean udapte(Client client);
}
