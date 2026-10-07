import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;
    
    public Deck() {
        this.deck = new ArrayList<Card>();
        this.build();
    }

    public void build() {
        for (Rank rank: Rank.values()) {
            for (Suit suit: Suit.values()) {
                this.deck.add(new Card(suit, rank));
            }
        }
        this.shuffle();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card dealCard() {
        Card topCard = this.deck.get(0);
        this.deck.remove(0);
        return topCard;
    }

    public int cardsLeft() {
        return this.deck.size();
    }

    public void resetDeck() {
        this.deck.clear();
        this.build();
        this.shuffle();
    }    
}
