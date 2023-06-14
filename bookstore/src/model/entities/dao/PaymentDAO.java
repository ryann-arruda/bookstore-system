package model.entities.dao;

import java.util.List;

import model.entities.Payment;

public interface PaymentDAO {
	int insert(Payment payment);
	public List<Payment> retrieveAllPaymentsClient(int idClient);	
	boolean update(List<Payment> payments);
	Payment retrieveById(int paymentId);
}
