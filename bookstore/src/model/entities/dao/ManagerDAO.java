package model.entities.dao;

import model.entities.Manager;

public interface ManagerDAO {
	
	Manager retrieve(String name);
	boolean update(ManagerDAO manager);
}
