package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person{
	private List<Book> books;
	
	public Author(String name, int age, String email, Address address) {
		super(name, age, email, address);
		this.books = new ArrayList<>();
	}
	
	public List<Book> getBooks(){
		return List.copyOf(books);
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
}
