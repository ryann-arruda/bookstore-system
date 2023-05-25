package model.entities.dao;

import model.entities.Payment;

public interface PaymentDAO {
	boolean insert(Payment payment);
	Payment retrieve(int idClient);
	boolean deleteById(int id);
	boolean update(Payment payment);
}
