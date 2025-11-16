import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    
    private static final String URL = "jdbc:mysql://localhost:3306/ges-etudiant";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Connexion établie");
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Driver MySQL non trouvé !");
                throw new SQLException("Driver non trouvé", e);
            } catch (SQLException e) {
                System.err.println("❌ Erreur de connexion");
                throw e;
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✅ Connexion fermée");
            } catch (SQLException e) {
                System.err.println("❌ Erreur fermeture");
                e.printStackTrace();
            }
        }
    }
}