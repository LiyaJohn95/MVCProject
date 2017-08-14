package com.expertzlab.bookstore;

/**
 * Created by vijeesh on 11/08/17.
 */
public class Book {
    private int id;
    private String title;
    private String description;
    private String author;
    private float price;
   
    public Book() {
    }
    
    public Book(int id) {
        this.id = id;
    }

    public Book(int id, String title, String author,String description, float price) {
        this(title, author, description, price);
        this.id = id;
    }

    public Book(String title, String author, String description,float price) {
        this.title = title;
        this.author = author;
        this.description= description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", author=" + author
				+ ", price=" + price + "]";
	}

	
}