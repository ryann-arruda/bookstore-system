package model.entities.dao;

import model.entities.Payment;
import model.entities.enums.PaymentStatus;

public interface PaymentDAO {
	int insert(Payment payment);
	Payment retrieve(int idClient);
	boolean deleteById(int id);
	boolean update(PaymentStatus status, int clientId);
	int retrievePaymentId(int clientId);
}
