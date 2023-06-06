package model.entities;

public class Client extends Person{
	private String password;
	private Boolean isOnePiece;
	private String team;
	
	public Client() {
		
	}
	
	public Client(Client client) {
		super(client.getName(), client.getAge(), client.getEmail(), client.getAddress());
		this.password = client.getPassword();
		this.isOnePiece = client.getIsOnePiece();
		this.team = client.getTeam();
	}
	
	public Client(String name, int age, String email, Boolean isOnePiece, String team, String password, Address address) {
		super(name, age, email, address);
		this.password = password;
		this.isOnePiece = isOnePiece;
		this.team = team;
	}

	public Boolean getIsOnePiece() {
		return isOnePiece;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getTeam() {
		return team;
	}

	public void setIsOnePiece(Boolean isOnePiece) {
		this.isOnePiece = isOnePiece;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void buyBook(Order order, Book book) {
		order.addNewItem(book);
	}
}