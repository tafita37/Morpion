package wera;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

import back.*;
import mpihaino.*;
import java.awt.event.*;
import wera.*;

public class Partie
extends JPanel {
    Fenetre fenetre;
    Terrain terr;
    PanelPoint[] pnp;
    MouseListener msn;
    Socket s;
    
/// Getters and Setters 
    public Fenetre getFenetre() {
        return this.fenetre;
    }

    public void setFenetre(Fenetre nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception();
        }
        this.fenetre=nouveau;
    }

    public Terrain getTerr() {
        return this.terr;
    }

    public void setTerr(Terrain nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Terrain inexistant");
        }
        this.terr=nouveau;
    }

    public PanelPoint[] getPnp() {
        return this.pnp;
    }

    public void setPnp(PanelPoint[] nouveau)
    throws Exception {
        if(nouveau==null||nouveau.length==0) {
            throw new Exception("PanelPoint invalide");
        }
        this.pnp=nouveau;
    }

/// Constructeurs
    public Partie(Terrain terr)
    throws Exception {
        this.terr=terr;
        this.pnp=new PanelPoint[terr.getCote()*terr.getCote()];
        this.setLayout(new GridLayout(this.terr.getCote(), this.terr.getCote()));
        // for(int i=0; i<this.pnp.length; i++) {
        //     this.pnp[i]=new PanelPoint(this);
        //     ListenerPartie lp=(ListenerPartie) this.msn;
        //     lp.setPnp(this.pnp[i]);
        //     this.pnp[i].addMouseListener(new ListenerPartie(this, i));
        //     this.add(pnp[i]);
        // }
    }

/// Lire un fichier
    public static String readFile(String path) {
        BufferedReader br=null;
        FileReader filereader=null;
        String contain="";
        try {
            filereader=new FileReader(path);
            br=new BufferedReader(filereader);
            contain=br.readLine();
            if(contain==null) {
                contain="";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return contain;
    }

/*------------------------------------------Fonctions ampiasaina------------------------------------------- */
/// Graphics
    public void paint(Graphics g) {
        int largeur=this.getWidth()/3;
        int longueur=this.getHeight()/3;
        for(int i=0; i<this.terr.getCote(); i++) {
            for(int j=0; j<this.terr.getCote(); j++) {
                g.drawRect(j+largeur*j, i+longueur*i, largeur, longueur);
                if(this.terr.getPlateau()[j][i]==1) {
                    g.drawOval(j+largeur*j+5, i+longueur*i, largeur-5, longueur-5);
                } else if(this.terr.getPlateau()[j][i]==2) {
                    g.drawLine(j+largeur*j, i+longueur*i, j+largeur*j+largeur, i+longueur*i+longueur);
                    g.drawLine(j+largeur*j, i+longueur*i+longueur, j+largeur*j+largeur, i+longueur*i);
                }
            }
        }
        // this.terr.showTerrain();
        this.repaint();
    }

    public MouseListener getMsn() {
        return msn;
    }

    public void setMsn(MouseListener msn) {
        this.msn = msn;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }
}
