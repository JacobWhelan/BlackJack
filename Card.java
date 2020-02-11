import java.io.Serializable;

public class Card implements Serializable{

    static final long serialVersionUID = 1L;

    private int value;

    private int suit;

    

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
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