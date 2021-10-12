package it.fi.meucci;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Classe che si occupa di creare e gestire un server, riceve un singolo client e inverte la stringa che gli viene inviata
 * @author Dardan Matias Berisha
 */
public class Server {
    
    protected ServerSocket server       = null;
    protected ArrayList<ServerThread> clienti;
    
    /*
    protected Socket client             = null;
    protected String stringaRicevuta    = null;
    protected String stringaModificata  = null;
    protected BufferedReader inDalClient;
    protected DataOutputStream outVersoClient;
    */

    protected int porta;
    
    public Server(int porta) {
        this.porta = porta;
        clienti = new ArrayList<>();
    }
    
    /**
     * Metodo che fa partire e mette in attesa di un client il server
     * @return Ritorna il socket del client
     */
    public ArrayList<ServerThread> attendi(){
        try {
            
            System.out.println("1 SERVER - partito in esecuzione...");
            server = new ServerSocket(porta); //creazione di un server su una porta, quest'oggetto socket è il connection socket
            System.out.println("1 SERVER - Attesa arrivo client...");
            
            for (int i = 0; i < 5; i++) {
                clienti.add(new ServerThread(server.accept(), i+1, server));//rimane in attesa di un client e quando arriva lo i inserisce nell'array di clienti
                clienti.get(clienti.size()-1).start();//faccio partire il thread così il client può essere servito
            }

        } catch (Exception e) {System.out.println("SERVER - Errore durante l'istanza del server");}
        
        return clienti;
    }  
    
}
