import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        
        String username = "root", password = "", url = "jdbc:mysql://localhost:3306/ges-etudiant";
        System.out.println("Connexion au serveur...");
        
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Connexion réussie !");
            
            var data = con.prepareStatement("SELECT * FROM etudiants").executeQuery();
            
            System.out.println("\n📋 Liste des étudiants :");
            System.out.println("═══════════════════════════════════════════════════════");
            
            while(data.next()) {
                String nom = data.getString("nom");
                String prenom = data.getString("prenom");
                String numero = data.getString("numero_etudiant");
                Float moyenne = data.getFloat("moyenne");
                String avis = data.getString("avis");
                
                System.out.println(String.format(
                    "🎓 %s %s (%s) - Moyenne: %.2f/20 - Avis: %s", 
                    prenom, nom, numero, moyenne, avis
                ));
            }
            
            System.out.println("═══════════════════════════════════════════════════════");
            con.close();
            
        } catch(SQLException e) {
            System.err.println("❌ Erreur de connexion !");
            e.printStackTrace();
        }
    }
}