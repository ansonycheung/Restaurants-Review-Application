package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import review.model.*;

/**
 * Data access object (DAO) class to interact with the underlying Companies
 * table in your MySQL instance. This is used to store {@link CompaniesDao} into
 * your MySQL instance and retrieve {@link CompaniesDao} from MySQL instance.
 */
public class CompaniesDao {
	protected ConnectionManager connectionManager;

	// Single pattern: instantiation is limited to one object.
	private static CompaniesDao instance = null;

	protected CompaniesDao() {
		connectionManager = new ConnectionManager();
	}

	public static CompaniesDao getInstance() {
		if (instance == null) {
			instance = new CompaniesDao();
		}
		return instance;
	}

	/**
	 * Save the Companies instance by storing it in your MySQL instance. This runs a
	 * INSERT statement.
	 * 
	 * @throws SQLException
	 */
	public Companies create(Companies company) throws SQLException {
		String insertCompany = "INSERT INTO Companies(CompanyName,About) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCompany);
			// PreparedStatement allows us to substitute specific types into the query
			// template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			insertStmt.setString(1, company.getCompanyName());
			insertStmt.setString(2, company.getAbout());
			// Note that we call executeUpdate(). This is used for a INSERT/UPDATE/DELETE
			// statements, and it returns an int for the row counts affected (or 0 if the
			// statement returns nothing). For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// I'll leave it as an exercise for you to write UPDATE/DELETE methods.
			insertStmt.executeUpdate();
			// Note 1: if this was an UPDATE statement, then the user fields should be
			// updated before returning to the caller.
			// Note 2: there are no auto-generated keys, so no update to perform on the
			// input param user.
			return company;
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
	 * Get the Companies record by fetching it from your MySQL instance. This runs a
	 * SELECT statement and returns a single Companies instance.
	 * 
	 * @throws SQLException
	 */
	public Companies getCompanyByCompanyName(String companyName) throws SQLException {
		String selectCompany = "SELECT CompanyName,About FROM Companies WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCompany);
			selectStmt.setString(1, companyName);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if (results.next()) {
				String resultCompanyName = results.getString("CompanyName");
				String about = results.getString("About");
				Companies company = new Companies(resultCompanyName, about);
				return company;
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
	 * Update the About of the Companies instance. This runs a UPDATE statement.
	 * 
	 * @throws SQLException
	 */
	public Companies updateAbout(Companies company, String newAbout) throws SQLException {
		String updateAbout = "UPDATE Companies SET About=? WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAbout);
			updateStmt.setString(1, newAbout);
			updateStmt.setString(2, company.getCompanyName());
			updateStmt.executeUpdate();

			// Update the company param before returning to the caller.
			company.setAbout(newAbout);
			return company;
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
	 * Delete the Companies instance. This runs a DELETE statement.
	 * 
	 * @throws SQLException
	 */
	public Companies delete(Companies company) throws SQLException {
		String deleteCompany = "DELETE FROM Companies WHERE CompanyName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCompany);
			deleteStmt.setString(1, company.getCompanyName());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Companies instance.
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
