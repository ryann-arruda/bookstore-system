package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.Database;
import db.DatabaseException;
import model.entities.Payment;
import model.entities.dao.ClientDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.PaymentDAO;
import model.entities.enums.PaymentStatus;

public class PaymentDaoJdbc implements PaymentDAO{
	private Connection conn;
	
	public PaymentDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean insert(Payment payment) {
		ClientDAO clientDao = null;
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			clientDao = DAOFactory.getClientDAO();
			
			int clientId = clientDao.retrieveClientId(payment.getClient().getEmail());
			
			if(clientId != -1) {
				ps = conn.prepareStatement("INSERT INTO Payment (total_amount, payment_status, payment_type, payment_time, client_t_id) VALUES " + 
											"(?,?,?,?,?)");
				
				ps.setFloat(1, payment.getTotalAmount());
				ps.setInt(2, payment.getStatus().ordinal());
				ps.setInt(3, payment.getPaymentType().ordinal());
				ps.setTimestamp(4, new Timestamp(payment.getPaymentTime().getTime()));
				ps.setInt(5, clientId);
				
				rowsAffected = ps.executeUpdate();
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(ps);
		}
		
		if(rowsAffected != -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public Payment retrieve(int idClient) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PaymentStatus status, int clientId) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		
		try {
			int paymentId = retrievePaymentId(clientId);
			
			if(paymentId != -1) {
				ps = conn.prepareStatement("UPDATE Payment SET payment_status = ? WHERE payment_id = ?");
				
				ps.setInt(1, status.ordinal());
				ps.setInt(2, paymentId);
				
				rowsAffected = ps.executeUpdate();
			}
			
			
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(ps);
		}
		
		if(rowsAffected != -1) {
			return true;
		}
		
		return false;
	}

	@Override
	public int retrievePaymentId(int clientId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int paymentId = -1;
		
		try {
			ps = conn.prepareStatement("SELECT payment_id FROM Payment WHERE client_t_id = ?");
			
			ps.setInt(1, clientId);
			
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
}
