package model.entities.dao.impl;

import java.util.List;

import model.entities.Order;
import model.entities.dao.OrderDAO;

public class OrderDaoJdbc implements OrderDAO{

	@Override
	public boolean insert(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Order retrive(int idClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean udapte(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Order> listAll(int idClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order listOneOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}
