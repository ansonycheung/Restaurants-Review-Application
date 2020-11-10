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
 * Data access object (DAO) class to interact with the underlying Recommendations table in
 * your MySQL instance. This is used to store {@link Recommendations} into your
 * MySQL instance and retrieve {@link Recommendations} from MySQL instance.
 */
public class RecommendationsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static RecommendationsDao instance = null;

	protected RecommendationsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RecommendationsDao getInstance() {
		if (instance == null) {
			instance = new RecommendationsDao();
		}
		return instance;
	}

	/**
	 * Save the Recommendations instance by storing it in your MySQL instance. This
	 * runs a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public Recommendations create(Recommendations recommendation) throws SQLException {
		String insertRecommendation = "INSERT INTO Recommendations(UserName,RestaurantId) " + "VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Recommendations has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRecommendation,
					Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setString(1, recommendation.getUser().getUserName());
			insertStmt.setInt(2, recommendation.getRestaurant().getRestaurantId());

			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if (resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(recommendationId);
			// Note 1: if this was an UPDATE statement, then the user fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param user.
			return recommendation;
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
	 * Get the Recommendations record by fetching it from your MySQL instance. This
	 * runs a SELECT statement and returns a single Recommendations instance. Note
	 * that we use UsersDao and RestaurantsDao to retrieve the referenced Users and
	 * Restaurants instances. One alternative (possibly more efficient) is using a
	 * single SELECT statement to join the Reviews, Users, Restaurants tables and
	 * then build each object.
	 * 
	 * @throws SQLException
	 */
	public Recommendations getRecommendationById(int recommendationId) throws SQLException {
		String selectRecommendation =
				"SELECT RecommendationId,UserName,RestaurantId "
				+ "FROM Recommendations "
				+ "WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			if (results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");

				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);

				Recommendations recommendation = new Recommendations(resultRecommendationId, user, restaurant);
				return recommendation;
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
	 * Get the all the Recommendations for a userName.
	 * 
	 * @throws SQLException
	 */
	public List<Recommendations> getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<Recommendations>();
		String selectRecommendations = 
				"SELECT RecommendationId,UserName,RestaurantId "
				+ "FROM Recommendations "
				+ "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while (results.next()) {
				int recommendationId = results.getInt("RecommendationId");
				
				String resultUserName = results.getString("UserName");
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				
				int restaurantId = results.getInt("RestaurantId");
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				
				Recommendations recommendation = new Recommendations(recommendationId, resultUser, restaurant);
				recommendations.add(recommendation);
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
		return recommendations;
	}

	/**
	 * Get the all the Recommendations for a restaurantId.
	 * @throws SQLException 
	 */
	public List<Recommendations> getRecommendationsByRestaurantId(int restaurantId) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<Recommendations>();
		String selectRecommendations = 
				"SELECT RecommendationId,UserName,RestaurantId "
				+ "FROM Recommendations "
				+ "WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while (results.next()) {
				int recommendationId = results.getInt("RecommendationId");
								
				String userName = results.getString("UserName");
				Users user = usersDao.getUserByUserName(userName);
				
				int resultRestaurantId = results.getInt("RestaurantId");
				Restaurants resultRestaurant = restaurantsDao.getRestaurantById(resultRestaurantId);
				
				Recommendations recommendation = new Recommendations(recommendationId, user, resultRestaurant);
				recommendations.add(recommendation);
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
		return recommendations;
	}

	/**
	 * Delete the Recommendations instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public Recommendations delete(Recommendations recommendation) throws SQLException {
		String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
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
