import java.sql.*;
import java.util.List;

public class Etudiant {
    private int id;
    private String nom;
    private String prenom;
    private String numeroEtudiant;
    private float moyenne;
    private String avis;
    
    public Etudiant(String nom, String prenom, String numeroEtudiant) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.moyenne = 0;
        this.avis = "Non calculé";
    }
    
    public Etudiant(int id, String nom, String prenom, String numeroEtudiant, float moyenne, String avis) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroEtudiant = numeroEtudiant;
        this.moyenne = moyenne;
        this.avis = avis;
    }
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getNumeroEtudiant() { return numeroEtudiant; }
    public float getMoyenne() { return moyenne; }
    public String getAvis() { return avis; }
    public boolean ajouterEtudiant() {
        String sql = "INSERT INTO etudiants (nom, prenom, numero_etudiant, moyenne, avis) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, numeroEtudiant);
            pstmt.setFloat(4, moyenne);
            pstmt.setString(5, avis);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                System.out.println("✅ Étudiant ajouté : " + prenom + " " + nom + " (ID: " + id + ")");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout étudiant");
            e.printStackTrace();
        }
        return false;
    }
    public static Etudiant getEtudiantParNumero(String numeroEtudiant) {
        String sql = "SELECT * FROM etudiants WHERE numero_etudiant = ?";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroEtudiant);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Etudiant(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("numero_etudiant"),
                    rs.getFloat("moyenne"),
                    rs.getString("avis")
                );
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur recherche étudiant");
            e.printStackTrace();
        }
        return null;
    }

    public boolean calculerEtMettreAJourMoyenne() {
        List<Notation> notations = Notation.getNotationsParEtudiant(id);
        
        if (notations.isEmpty()) {
            System.out.println("⚠️ Aucune note");
            return false;
        }
        
        float sommeNotes = 0;
        float sommeCoef = 0;
        
        for (Notation n : notations) {
            sommeNotes += n.getNote() * n.getCoefficient();
            sommeCoef += n.getCoefficient();
        }
        
        this.moyenne = sommeNotes / sommeCoef;
        if (moyenne >= 16) {
            this.avis = "Très bien";
        } else if (moyenne >= 14) {
            this.avis = "Bien";
        } else if (moyenne >= 12) {
            this.avis = "Assez bien";
        } else if (moyenne >= 10) {
            this.avis = "Passable";
        } else {
            this.avis = "Insuffisant";
        }
        
        // Mise à jour BD
        String sql = "UPDATE etudiants SET moyenne = ?, avis = ? WHERE id = ?";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setFloat(1, moyenne);
            pstmt.setString(2, avis);
            pstmt.setInt(3, id);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✅ Moyenne : " + String.format("%.2f", moyenne) + "/20 - " + avis);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur MAJ moyenne");
            e.printStackTrace();
        }
        return false;
    }
    public void afficherInfosCompletes() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("🎓 " + prenom + " " + nom + " (" + numeroEtudiant + ")");
        System.out.println("📊 Moyenne : " + String.format("%.2f", moyenne) + "/20");
        System.out.println("🏆 Avis : " + avis);
        System.out.println("────────────────────────────────────────");
        
        List<Notation> notations = Notation.getNotationsParEtudiant(id);
        
        if (notations.isEmpty()) {
            System.out.println("⚠️ Aucune note");
        } else {
            System.out.println("📚 Notes :");
            for (Notation n : notations) {
                n.afficherInfos();
            }
        }
        System.out.println("════════════════════════════════════════\n");
    }
}