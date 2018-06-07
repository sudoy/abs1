package abs1.beans;

public class Category {
	private int category_id;
	private String category_data;

	public Category(int category_id, String category_data) {
		super();
		this.category_id = category_id;
		this.category_data = category_data;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_data() {
		return category_data;
	}

	public void setCategory_data(String category_data) {
		this.category_data = category_data;
	}


}
