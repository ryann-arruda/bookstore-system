package model.entities;

public class Address {
	private String thoroughfare;
	private String neighborhood;
	private String complement;
	private Integer number;
	private String zipCode;
	
	public Address(String thoroughfare, String neighborhood, String complement, Integer number, String zipCode) {
		this.thoroughfare = thoroughfare;
		this.neighborhood = neighborhood;
		this.complement = complement;
		this.number = number;
		this.zipCode = zipCode;
	}
	
	public String getThoroughfare() {
		return thoroughfare;
	}
	
	public String getNeighborhood() {
		return neighborhood;
	}
	
	public String getComplement() {
		return complement;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setThoroughfare(String thoroughfare) {
		this.thoroughfare = thoroughfare;
	}
	
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	public void setComplement(String complement) {
		this.complement = complement;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((thoroughfare == null) ? 0 : thoroughfare.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		Address other = (Address) obj;
		if (neighborhood == null) {
			if (other.neighborhood != null)
				return false;
		} else if (!neighborhood.equals(other.neighborhood))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (thoroughfare == null) {
			if (other.thoroughfare != null)
				return false;
		} else if (!thoroughfare.equals(other.thoroughfare))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}	
}
