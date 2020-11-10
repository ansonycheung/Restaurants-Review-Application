package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import review.model.*;

/**
 * Data access object (DAO) class to interact with the underlying
 * TakeOutRestaurantsDao table in your MySQL instance. This is used to store
 * {@link TakeOutRestaurantsDao} into your MySQL instance and retrieve
 * {@link TakeOutRestaurantsDao} from MySQL instance.
 */
public class TakeOutRestaurantsDao extends RestaurantsDao {
	// Single pattern: instantiation is limited to one object.
	private static TakeOutRestaurantsDao instance = null;

	protected TakeOutRestaurantsDao() {
		super();
	}

	public static TakeOutRestaurantsDao getInstance() {
		if (instance == null) {
			instance = new TakeOutRestaurantsDao();
		}
		return instance;
	}

	/**
	 * Save the TakeOutRestaurants instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public TakeOutRestaurants create(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		// Insert into the superclass table first.
		create(new Restaurants(takeOutRestaurant.getRestaurantId(), takeOutRestaurant.getName(),
				takeOutRestaurant.getDescription(), takeOutRestaurant.getMenu(), takeOutRestaurant.getHours(),
				takeOutRestaurant.isActive(), takeOutRestaurant.getCuisineType(), takeOutRestaurant.getStreet1(),
				takeOutRestaurant.getStreet2(), takeOutRestaurant.getCity(), takeOutRestaurant.getState(),
				takeOutRestaurant.getZip(), takeOutRestaurant.getCompany()));

		String insertTakeOutRestaurant = "INSERT INTO TakeOutRestaurant(RestaurantId,MaxWaitTime) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTakeOutRestaurant);
			insertStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			insertStmt.setInt(2, takeOutRestaurant.getMaxWaitTime());
			insertStmt.executeUpdate();
			return takeOutRestaurant;
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
	 * Get the TakeOutRestaurants record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single TakeOutRestaurants
	 * instance. Note that we use CompaniesDao to retrieve the referenced Companies
	 * instance. One alternative (possibly more efficient) is using a single SELECT
	 * statement to join the TakeOutRestaurants, Companies tables and then build
	 * each object.
	 * 
	 * @throws SQLException
	 */
	public TakeOutRestaurants getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {
		// To build an SitDownRestaurants object, we need the Restaurants record, too.
		String selectTakeOutRestaurant = "SELECT TakeOutRestaurant.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, MaxWaitTime "
				+ "FROM TakeOutRestaurant INNER JOIN Restaurants "
				+ "  ON TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId "
				+ "WHERE TakeOutRestaurant.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurant);
			selectStmt.setInt(1, takeOutRestaurantId);
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
				int maxWaitTime = results.getInt("MaxWaitTime");
				TakeOutRestaurants takeOutRestaurant = new TakeOutRestaurants(resultRestaurantId, name, description,
						menu, hours, active, cuisineType, street1, street2, city, state, zip, company, maxWaitTime);
				return takeOutRestaurant;
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
	 * Get the matching TakeOutRestaurants records by fetching from your MySQL
	 * instance. This runs a SELECT statement and returns a list of matching
	 * TakeOutRestaurants.
	 * 
	 * @throws SQLException
	 */
	public List<TakeOutRestaurants> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
		List<TakeOutRestaurants> takeOutRestaurants = new ArrayList<TakeOutRestaurants>();
		String selectTakeOutRestaurants = "SELECT TakeOutRestaurant.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, MaxWaitTime "
				+ "FROM TakeOutRestaurant INNER JOIN Restaurants "
				+ "  ON TakeOutRestaurant.RestaurantId = Restaurants.RestaurantId "
				+ "WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurants);
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
				int maxWaitTime = results.getInt("MaxWaitTime");
				TakeOutRestaurants takeOutRestaurant = new TakeOutRestaurants(restaurantId, name, description, menu,
						hours, active, cuisineType, street1, street2, city, state, zip, resultCompany, maxWaitTime);
				takeOutRestaurants.add(takeOutRestaurant);
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
		return takeOutRestaurants;
	}

	/**
	 * Delete the TakeOutRestaurants instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		String deleteTakeOutRestaurant = "DELETE FROM TakeOutRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTakeOutRestaurant);
			deleteStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"No records available to delete for RestaurantId=" + takeOutRestaurant.getRestaurantId());
			}

			// Then also delete from the superclass.
			// Notes:
			// 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Administrators first.
			// 2. BlogPosts has a fk constraint on BlogUsers with the reference option
			// ON DELETE SET NULL. If the BlogPosts fk reference option was instead
			// ON DELETE RESTRICT, then the caller would need to delete the referencing
			// BlogPosts before this BlogUser can be deleted.
			// Example to delete the referencing BlogPosts:
			// List<BlogPosts> posts =
			// BlogPostsDao.getBlogPostsForUser(blogUser.getUserName());
			// for(BlogPosts p : posts) BlogPostsDao.delete(p);
			super.delete(takeOutRestaurant);

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
