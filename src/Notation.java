import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Notation {
    private int id;
    private int etudiantId;
    private String matiere;
    private float note;
    private float coefficient;
    
    public Notation(int etudiantId, String matiere, float note, float coefficient) {
        this.etudiantId = etudiantId;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
    }
    
    public Notation(int id, int etudiantId, String matiere, float note, float coefficient) {
        this.id = id;
        this.etudiantId = etudiantId;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
    }

    public int getId() { return id; }
    public int getEtudiantId() { return etudiantId; }
    public String getMatiere() { return matiere; }
    public float getNote() { return note; }
    public float getCoefficient() { return coefficient; }

    public boolean ajouterNotation() {
        String sql = "INSERT INTO notations (etudiant_id, matiere, note, coefficient) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, etudiantId);
            pstmt.setString(2, matiere);
            pstmt.setFloat(3, note);
            pstmt.setFloat(4, coefficient);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                System.out.println("✅ Note ajoutée : " + matiere + " - " + note + "/20");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur ajout note");
            e.printStackTrace();
        }
        return false;
    }
    public static List<Notation> getNotationsParEtudiant(int etudiantId) {
        List<Notation> notations = new ArrayList<>();
        String sql = "SELECT * FROM notations WHERE etudiant_id = ?";
        
        try (Connection con = DatabaseManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, etudiantId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Notation n = new Notation(
                    rs.getInt("id"),
                    rs.getInt("etudiant_id"),
                    rs.getString("matiere"),
                    rs.getFloat("note"),
                    rs.getFloat("coefficient")
                );
                notations.add(n);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur récupération notes");
            e.printStackTrace();
        }
        return notations;
    }
    
    public void afficherInfos() {
        System.out.println("  📚 " + matiere + " : " + note + "/20 (coef " + coefficient + ")");
    }
}