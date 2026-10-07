public enum Rank {
    ACE("Ace", 11, "A"),
    TWO("Two", 2, "2"), 
    THREE("Three", 3, "3"), 
    FOUR("Four", 4, "4"), 
    FIVE("Five", 5, "5"), 
    SIX("Six", 6, "6"), 
    SEVEN("Seven", 7, "7"), 
    EIGHT("Eight", 8, "8"), 
    NINE("Nine", 9, "9"), 
    TEN("Ten", 10, "10"), 
    JACK("Jack", 10, "J"), 
    QUEEN("Queen", 10, "Q"),
    KING("King", 10, "K");

    private String name;
    private int value;
    private String letter;

    private Rank(String name, int value, String letter) {
        this.name = name;
        this.value = value;
        this.letter = letter;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public String getLetter() {
        return this.letter;
    }
}
