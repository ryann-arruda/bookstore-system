package model.entities;

import java.util.List;

public class Book {
	private String title;
	private float price;
	private String mainGenre;
	private String placePublication;
	private int yearPublication;
	private List<String> authors;
	
	public Book(String title, float price, String mainGenre, String placePublication, int yearPublication, List<String> authors) {
		this.title = title;
		this.price = price;
		this.mainGenre = mainGenre;
		this.placePublication = placePublication;
		this.yearPublication = yearPublication;
		this.authors = authors;
	}
	
	public String getName() {
		return title;
	}
	
	public float getPrice() {
		return price;
	}
	
	public String getMainGenre() {
		return mainGenre;
	}
	
	public String getPlacePublication() {
		return placePublication;
	}
	
	public int getYearPublication() {
		return yearPublication;
	}
	
	public List<String> getAuthors(){
		return List.copyOf(authors);
	}
	
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((mainGenre == null) ? 0 : mainGenre.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (mainGenre == null) {
			if (other.mainGenre != null)
				return false;
		} else if (!mainGenre.equals(other.mainGenre))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}