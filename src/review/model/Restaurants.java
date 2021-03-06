package review.model;

public class Restaurants {
	protected int restaurantId;
	protected String name;
	protected String description;
	protected String menu;
	protected String hours;
	protected boolean active;
	protected CuisineType cuisineType;
	protected String street1;
	protected String street2;
	protected String city;
	protected String state;
	protected int zip;
	protected Companies company;

	public enum CuisineType {
		AFRICAN, AMERICAN, ASIAN, EUROPEAN, HISPANIC
	}

	// This constructor can be used for reading records from MySQL, where we only
	// have the restaurantId, such as a foreign key reference to RestaurantId.
	// Given RestaurantId, we can fetch the full Restaurants record.
	public Restaurants(int restaurantId) {
		this.restaurantId = restaurantId;
	}

	// This constructor can be used for creating new records, where the restaurantId
	// may not be assigned yet since it is auto-generated by MySQL.
	public Restaurants(String name, String description, String menu, String hours, boolean active,
			CuisineType cuisineType, String street1, String street2, String city, String state, int zip,
			Companies company) {
		this.name = name;
		this.description = description;
		this.menu = menu;
		this.hours = hours;
		this.active = active;
		this.cuisineType = cuisineType;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.company = company;
	}

	// This constructor can be used for reading records from MySQL, where we have
	// all fields, including the RestaurantId.
	public Restaurants(int restaurantId, String name, String description, String menu, String hours, boolean active,
			CuisineType cuisineType, String street1, String street2, String city, String state, int zip,
			Companies company) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.menu = menu;
		this.hours = hours;
		this.active = active;
		this.cuisineType = cuisineType;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.company = company;
	}

	public int getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
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

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CuisineType getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(CuisineType cuisineType) {
		this.cuisineType = cuisineType;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public Companies getCompany() {
		return company;
	}

	public void setCompany(Companies company) {
		this.company = company;
	}
}
