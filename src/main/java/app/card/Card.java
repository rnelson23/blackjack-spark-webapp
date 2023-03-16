package app.card;

public class Card {
    public final Rank rank;
    public boolean faceUp;
    public final String face;
    public final String back;

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

    public Card flip() {
        faceUp = !faceUp;
        return this;
    }

    @Override
    public String toString() {
        return faceUp ? face : back;
    }
}
