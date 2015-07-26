import java.awt.*;


public class TablePile extends CardPile {

    public static final int PAINT_Y_OFFSET = 35;


    TablePile(final int x, final int y, final int c) {
        // initialize the parent class
        super(x, y);
        // then initialize our pile of cards
        for (int i = 0; i < c; i++) {
            addCard(Solitaire.deckPile.pop());
        }
        // flip topmost card face up
        getTop().flip();
    }


    public boolean canTake(final Card aCard) {
        if (isEmpty()) {
            return aCard.isKing();
        }
        Card topCard = getTop();
        return (aCard.getColor() != topCard.getColor()) &&
                (aCard.getRank() == topCard.getRank() - 1);
    }


    public boolean includes(final int tx, final int ty) {
        int offset = 0;
        Card tmp = getTop();
        while (tmp.link != null) {
            tmp = tmp.link;
            offset += PAINT_Y_OFFSET;
        }
        return (tx >= x) && (tx <= x + Card.WIDTH) &&
                (ty >= y + offset) && (ty <= y + offset + Card.HEIGHT);
    }


    public void select(final int tx, final int ty) {
        if (isEmpty()) {
            return;
        }

        // if face down, then flip
        Card topCard = getTop();
        if (!topCard.isFaceUp()) {
            topCard.flip();
            return;
        }

        // else see if any getSuit pile can take card
        topCard = pop();
        for (int i = 0; i < 4; i++) {
            if (Solitaire.suitPile[i].canTake(topCard)) {
                Solitaire.suitPile[i].addCard(topCard);
                return;
            }
        }
        // else see if any other table pile can take card
        for (int i = 0; i < 7; i++) {
            if (Solitaire.tableau[i].canTake(topCard)) {
                Solitaire.tableau[i].addCard(topCard);
                return;
            }
        }
        // else put it back on our pile
        addCard(topCard);
    }


    private int stackDisplay(final Graphics g, final Card aCard) {
        int localy;
        if (aCard == null) {
            return y;
        }
        localy = stackDisplay(g, aCard.link);
        aCard.draw(g, x, localy);
        return localy + PAINT_Y_OFFSET;
    }


    public void display(final Graphics g) {
        stackDisplay(g, getTop());
    }

}
