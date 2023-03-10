package mpihaino;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import element.*;
import wera.*;
public class ListenerPartie
extends MouseAdapter {
    Partie partie;
    PanelPoint pnp;
    Socket client;

    public ListenerPartie(Partie partie) {
        this.partie = partie;
    }

/// Getters and Setters
    public Partie getPartie() {
        return this.partie;
    }

    public void setPartie(Partie nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Partie inexistante");
        }
        this.partie=nouveau;
    }

    public PanelPoint getPnp() {
        return this.pnp;
    }

    public void setPnp(PanelPoint nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("PanelPoint invalide");
        }
        this.pnp=nouveau;
    }

/// Constructeurs
    public ListenerPartie(Partie p, int num, Socket s)
    throws Exception {
        this.setPartie(p);
        this.setPnp(p.getPnp()[num]);
        this.setClient(s);
    }

/// Ecrire dans un fichier
    public static void writeInFile(String path, String contenu) {
        try {
            FileWriter fw=new FileWriter(path, true);
            fw.write(contenu);
            fw.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

/*-----------------------------------------Fonctions ampiasaina---------------------------------- */
    public void mouseClicked(MouseEvent e) {
        Joueur j=this.partie.getTerr().getCorrectPlayers();
        try {
            PrintWriter pr=new PrintWriter(this.client.getOutputStream());
            if(j!=null&&j.getNumero()==1) {
                this.pnp.setProp(j);
                int x=this.pnp.getX()/(this.partie.getWidth()/this.partie.getTerr().getCote());
                int y=this.pnp.getY()/(this.partie.getHeight()/this.partie.getTerr().getCote());
                j.setPoint(x, y);
                pr.println(x+" "+y);
                pr.flush();
                if(j.checkVictory(x, y)) {
                    this.partie.setVisible(false);
                    JPanel victoire=new JPanel();
                    victoire.add(new JLabel("Victoire de "+j.getPrenom()));
                    this.partie.getFenetre().setContentPane(victoire);
                }
            } 
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        this.pnp.repaint();
        this.partie.repaint();
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }
}
