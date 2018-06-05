package abs1.beans;

import java.sql.Date;

public class Abs1 {

	private int id;
	private Date date;
	private int category;
	private String note;
	private int price;



	public Abs1(int id, Date date, int categoryId, String note, int price) {
		super();
		this.id = id;
		this.date = date;
		this.category = categoryId;
		this.note = note;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCategoryId() {
		return category;
	}
	public void setCategoryId(int categoryId) {
		this.category = categoryId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
