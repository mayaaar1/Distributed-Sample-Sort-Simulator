import java.util.*; // Pour avoir ArrayList et Collections.sort (le tri)
import gridsim.*; // Le cœur du simulateur (gestion du temps et des entités)
import gridsim.net.*; // Pour la gestion des câbles réseau et de la vitesse (Link)
import eduni.simjava.*; // Pour la gestion des événements Sim_event


public class Worker extends GridSim {

    // Constructeur : Initialise l'entité Worker sur la grille
    public Worker(String name, Link link) throws Exception {
        super(name, link); // Enregistre le nom et le lien dans le simulateur
    }

    @Override
    public void body() {
        
    	// ev est l'objet qui va stocker les détails du message reçu (source, données, tag)
        Sim_event ev = new Sim_event(); 
        // objet -> stocker/lire les informations contenues dans un message reçu par une entité
    
        // Le Worker reste à l'écoute tant que la simulation est active
        while (Sim_system.running()) {
        	
        // Attente d'un message provenant du Master (bloquant)
            super.sim_get_next(ev); 
            
         // BLOC 3 : RÉCEPTION ET TRI (Le cœur du tri distribué)
         // On vérifie si le message est une liste de données à trier (Tag 1)
            if (ev.get_tag() == 1) { // TAG 1 : Réception des données à trier
            	
            	// On extrait les données du Master et on les transforme en liste Java (Cast)
                ArrayList<Integer> localList = (ArrayList<Integer>) ev.get_data();
                
   
                // Tri local (CPU) : C'est ici qu'on gagne du temps en parallélisant
                Collections.sort(localList); 
                
             // BLOC 4 : RENVOI DU RÉSULTAT
            // On prépare le colis de retour (données triées, taille en octets, destination = l'envoyeur) TAG 2 
                int masterID = GridSim.getEntityId("Master");
                IO_data response = new IO_data(localList, 500, masterID);
                
             // On renvoie le colis au Master avec le Tag 2 (pour dire : "C'est trié !")
                super.send(super.output, 0, 2, response);
            } 
            
            
         // BLOC 5 : ARRÊT DE SÉCURITÉ
        // Si le Master envoie le signal de fin, on sort de la boucle pour arrêter le Worker
            else if (ev.get_tag() == GridSimTags.END_OF_SIMULATION) {
                break; 
            }
        }
        
        
     // Libèrent la mémoire et ferment les ports de communication 
        shutdownUserEntity();
        terminateIOEntities();
    }
}