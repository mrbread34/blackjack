public class BlackjackDealer extends BlackjackPlayer {
    private final int standThreshold = 17;
    private boolean hideHoleCard;

    public BlackjackDealer() {
        super();
        this.hideHoleCard = true;
    }

    public void playTurn(Deck deck, BlackjackPlayer player) {
        while (this.hand.getValue() < this.standThreshold) {
            BlackjackGame.clearConsole();
            this.hit(deck);
            player.displayHand(player.getHand());
            this.displayHand();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }

    public void revealHoleCard() {
        this.hideHoleCard = false;
    }

    public int getThreshold() {
        return this.standThreshold;
    }

    public void peek() throws InterruptedException {
        if (this.hand.getCards().get(1).getRank() == Rank.ACE || this.hand.getCards().get(1).getRankValue() == 10) {
            System.out.println(Ansi.ITALIC + "\nDealer peeks at the hole card..." + Ansi.RESET);
            Thread.sleep(2000);
            if (this.hand.isBlackjack()) {
                BlackjackGame.animatedText(Ansi.BRIGHT_RED + "Dealer has a blackjack...\n" + Ansi.RESET, 50);

                // Reveal hole card
                hideHoleCard = false;
                BlackjackGame.clearConsole();
                super.displayHand(super.getHand());
                this.displayHand(this.hand);
                return;
            }
            BlackjackGame.animatedText(Ansi.LIME_GREEN + "Dealer does not have a blackjack!" + Ansi.RESET, 20);
            Thread.sleep(800);
        }
    }

    public void displayHand() {
        if (ASCII_ART) {
            System.out.println(Ansi.BOLD + "Dealer's Hand: " + Ansi.RESET);
            System.out.print(this.hand.toAsciiArt(hideHoleCard));
            if (hideHoleCard) {
                System.out.println("Total: ???");
            } else System.out.println("Total: " + this.hand.getValue() + "\n");
            
        } else {
            if (this.hideHoleCard) {
                System.out.println("Dealer's Hand:   [" + this.hand.getCards().get(0) + "]  [???]");
            } else {
                System.out.print("Dealer's Hand:   ");
                System.out.print(this.hand);
                System.out.print(" (Value: " + this.hand.getValue() + ")\r");
            }
        }

    }

    @Override
    public void resetHands() {
        super.resetHands();
        this.hideHoleCard = true;
    }
}