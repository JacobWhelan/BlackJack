import javax.imageio.ImageIO;
import java.io.Serializable;

import java.awt.image.*;

public class Card implements Serializable {

    static final long serialVersionUID = 1L;

    private int value;

    private int suit;

    private int ID;

    public BufferedImage getImage() {
        BufferedImage img;

        try {
            img = ImageIO.read(this.getClass().getResourceAsStream("/png/" + this.toString() + ".png"));
        } catch(Exception e) {
            e.printStackTrace();
            img = null;
        }

        return img;
    }

    public Card(int id) {
        this.ID = id;
        this.suit = (id/13) + 1;
        this.value = (id%13) + 1;
    }

    public int getID() {
        return ID;
    }

    public int cardValue() {
        switch(this.value) {
            case 1: return 11;

            case 11: return 10;

            case 12: return 10;

            case 13: return 10;

            default: return this.value;
        }
    }

    public int getValue() {
        return value;
    }

    public int getSuit() {
        return this.suit;
    }

    @Override
    public String toString() {
        String suitName = "";
        String valueName = "";

        switch(getSuit()) {
            case 1:
                suitName = "Hearts";
                break;
            
            case 2:
                suitName = "Spades";
                break;
            
            case 3:
                suitName = "Diamonds";
                break;

            case 4: 
                suitName = "Clubs";
                break;
        }
        
        switch(getValue()) {
            case 1: valueName = "Ace";
                break;

            case 11: valueName = "Jack";
                break;

            case 12: valueName = "Queen";
                break;

            case 13: valueName = "King";
                break;
                
            default: valueName = Integer.toString(getValue());
        }

        return "["+ valueName + " of " + suitName + "]";
    }

}