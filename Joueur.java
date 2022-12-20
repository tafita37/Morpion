package element;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.Vector;
import back.*;
public class Joueur
implements Serializable {
    int numero;
    String prenom;
    Terrain terr;
    boolean tour=false;

/// Getters and Setters
    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int nouveau)
    throws Exception {
        if(nouveau<=0) {
            throw new Exception("Numero de joueur trop petit");
        }
        this.numero=nouveau;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public boolean isOtherThanSpace(String word) {
        for(int i=0; i<word.length(); i++) {
            if(String.valueOf(word.charAt(i)).compareToIgnoreCase(" ")!=0) {
                return true;
            }
        }
        return false;
    }

    public void setPrenom(String nouveau)
    throws Exception {
        // if(nouveau==null||!isOtherThanSpace(nouveau)) {
        //     throw new Exception("Prenom invalide");
        // }
        this.prenom=nouveau;
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

    public boolean isTour() {
        return this.tour;
    }

    public void setTour(boolean nouveau) {
        this.tour=nouveau;
    }

/// Constructeurs
    public Joueur(){}

    public Joueur(int num, String prenom)
    throws Exception {
        if(num==2) {
            this.setTour(true);
        }
        this.setNumero(num);
        this.setPrenom(prenom);
        // this.setTerr(terr);
    }

/*------------------------------------------Fonctions ampiasaina---------------------------------- */
/// Victoire sur une partie
    public boolean checkVictorySub(Vector<Integer> vec) {
        if(vec.size()!=this.terr.getCote()) {
            return false;
        }
        for(int i=0; i<vec.size(); i++) {
            if(vec.get(i)!=this.numero) {
                return false;
            }
        }
        return true;
    }

/// Verifier victoire
    public boolean checkVictory(int x, int y) {
        Vector<Vector<Integer>> vec=new Vector<>();
        Vector<Integer> vertical=this.terr.extractSubMatriceVertical(x, y);
        Vector<Integer> horizontal=this.terr.extractSubMatriceHorizontal(x, y);
        Vector<Integer> diagonal=this.terr.extractSubMatriceDiagonal(x, y);
        Vector<Integer> antidiagonal=this.terr.extractSubMatriceAntidiagonal(x, y);
        vec.add(vertical);
        vec.add(horizontal);
        vec.add(diagonal);
        vec.add(antidiagonal);
        for(int i=0; i<vec.size(); i++) {
            if(this.checkVictorySub(vec.get(i))) {
                return true;
            }
        }
        return false;
    }

/// Placer points
    public void setPoint(int x, int y) {
        if(this.terr.isValidCoordonnee(x)&&this.terr.isValidCoordonnee(y)&&this.isTour()&&this.terr.getPlateau()[x][y]==0) {
            this.terr.getPlateau()[x][y]=this.numero;
            this.setTour(false);
            Joueur suivant=this.terr.getNextCorrespondingPlayer(this.numero);
            suivant.setTour(true);
        }
        if(this.checkVictory(x, y)) {
            System.out.println("Victoire de "+this.getPrenom());
            this.terr.setAllTurnToFalse();
        }
    }
}
