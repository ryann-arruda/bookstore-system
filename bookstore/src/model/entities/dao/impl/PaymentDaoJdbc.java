package model.entities.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import db.Database;
import db.DatabaseException;
import model.entities.Client;
import model.entities.Payment;
import model.entities.dao.ClientDAO;
import model.entities.dao.DAOFactory;
import model.entities.dao.PaymentDAO;
import model.entities.enums.PaymentStatus;
import model.entities.enums.PaymentType;

public class PaymentDaoJdbc implements PaymentDAO{
	private Connection conn;
	
	public PaymentDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	@Override
	public int insert(Payment payment) {
		ClientDAO clientDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rowsAffected = -1;
		int paymentId = -1;
		
		try {
			clientDao = DAOFactory.getClientDAO();
			
			int clientId = clientDao.retrieveClientId(payment.getClient().getEmail());
			
			if(clientId != -1) {
				ps = conn.prepareStatement("INSERT INTO Payment (total_amount, payment_status, payment_type, payment_time, client_t_id) VALUES " + 
											"(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				
				ps.setFloat(1, payment.getTotalAmount());
				ps.setInt(2, payment.getStatus().ordinal());
				ps.setInt(3, payment.getPaymentType().ordinal());
				ps.setTimestamp(4, new Timestamp(payment.getPaymentTime().getTime()));
				ps.setInt(5, clientId);
				
				rowsAffected = ps.executeUpdate();
				
				if (rowsAffected > 0) {
					rs = ps.getGeneratedKeys();
					
					while(rs.next()) {
						paymentId = rs.getInt(1);
					}
				}
			}
		}
		catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		finally {
			Database.closeStatement(ps);
		}
		
		return paymentId;
	}

	@Override
	public Payment retrieve(int idClient) {
		ClientDAO clientDao = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Payment payment = null;
		
		try {
			clientDao = DAOFactory.getClientDAO();
			ps = conn.prepareStatement("SELECT * FROM Payment WHERE client_t_id = ?");
			
			ps.setInt(1, idClient);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Client client = clientDao.retrieve(idClient);
				PaymentStatus paymentStatus = PaymentStatus.values()[rs.getInt("payment_status")];
				PaymentType paymentType = PaymentType.values()[rs.getInt("payment_type")];
				
				payment = new Payment(rs.getFloat("total_amount"), paymentStatus, paymentType,
									  new Date(rs.getTimestamp("payment_time").getTime()), client);
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

	@Override
	public boolean deleteById(int id) {
		PreparedStatement ps = null;
		int rowsAffected = -1;
		try {
			ps = conn.prepareStatement("DELETE FROM Payment WHERE payment_id = ?");
			
			ps.setInt(1, id);
			
			rowsAffected = ps.executeUpdate();
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
