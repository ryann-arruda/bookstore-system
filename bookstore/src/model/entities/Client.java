package model.entities;

public class Client {
	private String name;
	private int age;
	private String email;
	private Boolean isOnePiece;
	private String team;
	private String password;
	
	private Address address;
	
	public Client(String name, int age, String email, Boolean isOnePiece, String team, String password, Address address) {
		this.name = name;
		this.age = age;
		this.email = email;
		this.isOnePiece = isOnePiece;
		this.team = team;
		this.password = password;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return new Address(address);
	}

	public void setAddress(Address address) {
		this.address = new Address(address.getThoroughfare(), address.getNeighborhood(), address.getComplement(), address.getNumber(), address.getZipCode());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (age != other.age)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}
