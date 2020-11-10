package review.model;

/**
 * SitDownRestaurants is a simple, plain old java objects (POJO). Well, almost (it
 * extends {@link Restaurants}).
 */
public class SitDownRestaurants extends Restaurants {
	protected int capacity;

	public SitDownRestaurants(int restaurantId) {
		super(restaurantId);
	}

	public SitDownRestaurants(int restaurantId, String name, String description, String menu, String hours,
			boolean active, CuisineType cuisineType, String street1, String street2, String city, String state, int zip,
			Companies company, int capacity) {
		super(restaurantId, name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip,
				company);
		this.capacity = capacity;
	}

	/** Getters and setters. */

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
