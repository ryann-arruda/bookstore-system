package model.entities.dao;

import java.util.List;

import model.entities.Order;
import model.entities.enums.PaymentType;

public interface OrderDAO {
	
	boolean insert(Order order, PaymentType paymentType);
	Order retrive(int idClient);
	boolean deleteById(int id);
	List<Order> listAllOrders(int idClient);
}
