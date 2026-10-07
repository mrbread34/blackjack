public enum Suit {
    
    DIAMOND("Diamonds", "\u2666"), 
    CLUB("Clubs", "\u2663"),
    HEART("Hearts", "\u2665"),
    SPADE("Spades", "\u2660");

    private String name;
    private String symbol;

    private Suit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }


}
