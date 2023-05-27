package model.entities;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person{
	private List<Book> books;
	
	public Author(String name, int age, String email, String password, Address address) {
		super(name, age, email, password, address);
		this.books = new ArrayList<>();
	}
	
	public List<Book> getBooks(){
		return List.copyOf(books);
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
}
