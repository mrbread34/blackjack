public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        return this.rank.getName() + " of " + this.suit.getName();
    }

    public String toAsciiArt() {

        String textColour = Ansi.WHITE + Ansi.BOLD;
        if (this.suit == Suit.DIAMOND || this.suit == Suit.HEART) textColour = Ansi.BRIGHT_RED;
        StringBuilder cardArt = new StringBuilder();
        cardArt.append(Ansi.BOLD + "+" + Ansi.RESET + "-----" + Ansi.BOLD + "+" + Ansi.RESET + "\n");
        cardArt.append(String.format("| %s%s%s%s |\n", textColour, String.format("%-2s", this.rank.getLetter()), Ansi.RESET, " "));
        cardArt.append(String.format("|  %s%s%s  |\n", textColour, this.suit.getSymbol(), Ansi.RESET));
        cardArt.append(String.format("| %s%s%s%s |\n", " ", textColour, String.format("%2s", this.rank.getLetter()), Ansi.RESET));
        cardArt.append(Ansi.BOLD + "+" + Ansi.RESET + "-----" + Ansi.BOLD + "+" + Ansi.RESET);

        return cardArt.toString();
    }

    public Rank getRank() {
        return this.rank;
    }

    public int getRankValue() {
        return this.rank.getValue();
    }

    public Suit getSuit() {
        return this.suit;
    }
}
