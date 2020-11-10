package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import review.model.*;

/**
 * Data access object (DAO) class to interact with the underlying Restaurants
 * table in your MySQL instance. This is used to store {@link Restaurants} into
 * your MySQL instance and retrieve {@link Restaurants} from MySQL instance.
 */
public class RestaurantsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static RestaurantsDao instance = null;

	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RestaurantsDao getInstance() {
		if (instance == null) {
			instance = new RestaurantsDao();
		}
		return instance;
	}

	/**
	 * Save the Restaurants instance by storing it in your MySQL instance. This runs
	 * a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public Restaurants create(Restaurants restaurant) throws SQLException {
		String insertRestaurant =
			"INSERT INTO Restaurants(Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Restaurants has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRestaurant,
					Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setString(1, restaurant.getName());
			insertStmt.setString(2, restaurant.getDescription());
			insertStmt.setString(3, restaurant.getMenu());
			insertStmt.setString(4, restaurant.getHours());
			insertStmt.setBoolean(5, restaurant.isActive());
			insertStmt.setString(6, restaurant.getCuisineType().name());
			insertStmt.setString(7, restaurant.getStreet1());
			insertStmt.setString(8, restaurant.getStreet2());
			insertStmt.setString(9, restaurant.getCity());
			insertStmt.setString(10, restaurant.getState());
			insertStmt.setInt(11, restaurant.getZip());
			insertStmt.setString(12, restaurant.getCompany().getCompanyName());
			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int restaurantId = -1;
			if (resultKey.next()) {
				restaurantId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			restaurant.setRestaurantId(restaurantId);
			// Note 1: if this was an UPDATE statement, then the user fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param user.
			return restaurant;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
		}
	}

	/**
	 * Get the Restaurants record by fetching it from your MySQL instance. This runs
	 * a SELECT statement and returns a single Restaurants instance. Note that we
	 * use CompaniesDao to retrieve the referenced Companies instance. One
	 * alternative (possibly more efficient) is using a single SELECT statement to
	 * join the Restaurants, Companies tables and then build each object.
	 * 
	 * @throws SQLException
	 */
	public Restaurants getRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant =
			"SELECT RestaurantId,Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName "
			+ "FROM Restaurants " + "WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			if (results.next()) {
				int resultRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType cuisineType = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);

				Restaurants restaurant = new Restaurants(resultRestaurantId, name, description, menu, hours, active,
						cuisineType, street1, street2, city, state, zip, company);
				return restaurant;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return null;
	}

	/**
	 * Get the matching Restaurants records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Restaurants.
	 * 
	 * @throws SQLException
	 */
	public List<Restaurants> getRestaurantsByCuisine(Restaurants.CuisineType cuisine) throws SQLException {
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		String selectRestaurants =
			"SELECT RestaurantId,Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName "
			+ "FROM Restaurants " + "WHERE CuisineType=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, cuisine.toString());
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while (results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType resultCuisineType = Restaurants.CuisineType
						.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);

				Restaurants restaurant = new Restaurants(restaurantId, name, description, menu, hours, active,
						resultCuisineType, street1, street2, city, state, zip, company);
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return restaurants;
	}

	/**
	 * Get the matching Restaurants records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching Restaurants.
	 * 
	 * @throws SQLException
	 */
	public List<Restaurants> getRestaurantsByCompanyName(String companyName) throws SQLException {
		List<Restaurants> restaurants = new ArrayList<Restaurants>();
		String selectRestaurants =
			"SELECT RestaurantId,Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName "
			+ "FROM Restaurants " + "WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while (results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.CuisineType cuisineType = Restaurants.CuisineType.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				String resultCompanyName = results.getString("CompanyName");
				Companies resultCompany = companiesDao.getCompanyByCompanyName(resultCompanyName);

				Restaurants restaurant = new Restaurants(restaurantId, name, description, menu, hours, active,
						cuisineType, street1, street2, city, state, zip, resultCompany);
				restaurants.add(restaurant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (selectStmt != null) {
				selectStmt.close();
			}
			if (results != null) {
				results.close();
			}
		}
		return restaurants;
	}

	/**
	 * Delete the Restaurants instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public Restaurants delete(Restaurants restaurant) throws SQLException {
		// Note: Reservations has a fk constraint on Restaurants with the reference
		// option ON DELETE CASCADE.
		// So this delete operation will delete all the referencing Reservations.
		// Note: Recommendations has a fk constraint on Restaurants with the reference
		// option ON DELETE CASCADE.
		// So this delete operation will delete all the referencing Recommendations.
		String deleteRestaurant = "DELETE FROM Restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, restaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogComments instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}
