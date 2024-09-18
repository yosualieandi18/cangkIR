package apps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

	public static String jdbcUrl = "jdbc:mysql://localhost:3306/cangkir";
	public static String dbUser = "root";
	public static String dbPassword = "";

	public static Connection connect() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
	}

	public static void insertDataUser(String userId, String username, String email, String password, String gender, String role) {
		PreparedStatement preparedStatement = null;
		try {
			Connection connection = connect();
			String sql = "INSERT INTO msuser (UserId, Username, UserEmail, UserPassword, UserGender, UserRole) VALUES (?, ?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, gender);
			preparedStatement.setString(6, role);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void InsertCart(String UserId, String CupId, Integer Quantity, Integer Quantities) {
		PreparedStatement preparedStatement = null;
		try {
			Connection connection = connect();
			String sql = "INSERT INTO cart (UserID, CupID, Quantity) VALUES (?, ?, ?)"
					+ " ON DUPLICATE KEY UPDATE Quantity = Quantity + ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, UserId);
			preparedStatement.setString(2, CupId);
			preparedStatement.setInt(3, Quantity);
			preparedStatement.setInt(4, Quantities);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void InsertNewCup(String CupId, String CupName, Integer CupPrice) {
		PreparedStatement preparedStatement = null;
		try {
			Connection connection = connect();
			String sql = "INSERT INTO mscup (CupID, CupName, CupPrice) VALUES (?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, CupId);
			preparedStatement.setString(2, CupName);
			preparedStatement.setInt(3, CupPrice);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void InsertTrHeader(String TransactionID, String UserID, String CourierID, String TransactionDate, Integer Insurance) {
		PreparedStatement preparedStatement = null;
		try {
			Connection connection = connect();
			String sql = "INSERT INTO transactionheader (TransactionID, UserID, CourierID, TransactionDate, UseDeliveryInsurance) VALUES (?, ?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, TransactionID);
			preparedStatement.setString(2, UserID);
			preparedStatement.setString(3, CourierID);
			preparedStatement.setString(4, TransactionDate);
			preparedStatement.setInt(5, Insurance);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void InsertTrDetail(String TransactionID, String CupID, Integer Quantity) {
		PreparedStatement preparedStatement = null;
		try {
			Connection connection = connect();
			String sql = "INSERT INTO transactiondetail (TransactionID, CupID, Quantity) VALUES (?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, TransactionID);
			preparedStatement.setString(2, CupID);
			preparedStatement.setInt(3, Quantity);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getUsername(String username) {
        String query = "SELECT Username FROM msuser WHERE Username = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("Username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
	
	public static String getUserID(String username) {
        String query = "SELECT UserID FROM msuser WHERE Username = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
	
	public static String getUserRole(String username) {
        String query = "SELECT UserRole FROM msuser WHERE Username = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("UserRole");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
	
	public static String getCourierPrice(String courierName) {
		String query = "SELECT CourierPrice FROM mscourier WHERE CourierName = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courierName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("CourierPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return courierName;
		
	}
	
	public static String getCourierID(String courierName) {
		String query = "SELECT CourierID FROM mscourier WHERE CourierName = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, courierName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("CourierID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return courierName;
		
	}
	
	public static String getCupID(String cupName) {
        String query = "SELECT CupID FROM mscup WHERE CupName = ? ";
        try (
        	Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cupName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("CupID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cupName;
    }
	
	public void deleteData(String CupID) {
		PreparedStatement preparedStatement = null;

		try {
			Connection connection = connect();
			String sql = "DELETE FROM cart WHERE CupID = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, CupID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCupData(String CupID) {
		PreparedStatement preparedStatement = null;

		try {
			Connection connection = connect();
			String sql = "DELETE FROM mscup WHERE CupID = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, CupID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void EmptyCartData(String UserID) {
		PreparedStatement preparedStatement = null;

		try {
			Connection connection = connect();
			String sql = "DELETE FROM cart WHERE UserID = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, UserID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateCupData(Integer CupPrice, String CupID) {
		PreparedStatement preparedStatement = null;

		try {
			Connection connection = connect();
			String sql = "UPDATE mscup SET CupPrice = ? WHERE CupID = ? ";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, CupPrice);
			preparedStatement.setString(2, CupID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}