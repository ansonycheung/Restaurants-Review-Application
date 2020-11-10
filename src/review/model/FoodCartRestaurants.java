package review.model;

/**
 * FoodCartRestaurants is a simple, plain old java objects (POJO). Well, almost
 * (it extends {@link Restaurants}).
 */
public class FoodCartRestaurants extends Restaurants {
	protected boolean licensed;

	public FoodCartRestaurants(int restaurantId) {
		super(restaurantId);
	}

	public FoodCartRestaurants(int restaurantId, String name, String description, String menu, String hours,
			boolean active, CuisineType cuisineType, String street1, String street2, String city, String state, int zip,
			Companies company, boolean licensed) {
		super(restaurantId, name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip,
				company);
		this.licensed = licensed;
	}

	/** Getters and setters. */

	public boolean isLicensed() {
		return licensed;
	}

	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}

}
