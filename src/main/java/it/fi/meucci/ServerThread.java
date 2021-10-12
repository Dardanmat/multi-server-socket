package it.fi.meucci;
import java.net.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 * Questa classe serve da thread che utilizzerà il server per fornire servizio ad un client, un thread deve essere creato per client.
 * Questa classe forma il data socket
 * @author Dardan Matias Berisha
 */
public class ServerThread extends Thread{
 
    protected Socket client             = null;
    
    protected BufferedReader inDalClient;
    protected DataOutputStream outVersoClient;
    
    protected String stringaRicevuta    = null;
    protected String stringaModificata  = null;
    
    private ServerSocket server = null;
    
    /**
     * Costruttore per creare il server thread
     * @param client socket per servire il client(dovrebbe essere preso dal connection socket)
     * @param num numero del thread per poterli differenziare nella stampa a schermo
     */
    public ServerThread(Socket client, int num, ServerSocket server){
        super("SERVER.THREAD" + num);
        this.server = server;
        this.client = client;
        try {
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));   //associo il socket al client per effettuare la lettura
            outVersoClient  = new DataOutputStream(client.getOutputStream());                   //associo il socket al client per effettuare la scrittura
        
        } catch (Exception e) {System.out.println(super.getName() + " - Errore durante l'istanza del server");}
    }
    
    @Override
    public void run() {
        try{ //questa parte è esattamente il metodo comunica che avevamo precedentemente nella classe Server
            //prima di eseguire la prossima linea si aspetta l'arrivo di un client
            System.out.println("3 " + super.getName()+ " - Benvenuto client, ti rimanderò la stringa inviata all'incontrario. Attendo...");
            
            while(true){
                stringaRicevuta = inDalClient.readLine(); //si legge il primo messaggio che si riceve dal client (smetterà di leggere al carattere \n)
                
                if(stringaRicevuta.equalsIgnoreCase("Fine")){ //quando arriva il messaggio fine si esce dal loop e il socket verrà chiuso
                    break;
                }else if(stringaRicevuta.equalsIgnoreCase("Stop")){ //quando arriva il messaggio fine
                    server.close();
                }
                
                System.out.println("6 " + super.getName()+ " - Il messaggio è: \"" + stringaRicevuta + "\"");

                stringaModificata = invertiStringa(stringaRicevuta); //si usa il metodo per invertire la stringa
                System.out.println("7 " + super.getName()+ " - Invio la stringa modificata al client...");
                outVersoClient.writeBytes(stringaModificata + "\n"); //si invia la stringa all'incontrario al client

                System.out.println("9 " + super.getName()+ " - Fine elaborazione");
                
            }
            
            client.close(); //si chiude la comunicazione col client
            
        }catch(Exception e){
            System.out.println(super.getName()+ " - Errore durante la comunicazione");
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metodo che inverte la stringa che gli viene passa per il parametro str
     * @param str stringa da invertire
     * @return stringa inveritta
     */
    private String invertiStringa(String str){
        StringBuilder s = new StringBuilder(str);
        s.reverse();
        return s.toString();
    }
    
}
