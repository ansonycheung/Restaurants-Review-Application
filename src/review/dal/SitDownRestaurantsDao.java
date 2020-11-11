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
 * SitDownRestaurantsDao table in your MySQL instance. This is used to store
 * {@link SitDownRestaurantsDao} into your MySQL instance and retrieve
 * {@link SitDownRestaurantsDao} from MySQL instance.
 */
public class SitDownRestaurantsDao extends RestaurantsDao {
	// Single pattern: instantiation is limited to one object.
	private static SitDownRestaurantsDao instance = null;

	protected SitDownRestaurantsDao() {
		super();
	}

	public static SitDownRestaurantsDao getInstance() {
		if (instance == null) {
			instance = new SitDownRestaurantsDao();
		}
		return instance;
	}

	/**
	 * Save the SitDownRestaurants instance by storing it in your MySQL instance.
	 * This runs a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public SitDownRestaurants create(SitDownRestaurants sitDownRestaurant) throws SQLException {
		// Insert into the superclass table first.
		create(new Restaurants(sitDownRestaurant.getRestaurantId(), sitDownRestaurant.getName(),
				sitDownRestaurant.getDescription(), sitDownRestaurant.getMenu(), sitDownRestaurant.getHours(),
				sitDownRestaurant.isActive(), sitDownRestaurant.getCuisineType(), sitDownRestaurant.getStreet1(),
				sitDownRestaurant.getStreet2(), sitDownRestaurant.getCity(), sitDownRestaurant.getState(),
				sitDownRestaurant.getZip(), sitDownRestaurant.getCompany()));

		String insertSitDownRestaurant =
			"INSERT INTO SitDownRestaurant(RestaurantId,Capacity) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSitDownRestaurant);
			insertStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			insertStmt.setInt(2, sitDownRestaurant.getCapacity());
			insertStmt.executeUpdate();
			return sitDownRestaurant;
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
	 * Get the SitDownRestaurants record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single SitDownRestaurants
	 * instance. Note that we use CompaniesDao to retrieve the referenced Companies
	 * instance. One alternative (possibly more efficient) is using a single SELECT
	 * statement to join the SitDownRestaurants, Companies tables and then build
	 * each object.
	 * 
	 * @throws SQLException
	 */
	public SitDownRestaurants getSitDownRestaurantById(int sitDownRestaurantId) throws SQLException {
		// To build an SitDownRestaurants object, we need the Restaurants record, too.
		String selectSitDownRestaurant =
			"SELECT SitDownRestaurant.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Capacity "
			+ "FROM SitDownRestaurant INNER JOIN Restaurants "
			+ "  ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId "
			+ "WHERE SitDownRestaurant.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSitDownRestaurant);
			selectStmt.setInt(1, sitDownRestaurantId);
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
				int capacity = results.getInt("Capacity");
				SitDownRestaurants sitDownRestaurant = new SitDownRestaurants(resultRestaurantId, name, description,
						menu, hours, active, cuisineType, street1, street2, city, state, zip, company, capacity);
				return sitDownRestaurant;
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
	public List<SitDownRestaurants> getSitDownRestaurantsByCompanyName(String companyName) throws SQLException {
		List<SitDownRestaurants> sitDownRestaurants = new ArrayList<SitDownRestaurants>();
		String selectSitDownRestaurants =
			"SELECT SitDownRestaurant.RestaurantId AS RestaurantId, Name, Description, Menu, Hours, Active, CuisineType, Street1, Street2, City, State, Zip, CompanyName, Capacity "
			+ "FROM SitDownRestaurant INNER JOIN Restaurants "
			+ "  ON SitDownRestaurant.RestaurantId = Restaurants.RestaurantId "
			+ "WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSitDownRestaurants);
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
				int capacity = results.getInt("Capacity");
				SitDownRestaurants sitDownRestaurant = new SitDownRestaurants(restaurantId, name, description, menu,
						hours, active, cuisineType, street1, street2, city, state, zip, resultCompany, capacity);
				sitDownRestaurants.add(sitDownRestaurant);
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
		return sitDownRestaurants;
	}

	/**
	 * Delete the SitDownRestaurants instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public SitDownRestaurants delete(SitDownRestaurants sitDownRestaurant) throws SQLException {
		// Note: Reservations has a fk constraint on SitDownRestaurants with the reference
		// option ON DELETE CASCADE.
		// So this delete operation will delete all the referencing Reservations.
		String deleteSitDownRestaurant =
				"DELETE FROM SitDownRestaurant WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSitDownRestaurant);
			deleteStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"No records available to delete for RestaurantId=" + sitDownRestaurant.getRestaurantId());
			}

			// Then also delete from the superclass.
			// Notes:
			// Due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Restaurants first.
			super.delete(sitDownRestaurant);

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
