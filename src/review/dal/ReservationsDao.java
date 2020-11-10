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
 * Data access object (DAO) class to interact with the underlying Reservations
 * table in your MySQL instance. This is used to store {@link Reservations} into
 * your MySQL instance and retrieve {@link Reservations} from MySQL instance.
 */
public class ReservationsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static ReservationsDao instance = null;

	protected ReservationsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReservationsDao getInstance() {
		if (instance == null) {
			instance = new ReservationsDao();
		}
		return instance;
	}

	/**
	 * Save the Reservations instance by storing it in your MySQL instance. This
	 * runs a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public Reservations create(Reservations reservation) throws SQLException {
		String insertReservation = "INSERT INTO Reservations(Start,End,Size,UserName,RestaurantId) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Reservations has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReservation,
					Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setTimestamp(1, new Timestamp(reservation.getStart().getTime()));
			insertStmt.setTimestamp(2, new Timestamp(reservation.getEnd().getTime()));
			insertStmt.setInt(3, reservation.getSize());
			insertStmt.setString(4, reservation.getUser().getUserName());
			insertStmt.setInt(5, reservation.getSitDownRestaurants().getRestaurantId());

			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();

			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reservationId = -1;
			if (resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reservation.setReservationId(reservationId);
			// Note 1: if this was an UPDATE statement, then the user fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param user.
			return reservation;
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
	 * Get the Reservations record by fetching it from your MySQL instance. This
	 * runs a SELECT statement and returns a single Reservations instance. Note that
	 * we use UsersDao and SitDownRestaurantsDao to retrieve the referenced Users
	 * and SitDownRestaurants instances. One alternative (possibly more efficient)
	 * is using a single SELECT statement to join the Reservations, Users,
	 * SitDownRestaurants tables and then build each object.
	 * 
	 * @throws SQLException
	 */
	public Reservations getReservationById(int reservationId) throws SQLException {
		String selectReservation = "SELECT ReservationId,Start,End,Size,UserName,RestaurantId " + "FROM Reservations "
				+ "WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservation);
			selectStmt.setInt(1, reservationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
			if (results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Date start = new Date(results.getTimestamp("Start").getTime());
				Date end = new Date(results.getTimestamp("End").getTime());
				int size = results.getInt("Size");
				String userName = results.getString("UserName");
				int sitDownRestaurantId = results.getInt("RestaurantId");

				Users user = usersDao.getUserByUserName(userName);
				SitDownRestaurants sitDownRestaurant = sitDownRestaurantsDao
						.getSitDownRestaurantById(sitDownRestaurantId);

				Reservations reservation = new Reservations(resultReservationId, start, end, size, user,
						sitDownRestaurant);
				return reservation;
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
	 * Get the all the Reservations for a userName.
	 * 
	 * @throws SQLException
	 */
	public List<Reservations> getReservationsByUserName(String userName) throws SQLException {
		List<Reservations> reservations = new ArrayList<Reservations>();
		String selectReservations = "SELECT ReservationId,Start,End,Size,UserName,RestaurantId " + "FROM Reservations "
				+ "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
			while (results.next()) {
				int reservationId = results.getInt("ReservationId");
				Date start = new Date(results.getTimestamp("Start").getTime());
				Date end = new Date(results.getTimestamp("End").getTime());
				int size = results.getInt("Size");

				String resultUserName = results.getString("UserName");
				Users resultUser = usersDao.getUserByUserName(resultUserName);

				int sitDownRestaurantId = results.getInt("RestaurantId");
				SitDownRestaurants sitDownRestaurant = sitDownRestaurantsDao
						.getSitDownRestaurantById(sitDownRestaurantId);

				Reservations reservation = new Reservations(reservationId, start, end, size, resultUser,
						sitDownRestaurant);
				reservations.add(reservation);
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
		return reservations;
	}

	/**
	 * Get the all the Reservations for a restaurantId.
	 * 
	 * @throws SQLException
	 */
	public List<Reservations> getReservationsBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
		List<Reservations> reservations = new ArrayList<Reservations>();
		String selectReservations = "SELECT ReservationId,Start,End,Size,UserName,RestaurantId "
		+ "FROM Reservations "
		+ "WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setInt(1, sitDownRestaurantId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
			while (results.next()) {
				int reservationId = results.getInt("ReservationId");
				Date start = new Date(results.getTimestamp("Start").getTime());
				Date end = new Date(results.getTimestamp("End").getTime());
				int size = results.getInt("Size");

				String userName = results.getString("UserName");
				Users user = usersDao.getUserByUserName(userName);

				int resultSitDownRestaurantId = results.getInt("RestaurantId");
				SitDownRestaurants resultSitDownRestaurant = sitDownRestaurantsDao
						.getSitDownRestaurantById(resultSitDownRestaurantId);

				Reservations reservation = new Reservations(reservationId, start, end, size, user,
						resultSitDownRestaurant);
				reservations.add(reservation);
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
		return reservations;
	}

	/**
	 * Delete the Reservations instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public Reservations delete(Reservations reservation) throws SQLException {
		String deleteReservation = "DELETE FROM Reservations WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReservation);
			deleteStmt.setInt(1, reservation.getReservationId());
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
