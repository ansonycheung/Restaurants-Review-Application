package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import review.model.*;

/**
 * Data access object (DAO) class to interact with the underlying Reviews table
 * in your MySQL instance. This is used to store {@link Reviews} into your MySQL
 * instance and retrieve {@link Reviews} from MySQL instance.
 */
public class ReviewsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static ReviewsDao instance = null;

	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReviewsDao getInstance() {
		if (instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}

	/**
	 * Save the Reviews instance by storing it in your MySQL instance. This runs a
	 * INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public Reviews create(Reviews review) throws SQLException {
		String insertReview = "INSERT INTO Reviews(Created,Content,Rating,UserName,RestaurantId) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Reviews has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReview,
					Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setTimestamp(1, new Timestamp(review.getCreated().getTime()));
			insertStmt.setString(2, review.getContent());
			insertStmt.setDouble(3, review.getRating());
			insertStmt.setString(4, review.getUser().getUserName());
			insertStmt.setInt(5, review.getRestaurant().getRestaurantId());

			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if (resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			// Note 1: if this was an UPDATE statement, then the user fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param user.
			return review;
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
	 * Get the Reviews record by fetching it from your MySQL instance. This runs a
	 * SELECT statement and returns a single Reviews instance. Note that we use
	 * UsersDao and RestaurantsDao to retrieve the referenced Users and Restaurants
	 * instances. One alternative (possibly more efficient) is using a single SELECT
	 * statement to join the Reviews, Users, Restaurants tables and then build each
	 * object.
	 * 
	 * @throws SQLException
	 */
	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview = "SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " + "FROM Reviews "
				+ "WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			if (results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				Date created = new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");

				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);

				Reviews review = new Reviews(resultReviewId, created, content, rating, user, restaurant);
				return review;
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
	 * Get the all the Reviews for a userName.
	 * 
	 * @throws SQLException
	 */
	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews = "SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " + "FROM Reviews "
				+ "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while (results.next()) {
				int reviewId = results.getInt("ReviewId");
				Date created = new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");

				String resultUserName = results.getString("UserName");
				Users resultUser = usersDao.getUserByUserName(resultUserName);

				int restaurantId = results.getInt("RestaurantId");
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);

				Reviews review = new Reviews(reviewId, created, content, rating, resultUser, restaurant);
				reviews.add(review);
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
		return reviews;
	}

	/**
	 * Get the all the Reviews for a restaurantId.
	 * 
	 * @throws SQLException
	 */
	public List<Reviews> getReviewsByRestaurantId(int restaurantId) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews = "SELECT ReviewId,Created,Content,Rating,UserName,RestaurantId " + "FROM Reviews "
				+ "WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while (results.next()) {
				int reviewId = results.getInt("ReviewId");
				Date created = new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");

				String userName = results.getString("UserName");
				Users user = usersDao.getUserByUserName(userName);

				int resultRestaurantId = results.getInt("RestaurantId");
				Restaurants resultRestaurant = restaurantsDao.getRestaurantById(resultRestaurantId);

				Reviews review = new Reviews(reviewId, created, content, rating, user, resultRestaurant);
				reviews.add(review);
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
		return reviews;
	}

	/**
	 * Delete the Reviews instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
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
