package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import model.entities.enums.PaymentType;

public class Order {
	private float totalAmount;
	private List<Book> items;
	
	private Client client;
	private Payment payment;
	
	public Order(Client client) {
		this.totalAmount = 0.0f;
		this.items = new ArrayList<Book>();
		this.client = client;
		payment = null;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public List<Book> getItems() {
		return items;
	}

	public Client getClient() {
		return new Client(client);
	}
	
	public Payment getPayment() {
		
		if(payment != null) {
			return new Payment(payment);
		}
		
		return null;
	}
	
	public int quantityItems() {
		return items.size();
	}
	
	public void addNewItem(Book book) {
		items.add(book);
		calculateTotalAmount();
	}
	
	private void calculateTotalAmount() {
		
		for(Book b: items) {
			totalAmount += b.getPrice();
		}
	}
	
	public Payment finalizePurchase(PaymentType paymentType) {
		payment = new Payment(totalAmount, paymentType, new Date(), client);
		
		return new Payment(payment);
	}
}
