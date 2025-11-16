public class TestEtudiant {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   TEST GESTION DES ÉTUDIANTS AVEC BASE DE DONNÉES   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");
        
        // Test 1 : Ajouter un étudiant
        System.out.println("▶ Test 1 : Ajout d'un nouvel étudiant");
        System.out.println("─────────────────────────────────────────────────────");
        Etudiant etudiant1 = new Etudiant("Dubois", "Marie", "E2024004");
        etudiant1.ajouterEtudiant();
        System.out.println();
        
        // Test 2 : Ajouter des notes
        System.out.println("▶ Test 2 : Ajout de notes");
        System.out.println("─────────────────────────────────────────────────────");
        Notation note1 = new Notation(etudiant1.getId(), "Mathématiques", 16.5f, 3);
        note1.ajouterNotation();
        
        Notation note2 = new Notation(etudiant1.getId(), "Physique", 15.0f, 2);
        note2.ajouterNotation();
        
        Notation note3 = new Notation(etudiant1.getId(), "Informatique", 18.0f, 4);
        note3.ajouterNotation();
        
        Notation note4 = new Notation(etudiant1.getId(), "Anglais", 14.5f, 2);
        note4.ajouterNotation();
        System.out.println();
        
        // Test 3 : Calculer la moyenne
        System.out.println("▶ Test 3 : Calcul de la moyenne");
        System.out.println("─────────────────────────────────────────────────────");
        etudiant1.calculerEtMettreAJourMoyenne();
        System.out.println();
        System.out.println("▶ Test 4 : Affichage des informations complètes");
        System.out.println("─────────────────────────────────────────────────────");
        etudiant1.afficherInfosCompletes();
        System.out.println("▶ Test 5 : Recherche d'un étudiant");
        System.out.println("─────────────────────────────────────────────────────");
        Etudiant recherche = Etudiant.getEtudiantParNumero("E2024001");
        if (recherche != null) {
            System.out.println("✅ Étudiant trouvé !");
            recherche.afficherInfosCompletes();
        } else {
            System.out.println("❌ Étudiant non trouvé");
        }
        System.out.println("▶ Test 6 : Ajout d'un deuxième étudiant");
        System.out.println("─────────────────────────────────────────────────────");
        Etudiant etudiant2 = new Etudiant("Petit", "Thomas", "E2024005");
        etudiant2.ajouterEtudiant();
        
        new Notation(etudiant2.getId(), "Mathématiques", 19.0f, 3).ajouterNotation();
        new Notation(etudiant2.getId(), "Physique", 18.5f, 2).ajouterNotation();
        new Notation(etudiant2.getId(), "Informatique", 17.5f, 4).ajouterNotation();
        
        etudiant2.calculerEtMettreAJourMoyenne();
        etudiant2.afficherInfosCompletes();
        
        // Fermer la connexion
        System.out.println("▶ Fermeture de la connexion");
        System.out.println("─────────────────────────────────────────────────────");
        DatabaseManager.closeConnection();
        
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                   FIN DES TESTS                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}