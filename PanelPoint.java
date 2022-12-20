package wera;
import javax.swing.*;
import element.*;
import java.awt.*;
public class PanelPoint
extends JPanel {
    Partie enCours;
    Joueur prop;

/// Getters and Setters
    public Partie getEnCours() {
        return this.enCours;
    }

    public void setEnCours(Partie nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Aucune partie en cours");
        }
        this.enCours=nouveau;
    }

    public Joueur getProp() {
        return this.prop;
    }

    public void setProp(Joueur nouveau)
    throws Exception {
        if(nouveau==null) {
            throw new Exception("Proprietaire inexistant");
        }
        this.prop=nouveau;
    }

/// Constructeurs
    public PanelPoint(Partie p)
    throws Exception {
        this.setEnCours(p);
        // this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        this.setBackground(Color.black);
    }

/*-------------------------------------Fonctions ampiasaina---------------------------------- */
/// Graphics
    public void paint(Graphics g) {
        if(this.prop!=null) {
            if(prop.getNumero()==1) {
                g.drawOval(5, 5, this.getWidth()-10, this.getHeight()-10);
            } else if(prop.getNumero()==2) {
                g.drawLine(0, 0, this.getWidth(), this.getHeight());
                g.drawLine(this.getWidth(), 0, 0, this.getHeight());
            }
        }
        this.repaint();
    }
}
