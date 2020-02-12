// class to represent a deck of cards

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>(52);

    public Deck() {

        setDeck();

    }

    public void setDeck() {
        for(int i=0; i<52; i++) {
            deck.add(new Card(i));
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