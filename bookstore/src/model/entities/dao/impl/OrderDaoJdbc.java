package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Database;
import db.DatabaseException;
import model.entities.Client;
import model.entities.Order;
import model.entities.dao.AddressDAO;
import model.entities.dao.ClientDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.OrderDAO;

public class OrderDaoJdbc implements OrderDAO{
	
	private Connection conn;
	
	public OrderDaoJdbc(Connection conn) {
		this.conn = conn;
	}

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
	public List<Order> listAllOrders(int idClient) {
		ClientDAO clientDao = DAOFactory.getClientDAO();
		List<Order> orders = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = conn.prepareStatement("SELECT * FROM Order_t WHERE client_t_id = ?");
			
			ps.setInt(1, idClient);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				Client client = clientDao.retrieve(idClient); 
				
				Order order = new Order(client);
				
				orders.add(order);
				
			}
			
		}
		
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		
		return orders;
	}

	@Override
	public Order listOneOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}
