package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private float totalAmount;
	private List<Book> items;
	
	private Client client;
	
	public Order(Client client) {
		this.totalAmount = 0.0f;
		this.items = new ArrayList<Book>();
		this.client = client;
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
	
	public void addNewItem(Book book) {
		items.add(book);
		calculateTotalAmount();
	}
	
	private void calculateTotalAmount() {
		
		for(Book b: items) {
			totalAmount += b.getPrice();
		}
	}
}
