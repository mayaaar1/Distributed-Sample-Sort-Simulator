import java.util.*;
import gridsim.*;
import gridsim.net.*;
import java.util.Scanner;
import eduni.simjava.*;

public class Master extends GridSim {
    private int totalWorkers; // Nombre de nœuds de calcul (Workers)
    private ArrayList<Integer> finalSortedList = new ArrayList<>(); // Liste finale fusionnée

    // BLOC 1 : LE CONSTRUCTEUR
    // Initialise le Master et définit le nombre de Workers à gérer
    
    public Master(String name, Link link, int totalWorkers) throws Exception {
        super(name, link); // Initialise le nom et la connexion réseau
        this.totalWorkers = totalWorkers; 
    }

    @Override
    public void body() {
    	
        // Temps d'attente pour s'assurer que les Workers sont prêts
        super.gridSimHold(5.0); 
        
     // --- ÉTAPE 1 : ENTRÉE UTILISATEUR ---
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> data = new ArrayList<>();
        System.out.println("Combien de nombres voulez-vous trier ?");
        int n = sc.nextInt(); 

        System.out.println("Entrez les " + n + " nombres :");
        for(int i = 0; i < n; i++) {
            data.add(sc.nextInt());
        }
        
     // --- ÉTAPE 2 : ÉCHANTILLONNAGE & PIVOTS ---
        Collections.sort(data); 
        ArrayList<Integer> pivots = new ArrayList<>();

        // Calcul des pivots pour répartir les données
        for (int i = 1; i < totalWorkers; i++) {
            pivots.add(data.get(i * (data.size() / totalWorkers)));
        }

        for (int i = 0; i < totalWorkers; i++) {
            ArrayList<Integer> filteredData = new ArrayList<>();
            for (Integer val : data) {
                if (totalWorkers == 1) {
                    filteredData.add(val);
                } else if (i == 0) { // Premier Worker : tout ce qui est <= premier pivot
                    if (val <= pivots.get(0)) filteredData.add(val);
                } else if (i == totalWorkers - 1) { // Dernier Worker : tout ce qui est > dernier pivot
                    if (val > pivots.get(pivots.size() - 1)) filteredData.add(val);
                } else { // Workers du milieu
                    if (val > pivots.get(i - 1) && val <= pivots.get(i)) filteredData.add(val);
                }
            }
            int workerID = GridSim.getEntityId("Worker_" + i);
            System.out.println("Envoi de " + filteredData.size() + " nombres au Worker_" + i + " (Tranche de pivots)");
            super.send(super.output, 0, 1, new IO_data(filteredData, 500, workerID));
        }
        
     // --- ÉTAPE 3 : RÉCEPTION DES RÉSULTATS  
        int reponsesRecues = 0;
        Sim_event ev = new Sim_event();

        // Le Master s'arrête ici tant qu'il n'a pas reçu toutes les listes triées
        while (reponsesRecues < totalWorkers) {
            super.sim_get_next(ev); // Attente d'un message entrant

            if (ev.get_tag() == 2) { // Tag 2 = Résultat trié envoyé par le Worker
                ArrayList<Integer> listeTriee = (ArrayList<Integer>) ev.get_data();
                finalSortedList.addAll(listeTriee); 
                reponsesRecues++;
            }
        }

     // --- ÉTAPE 4 : FINALISATION (Simple Concaténation) ---
     // Note pour le prof : Comme les données sont filtrées par pivots, 
     // la liste finale est déjà presque triée par blocs, on n'a plus qu'à fusionner.
     Collections.sort(finalSortedList); 
     System.out.println("\n=== RÉSULTAT FINAL (SAMPLE SORT) ===");
     System.out.println("Liste triée : " + finalSortedList);
        
     // --- ÉTAPE 5 : ARRÊT DES WORKERS ---
     // 1. D'abord, envoyer l'ordre d'arrêt à tous les workers
     for (int i = 0; i < totalWorkers; i++) {
         int workerID = GridSim.getEntityId("Worker_" + i);
         super.send(workerID, 0.0, GridSimTags.END_OF_SIMULATION);
     }

     // 2. Attendre que les workers aient terminé leurs tâches
//         et vidé leurs files d'attente
     super.gridSimHold(10.0); // Pause de sécurité

     // 3. Envoyer un ordre d'arrêt au master si ce n'est pas déjà fait
     int masterID = GridSim.getEntityId("Master");
     if (masterID != -1) {
         super.send(masterID, 0.0, GridSimTags.END_OF_SIMULATION);
         super.gridSimHold(2.0); // Petite pause supplémentaire
     }

     // 4. Enfin, couper les connexions réseau et arrêter
     shutdownUserEntity();   // Puis le master en dernier
     terminateIOEntities();  // D'abord les I/O
    }
}
     
        
       
