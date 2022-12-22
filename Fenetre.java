package wera;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.swing.*;
import mpihaino.*;
import wera.*;
import back.*;
import element.*;
import java.util.*;

public class Fenetre
extends JFrame {
    Partie p;
    String etat;
    ServerSocket ss;
    Socket client;

/// Getters and Setters
    public Partie getP() {
        return this.p;
    }

    public void setP(Partie nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Partie inexistante");
        }
        this.p=nouveau;
    }

/// Constructeurs
    public Fenetre(String joueur1, String joueur2, String etat)
    throws Exception {
        Joueur j1=new Joueur(1, joueur1);
        Joueur j2=new Joueur(2, joueur2);
        this.etat=etat;
        Terrain terr=new Terrain(3, j1, j2);
        this.p=new Partie(terr);
        this.p.setFenetre(this);
        if(etat.compareTo("client")==0) {
            Scanner scan=new Scanner(System.in);
            System.out.println("Entrez l'adresse ip du server");
            String ip=scan.nextLine();
            this.client=new Socket(ip, 4999);
            this.p.setS(client);
            this.setTitle("Client");
            for(int i=0; i<this.p.getPnp().length; i++) {
                this.p.getPnp()[i]=new PanelPoint(this.p);
                this.p.getPnp()[i].addMouseListener(new ListenerPartie(this.p, i, this.client));
                this.p.add(this.p.getPnp()[i]);
            }
        } else {
            this.ss=new ServerSocket(4999);
            System.out.println("En attente d'un adversaire");
            Socket s=this.ss.accept();
            this.p.setS(s);
            this.setTitle("Server");
            for(int i=0; i<this.p.getPnp().length; i++) {
                this.p.getPnp()[i]=new PanelPoint(this.p);
                this.p.getPnp()[i].addMouseListener(new ListenerServer(this.p, i, this.ss, s));
                this.p.add(this.p.getPnp()[i]);
            }
        }
        this.getContentPane().add(p);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        while(true) {
            InputStreamReader in = new InputStreamReader(this.p.getS().getInputStream());
            BufferedReader bf=new BufferedReader(in);
            String str=bf.readLine();
            String[] coordonnees=str.split(" ");
            Joueur j=this.p.getTerr().getCorrectPlayers();
            if(j!=null) {
                j.setPoint(Integer.valueOf(coordonnees[0]), Integer.valueOf(coordonnees[1]));
                if(j.checkVictory(Integer.valueOf(coordonnees[0]), Integer.valueOf(coordonnees[1]))) {
                    this.p.setVisible(false);
                    JPanel victoire=new JPanel();
                    victoire.add(new JLabel("Victoire de "+j.getPrenom()));
                    this.setContentPane(victoire);
                }
            }
            for(int i=0; i<this.p.getPnp().length; i++) {
                this.p.getPnp()[i].repaint();
            }
            this.p.repaint();
        }
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
}
