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
import model.entities.Book;
import model.entities.Client;
import model.entities.Order;
import model.entities.dao.BookDAO;
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
		ClientDAO clientDao = null;
		BookDAO bookDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsAffected = -1;
		
		try {
			clientDao = DAOFactory.getClientDAO();
			
			int clientId = clientDao.retrieveClientId(order.getClient().getEmail());
			
			if (clientId != -1) {
				ps = conn.prepareStatement("INSERT INTO Order_t (total_amount, client_t_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				
				ps.setFloat(1, order.getTotalAmount());
				ps.setInt(2, clientId);
				
				rowsAffected = ps.executeUpdate();
				
				if(rowsAffected > 0) {
					int orderId = -1;
					rs = ps.getGeneratedKeys();
					
					while(rs.next()) {
						orderId = rs.getInt(1);
					}
					
					bookDao = DAOFactory.getBookDAO();
					
					for(Book book: order.getItems()) {
						int bookId = bookDao.retrieveBookId(book.getTitle());
						
						ps = conn.prepareStatement("INSERT INTO Order_Book (order_t_id, book_id) VALUES (?,?)");
						ps.setInt(1, orderId);
						ps.setInt(2, bookId);
						
						rowsAffected = ps.executeUpdate();
					}
				}
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		if(rowsAffected != -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public Order retrive(int idClient) {
		Statement st = null;

		try {
			st = conn.createStatement();
			
			
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(st);
		}
		return null;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			ps = conn.prepareStatement("DELETE FROM Order_t WHERE order_t_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
			
			if(rowsAffected > 0) {
				ps = conn.prepareStatement("DELETE FROM Order_Book WHERE order_t_id = ?");
				ps.setInt(1, id);
				
				rowsAffected = ps.executeUpdate();
			}
		}
		
		catch(SQLException e){
			throw new DatabaseException(e.getMessage());
		}
		
		finally{
			Database.closeStatement(ps);
		}
		
		if (rowsAffected != -1) {
			return true;
		}
		
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


}
