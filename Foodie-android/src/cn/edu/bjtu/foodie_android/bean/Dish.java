package cn.edu.bjtu.foodie_android.bean;

public class Dish {
	public static final int TYPE_CHECKED = 1;
	public static final int TYPE_NOCHECKED = 0;
	private int type;
	private String name;
	private int price;

	public Dish(int type, String name, int price) {
		super();
		this.type = type;
		this.name = name;
		this.price = price;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
