package model.entities;

public class Client extends Person{
	private Boolean isOnePiece;
	private String team;
	
	public Client(Client client) {
		super(client.getName(), client.getAge(), client.getEmail(), client.getPassword(), client.getAddress());
		this.isOnePiece = client.getIsOnePiece();
		this.team = client.getTeam();
	}
	
	public Client(String name, int age, String email, Boolean isOnePiece, String team, String password, Address address) {
		super(name, age, email, password, address);
		this.isOnePiece = isOnePiece;
		this.team = team;
	}

	public Boolean getIsOnePiece() {
		return isOnePiece;
	}

	public void setIsOnePiece(Boolean isOnePiece) {
		this.isOnePiece = isOnePiece;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	public void buyBook(Order order, Book book) {
		order.addNewItem(book);
	}
}