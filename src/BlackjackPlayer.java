import java.util.ArrayList;

public class BlackjackPlayer {
    protected Hand hand; // should be called mainHand but kept as hand as the split functionality was added later
    private ArrayList<Integer> splitBets;
    protected ArrayList<Hand> hands; // protected to allow Dealer to access for resetting
    private boolean hasSplit;
    private int bet;
    private Currency bank;

    protected final boolean ASCII_ART = true; // original code was implemented without ASCII Art

    public BlackjackPlayer(Currency bank) {
        this.hand = new Hand();
        this.bank = bank;
        this.splitBets = new ArrayList<>();
        this.hands = new ArrayList<>() {
            {
                add(hand);
            }
        };
        this.hasSplit = false;
    }

    public BlackjackPlayer() {
        this.hand = new Hand();

        // To avoid null pointer exceptions for dealer
        this.splitBets = new ArrayList<>();
        this.hands = new ArrayList<>();
        this.hasSplit = false;
    }

    // For testing purposes only
    public void debugBlackjack() {
        this.hand = new Hand();
        this.hand.addCard(new Card(Suit.SPADE, Rank.ACE));
        this.hand.addCard(new Card(Suit.HEART, Rank.JACK));
    }

    public void debugSplit() {
        this.hand = new Hand();
        this.hand.addCard(new Card(Suit.SPADE, Rank.EIGHT));
        this.hand.addCard(new Card(Suit.HEART, Rank.EIGHT));
    }

    public void placeBet(int amount)  {
        if (amount < 10) throw new IllegalArgumentException(Ansi.BRIGHT_RED + "Bet has to be greater than or equal to $10." + Ansi.RESET);
        this.bet = amount;
        bank.withdraw(amount);
    }

    public void hit(Deck deck) {
        this.hand.addCard(deck.dealCard());
    }

    public void hit(Deck deck, Hand hand) {
        hand.addCard(deck.dealCard());
    }

    public void doubleDown() {
        bank.withdraw(this.bet);
        this.bet *= 2;       
    }

    public void split(Deck deck) {
        if (this.hasSplit) {
            throw new IllegalStateException(Ansi.BRIGHT_RED + "You can only split once per round." + Ansi.RESET);
        }
        
        if (!this.canSplit()) {
            System.out.println(Ansi.BRIGHT_RED + "You are not allowed to split. Your cards are not a pair." + Ansi.RESET);
            return;
        }

        if (!this.enoughMoney()) {
            System.out.println(Ansi.BRIGHT_RED + "You do not have enough money to split." + Ansi.RESET);
            return;
        }   
        this.hasSplit = true;
        bank.withdraw(this.bet);

        Card secondCard = this.hand.getCards().get(1);
        this.hand.getCards().remove(1);

        Hand splitHand = new Hand(secondCard);

        this.hands.add(splitHand);
        this.splitBets.add(this.bet);
    }

    public int getHandValue() {
        return this.hand.getValue();
    }

    public boolean canSplit() {
        return this.hand.getCards().size() == 2 && this.hand.getCards().get(0).getRankValue() == this.hand.getCards().get(1).getRankValue();
    }

    public boolean canDoubleDown() {
        return this.hand.getCards().size() == 2 && this.enoughMoney();
    }

    public boolean enoughMoney() {
        return bank.getBalance() >= this.bet;
    }

    public Hand getHand() {
        return this.hand;
    }

    public ArrayList<Hand> getHands() {
        return this.hands;
    }

    public ArrayList<Integer> getSplitBets() {
        return this.splitBets;
    }

    public int getBet() {
        return this.bet;
    }

    public void resetHands() {
        this.hand = new Hand();
        this.hands.clear();
        this.hands.add(this.hand);
        this.splitBets.clear();
        this.hasSplit = false;
        
    }

    public boolean allHandsBust() {
        for (Hand h : hands) {
            if (!h.isBust()) return false;
        }
        return true;
    }

    public boolean hasSplit() {
        return this.hasSplit;
    }

    public void displayHand(Hand hand) {
        if (ASCII_ART) {
            System.out.println(Ansi.ITALIC + Ansi.UNDERLINE + Ansi.BOLD + "Your Hand:" + Ansi.RESET);
            System.out.print(hand.toAsciiArt(false));
            System.out.println("Total: " + hand.getValue() + "\n");
        } else {
            System.out.print("Your Hand:       ");
            System.out.print(hand);
            System.out.println(" (Value: " + hand.getValue() + ")");
        }

    }


}
