package model.entities.dao;

import java.util.List;

import model.entities.Order;

public interface OrderDAO {
	
	boolean insert(Order order);
	Order retrive(int idClient);
	boolean deleteById(int id);
	List<Order> listAllOrders(int idClient);
}
