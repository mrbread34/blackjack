import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    public Hand(Card c) {
        this.cards = new ArrayList<Card>();
        this.cards.add(c);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public int getValue() {
        int sum = 0;
        int numberOfAces = 0;
        for (Card card: this.cards) {
            sum += card.getRank().getValue();
            if (card.getRank().getName().equals("Ace")) {
                numberOfAces++;
            }
        }
        while (sum > 21 && numberOfAces > 0) {
            sum -= 10;
            numberOfAces--;
        }
        return sum;
    }

    public boolean isBust() {
        return this.getValue() > 21;
    }

    public boolean isBlackjack() {
        return this.getValue() == 21 && this.getCards().size() == 2;
    }

    public String toString() {
        String message =  "";
        for (Card card: this.cards) {
            message += " [" + card + "] ";
        }
        return message.trim();
    }

    public String toAsciiArt(boolean hideHoleCard) {
        String[] holeCard = {Ansi.BOLD + "+" + Ansi.RESET + "-----" + Ansi.BOLD + "+" + Ansi.RESET, "|/////|", "|/////|", "|/////|", Ansi.BOLD + "+" + Ansi.RESET + "-----" + Ansi.BOLD + "+" + Ansi.RESET};

        StringBuilder[] rows = new StringBuilder[5];
        for (int i = 0; i < 5; i++) {
            rows[i] = new StringBuilder();
        }

        for (int i = 0; i < cards.size(); i++) {
            String art[];
            if (hideHoleCard && i == 0) {
                art = holeCard;
            } else {
                art = cards.get(i).toAsciiArt().split("\n");
            }

            for (int row = 0; row < 5; row++) {
                rows[row].append(art[row]).append("  ");
            }
        }

        StringBuilder fullHand = new StringBuilder();
        for (StringBuilder row: rows) {
            fullHand.append(row).append("\n");
        }

        return fullHand.toString();
    }
    
    public void guaranteedAce() {
        this.cards.set(0, new Card(Suit.SPADE, Rank.ACE));
    }
}


