import java.util.ArrayList;
import java.io.Serializable;

public class Hand implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Card> hand;

    private int handTotal;

    public Hand() {
        this.hand = new ArrayList<Card>(2);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void clearHand() {
        hand.clear();
    } 

    public void addToHand(Card card) {
        hand.add(card);
    }

    public int countHand() {
        handTotal = 0;
        int AceCounter = 0;

        for (int i=0;i<hand.size();i++) {
            handTotal += hand.get(i).cardValue();
            
            if(hand.get(i).getValue() == 1) {
                AceCounter++;
            }
        }

        if(handTotal > 21) {
            while(AceCounter > 0 && handTotal > 21) {
                handTotal -= 10;
                AceCounter--;
            }
        }

        if (handTotal > 21)
            return 0;
        else
            return handTotal;
        
    }

    @Override
    public String toString() {
        String str = "";

        for(Card card : getHand()) {
            str += card;
        }

        return str;
    }

}