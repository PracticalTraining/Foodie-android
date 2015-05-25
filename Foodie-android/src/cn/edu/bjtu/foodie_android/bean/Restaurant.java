package cn.edu.bjtu.foodie_android.bean;

public class Restaurant {
	/** primary key generated by guid **/
	private String id;
	/** restaurantname **/
	private String name;
	/** description **/
	private String description;
	/**  location  **/
	private float longitude;
	private float latitude;
	/**   picture  **/
	private String pictureUrl;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public float getLongitude(){
    	return longitude;
    }
    public void setLongitude(float longitude){
    	this.longitude = longitude;
    }
    public float getLatitude(){
    	return latitude;
    }
    public void setLatitude(float latitude){
    	this.latitude = latitude;
    }
    public String getPictureUrl(){
    	return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl){
    	this.pictureUrl = pictureUrl;
    }

}