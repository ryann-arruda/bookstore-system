package model.entities.dao;

public interface ManagerDAO {
	
	ManagerDAO retrieve(String name);
	boolean deleteById(int id);
	boolean update(ManagerDAO manager);
}
