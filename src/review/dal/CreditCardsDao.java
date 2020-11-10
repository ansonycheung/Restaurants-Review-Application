package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import review.model.*;

/**
 * Data access object (DAO) class to interact with the underlying CreditCards
 * table in your MySQL instance. This is used to store {@link CreditCards} into
 * your MySQL instance and retrieve {@link CreditCards} from MySQL instance.
 */
public class CreditCardsDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static CreditCardsDao instance = null;

	protected CreditCardsDao() {
		connectionManager = new ConnectionManager();
	}

	public static CreditCardsDao getInstance() {
		if (instance == null) {
			instance = new CreditCardsDao();
		}
		return instance;
	}

	/**
	 * Save the CreditCards instance by storing it in your MySQL instance. This runs
	 * a INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public CreditCards create(CreditCards creditCard) throws SQLException {
		String insertCreditCard =
			"INSERT INTO CreditCards(cardNumber,expiration,userName) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCreditCard);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setLong(1, creditCard.getCardNumber());
			insertStmt.setTimestamp(2, new Timestamp(creditCard.getExpiration().getTime()));
			insertStmt.setString(3, creditCard.getUserName());
			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();

			// Note 1: if this was an UPDATE statement, then the creditCard fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param creditCard.
			return creditCard;
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
	 * Get the CreditCards record by fetching it from your MySQL instance. This runs
	 * a SELECT statement and returns a single CreditCards instance.
	 * 
	 * @throws SQLException
	 */
	public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException {
		String selectCreditCard = 
			"SELECT CardNumber,Expiration,UserName " + 
			"FROM CreditCards "	+
			"WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCard);
			selectStmt.setLong(1, cardNumber);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if (results.next()) {
				long resultCardNumber = results.getLong("CardNumber");
				Date expiration = new Date(results.getTimestamp("Expiration").getTime());
				String userName = results.getString("UserName");
				CreditCards creditCard = new CreditCards(resultCardNumber, expiration, userName);
				return creditCard;
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
	 * Get the matching CreditCards records by fetching from your MySQL instance.
	 * This runs a SELECT statement and returns a list of matching CreditCards.
	 * 
	 * @throws SQLException
	 */
	public List<CreditCards> getCreditCardsByUserName(String userName) throws SQLException {
		List<CreditCards> creditCards = new ArrayList<CreditCards>();
		String selectCreditCards =
			"SELECT CardNumber,Expiration,UserName " +
			"FROM CreditCards " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCreditCards);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			while (results.next()) {
				long cardNumber = results.getLong("CardNumber");
				Date expiration = new Date(results.getTimestamp("Expiration").getTime());
				String resultUserName = results.getString("UserName");
				CreditCards creditCard = new CreditCards(cardNumber, expiration, resultUserName);
				creditCards.add(creditCard);
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
		return creditCards;
	}

	/**
	 * Update the Expiration of the CreditCards instance. This runs a UPDATE
	 * statement.
	 * 
	 * @throws SQLException
	 */
	public CreditCards updateExpiration(CreditCards creditCard, Date newExpiration) throws SQLException {
		String updateExpiration =
			"UPDATE CreditCards SET Expiration=? WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateExpiration);
			updateStmt.setTimestamp(1, new Timestamp(newExpiration.getTime()));
			updateStmt.setLong(2, creditCard.getCardNumber());
			updateStmt.executeUpdate();

			// Update the creditCard param before returning to the caller.
			creditCard.setExpiration(newExpiration);
			return creditCard;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (updateStmt != null) {
				updateStmt.close();
			}
		}

	}

	/**
	 * Delete the CreditCards instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public CreditCards delete(CreditCards creditCard) throws SQLException {
		String deleteCreditCard = "DELETE FROM CreditCards WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCreditCard);
			deleteStmt.setLong(1, creditCard.getCardNumber());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
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
