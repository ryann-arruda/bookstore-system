package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.Database;
import db.DatabaseException;
import model.entities.Book;
import model.entities.Client;
import model.entities.Order;
import model.entities.Payment;
import model.entities.dao.BookDAO;
import model.entities.dao.ClientDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.OrderDAO;
import model.entities.dao.PaymentDAO;
import model.entities.enums.PaymentType;

public class OrderDaoJdbc implements OrderDAO{
	
	private Connection conn;
	
	public OrderDaoJdbc(Connection conn) {
		this.conn = conn;
	}
	
	private int retrievePaymentIdOrder(int orderId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paymentId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT payment_id FROM Order_Payment WHERE order_t_id = ?");
			ps.setInt(1, orderId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				paymentId = rs.getInt(1);
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return paymentId;
	}

	@SuppressWarnings("resource")
	@Override
	public boolean insert(Order order, PaymentType paymentType) {
		ClientDAO clientDao = null;
		BookDAO bookDao = null;
		PaymentDAO paymentDao = null;
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
					paymentDao = DAOFactory.getPaymentDAO();
					
					for(Book book: order.getItems()) {
						int bookId = bookDao.retrieveBookId(book.getTitle());
						
						ps = conn.prepareStatement("INSERT INTO Order_Book (order_t_id, book_id) VALUES (?,?)");
						ps.setInt(1, orderId);
						ps.setInt(2, bookId);
						
						rowsAffected = ps.executeUpdate();
					}
					
					int paymentId = paymentDao.insert(new Payment(order.getTotalAmount(), paymentType , new Date(), order.getClient()));
					
					ps = conn.prepareStatement("INSERT INTO Order_Payment (order_t_id, payment_id) VALUES (?,?)");
					ps.setInt(1, orderId);
					ps.setInt(2, paymentId);
					
					rowsAffected = ps.executeUpdate();
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
	
	private Payment retrievePaymentOrder(int orderId) {
		PaymentDAO paymentDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Payment payment = null;
		
		try {			
			ps = conn.prepareStatement("SELECT payment_id FROM Order_Payment WHERE order_t_id = ?");
			ps.setInt(1, orderId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				paymentDao = DAOFactory.getPaymentDAO();
				int paymentId = rs.getInt(1);
				
				payment = paymentDao.retrieveById(paymentId);
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeResultSet(rs);
			Database.closeStatement(ps);
		}
		
		return payment;
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
				
				if (rowsAffected > 0) {
					int paymentId = retrievePaymentIdOrder(id);
					ps = conn.prepareStatement("DELETE FROM Order_Payment WHERE order_t_id = ?");
					ps.setInt(1, id);
					
					rowsAffected = ps.executeUpdate();
					
					if (rowsAffected > 0) {
						ps = conn.prepareStatement("DELETE FROM Payment WHERE payment_id = ?");
						ps.setInt(1, paymentId);
						
						rowsAffected = ps.executeUpdate();
					}
				}
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
	public List<Order> listAllOrdersClient(int idClient) {
		ClientDAO clientDao = null;
		List<Order> orders = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			clientDao = DAOFactory.getClientDAO();
			
			ps = conn.prepareStatement("SELECT * FROM Order_t WHERE client_t_id = ?");
			
			ps.setInt(1, idClient);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int orderId = rs.getInt("order_t_id");
				
				Client client = clientDao.retrieve(idClient);
				
				Order order = new Order(client);
				order.setPayment(retrievePaymentOrder(orderId));
				
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
