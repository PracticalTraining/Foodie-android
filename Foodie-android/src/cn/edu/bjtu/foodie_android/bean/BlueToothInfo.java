package cn.edu.bjtu.foodie_android.bean;

public class BlueToothInfo {
	private String name;
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BlueToothInfo(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}

}
