package back;
import element.*;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;

public class Terrain
implements Serializable {
    int cote=3;
    int[][] plateau=new int[this.cote][this.cote];
    Vector<Joueur> listJoueur=new Vector<Joueur>();

/// Getters and Setters
    public int getCote() {
        return this.cote;
    }

    public void setCote(int nouveau)
    throws Exception {
        if(nouveau<=0) {
            throw new Exception("nombre de cote invalide");
        }
        this.cote=nouveau;
    }

    public int[][] getPlateau() {
        return this.plateau;
    }

    public void setPlateau(int[][] nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Terrain inexistant");
        }
        this.plateau=nouveau;
    }

    public Vector<Joueur> getListJoueur() {
        return this.listJoueur;
    }

    public void setListJoueur(Vector<Joueur> nouveau)
    throws Exception {
        if(nouveau==null||nouveau.size()<=1) {
            throw new Exception("Joueur inexistant ou nombre de joueur trop petit");
        }
        this.listJoueur=nouveau;
    }

/*---------------------------------------------Prerequis------------------------------------- */
/// Creer le terrain
    public void createTerrain() {
        for(int i=0; i<cote; i++) {
            for(int j=0; j<cote; j++) {
                this.plateau[i][j]=0;
            }
        }
    }

/// Constructeurs
    public Terrain(int cote, Joueur joueur1, Joueur joueur2)
    throws Exception {
        this.setCote(cote);
        this.createTerrain();
        joueur1.setTerr(this);
        joueur2.setTerr(this);
        this.listJoueur.add(joueur1);
        this.listJoueur.add(joueur2);
    }

/*--------------------------------------------Ampiasaina------------------------------------ */
/// Afficher terrain
    public void showTerrain() {
        for(int i=0; i<cote; i++) {
            for(int j=0; j<cote; j++) {
                System.out.print(this.plateau[j][i]+"|");
            }
            System.out.println();
        }
    }

/// Verifier coordonnee valide
    public boolean isValidCoordonnee(int xOuY) {
        if(xOuY>=0&&xOuY<this.cote) {
            return true;
        }
        return false;
    }

/// Recuperer le joueur correspondant a un numero
    public Joueur getNextCorrespondingPlayer(int numero) {
        if(numero==this.listJoueur.size()) {
            numero=0;
        }
        for(int i=0; i<this.listJoueur.size(); i++) {
            if(this.listJoueur.get(i).getNumero()==numero+1) {
                return this.listJoueur.get(i);
            }
        }
        return null;
    }

/// Extraire une sous matrice verticale
    public Vector<Integer> extractSubMatriceVertical(int x, int y) {
        Vector<Integer> result=new Vector<Integer>();
        for(int i=0; i<cote; i++) {
            result.add(this.plateau[x][i]);
        }
        return result;
    }

/// Extraire une sous matrice horizontal
    public Vector<Integer> extractSubMatriceHorizontal(int x, int y) {
        Vector<Integer> result=new Vector<Integer>();
        for(int i=0; i<this.cote; i++) {
            result.add(this.plateau[i][y]);
        }
        return result;
    }

/// Extraire une sous matrice diagonal
    public Vector<Integer> extractSubMatriceDiagonal(int x, int y) {
        Vector<Integer> result=new Vector<Integer>();
        while(x<this.cote-1&&y>0) {
            x++;
            y--;
        }
        while(x>-1&&y<this.cote) {
            result.add(this.plateau[x][y]);
            x--;
            y++;
        }
        return result;
    }

/// Extraire une sous matrice antidiagonal
    public Vector<Integer> extractSubMatriceAntidiagonal(int x, int y) {
        Vector<Integer> result=new Vector<Integer>();
        while(x>0&&y>0) {
            x--;
            y--;
        }
        while(x<this.cote&&y<this.cote) {
            result.add(this.plateau[x][y]);
            x++;
            y++;
        }
        return result;
    }

/// Recuperer le joueur courant
    public Joueur getCorrectPlayers() {
        for(int i=0; i<this.listJoueur.size(); i++) {
            if(this.listJoueur.get(i).isTour()) {
                return this.listJoueur.get(i);
            }
        }
        return null;
    }

/// Remettre tout les tours à false
    public void setAllTurnToFalse() {
        for(int i=0; i<this.listJoueur.size(); i++) {
            this.listJoueur.get(i).setTour(false);
        }
    }

    /// Concat listPoints
        public String concatListPoints() {
            String result="";
            for(int i=0; i<this.plateau.length; i++) {
                for(int j=0; j<this.plateau[i].length; j++) {
                    result=result+this.plateau[i][j];
                }
                if(i!=this.plateau.length-1) {
                    result=result+"/";
                }
            }
            return result;
        }
    
    // /// Mandefa terrain
    //     public void sendTerrain() {
    //         Socket s=null;
    //         PrintWriter pr=null;
    //         try {
    //             s=new Socket("localhost", 4998);
    //             pr=new PrintWriter(s.getOutputStream());
    //             pr.println(this.concatListPoints());
    //             pr.flush();
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
}