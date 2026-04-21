import java.util.*;
import gridsim.*;
import gridsim.net.*;

public class Main {
    public static void main(String[] args) {
        try {
            // --- CONFIGURATION ---
            int numWorkers = 4; // On définit le nombre de Workers sur la grille
            
            
            // Initialisation du système GridSim (Nb entités, Date, Trace)
            // On ajoute 1 au nombre de Workers pour compter le Master
            GridSim.init(numWorkers + 1, Calendar.getInstance(), false);

            // --- PARAMÈTRES RÉSEAU (Câblage) ---
            double baud_rate = 10000.0; // Vitesse de transmission (bits/s)
            double propDelay = 10.0;    // Délai de propagation (ms)
            int mtu = 1500;             // Taille maximale d'un paquet (Maximum Transmission Unit)
            
         // On définit un temps de fin très loin (ex: 1000 secondes) pour avoir le temps de taper
            GridSim.init(numWorkers + 1, Calendar.getInstance(), false, null, null, "1000.0");
            
            // Création du routeur indispensable pour que les entités communiquent
            //infra de commu cnx 
            Router router = new RIPRouter("Routeur_Central");

            // --- CRÉATION DU MASTER ---
            // On crée un lien physique dédié pour le Master
            Link linkMaster = new SimpleLink("Link_Master", baud_rate, propDelay, mtu);
            
            // On lance l'entité Master avec ses paramètres
            Master master = new Master("Master", linkMaster, numWorkers);
            
         // On crée un ordonnanceur de paquets pour le Master et on l'attache au routeur
            PacketScheduler schedMaster = new FIFOScheduler("Sched_Master");
            router.attachHost(master, schedMaster);

            // --- CRÉATION DES WORKERS (Boucle dynamique) ---
            for (int i = 0; i < numWorkers; i++) {
            	
                // Chaque Worker a son propre lien réseau
                Link linkWorker = new SimpleLink("Link_Worker_" + i, baud_rate, propDelay, mtu);
                Worker worker = new Worker("Worker_" + i, linkWorker);
                
                // On crée un ordonnanceur unique pour chaque Worker et on l'attache
                PacketScheduler schedWorker = new FIFOScheduler("Sched_Worker_" + i);
                router.attachHost(worker, schedWorker);

            }

            // --- DÉMARRAGE ---
            // Lance la simulation et active le body() de chaque entité
            GridSim.startGridSimulation();

        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de la grille.");
            e.printStackTrace();
        }
    }
}