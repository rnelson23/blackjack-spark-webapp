package app.card;

public class Card {
    private final Rank rank;
    private boolean faceUp;
    private final String face;
    private final String back;

    public Card(Rank rank, String suit) {
        this.rank = rank;
        this.faceUp = false;

        this.face = String.format("""
                ┌───────────┐
                │ %s      %s │
                │           │
                │           │
                │     %s     │
                │           │
                │           │
                │ %s      %s │
                └───────────┘""", rank.rightPad(), suit, suit, suit, rank.leftPad());

        this.back = """
                ┌───────────┐
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                │ ░░░░░░░░░ │
                └───────────┘""";
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public Card flip() {
        faceUp = !faceUp;
        return this;
    }

    @Override
    public String toString() {
        return faceUp ? face : back;
    }
}
