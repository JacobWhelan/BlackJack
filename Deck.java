// class to represent a deck of cards

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>(52);

    public Deck() {

        setDeck();

    }

    public void setDeck() {
        for(int i=1;i<5;i++) {
            for(int j=1;j<14;j++) {
                Card toAdd = new Card(j, i);
                deck.add(toAdd);
            }
        }
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public void shuffle() {

        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        
    }
}