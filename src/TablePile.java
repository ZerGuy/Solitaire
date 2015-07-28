import java.awt.*;


public class TablePile extends CardPile {

    public static final int PAINT_Y_OFFSET = 35;
    private int numberInArray;
    private int numberOfCadsFaceUp = 0;
    private int numberOfCadsFaceDown = 0;


    TablePile(final int x, final int y, final int c) {
        // initialize the parent class
        super(x, y);
        // then initialize our pile of cards
        for (int i = 0; i < c; i++) {
            addCard(Solitaire.deckPile.pop());
        }
        // flip topmost card face up
        getTop().flip();

        numberInArray = 5 + c;
        numberOfCadsFaceDown = c - 1;
        numberOfCadsFaceUp = 1;
    }


    public boolean canTake(final Card aCard) {
        if (isEmpty()) {
            return aCard.isKing();
        }
        Card topCard = getTop();
        return (aCard.getColor() != topCard.getColor()) && (aCard.getRank() == topCard.getRank() - 1);
    }


    public boolean includes(final int tx, final int ty) {
        boolean answer = false;

        Card tmp = getTop();

        if (tmp == null) {  // no card
            answer = (tx >= x) && (tx <= x + Card.WIDTH) && (ty >= y) && (ty <= y + Card.HEIGHT);
        } else {
            int yHigher;
            int yLower = y + (numberOfCadsFaceUp + numberOfCadsFaceDown - 1) * PAINT_Y_OFFSET + Card.HEIGHT;

            if (numberOfCadsFaceUp == 0) {
                yHigher = y + (numberOfCadsFaceDown - 1) * PAINT_Y_OFFSET;
            } else {
                yHigher = y + numberOfCadsFaceDown * PAINT_Y_OFFSET;
            }

            answer = (tx >= x) && (tx <= x + Card.WIDTH) && (ty >= yHigher) && (ty <= yLower);
        }

        return answer;
    }


    public void select(final int tx, final int ty) {
        if (isEmpty()) {    // no card
            if (Solitaire.cardIsSelected()) {
                if (canTake(Solitaire.getSelectedCard())) {
                    addCards(Solitaire.popSelectedCards());
                } else if (Solitaire.isSamePile(numberInArray)) {
                    Solitaire.deselectCards();
                }
            }
        } else {    //there are some cards
            Card topCard = getTop();

            if (!Solitaire.cardIsSelected() && !topCard.isFaceUp()) { // if face down, then flip
                topCard.flip();
                numberOfCadsFaceDown--;
                numberOfCadsFaceUp++;
                Solitaire.deselectCards();
            } else {
                //calculate how many cards selected
                int yLowerBound = y + (numberOfCadsFaceDown + numberOfCadsFaceUp - 1) * PAINT_Y_OFFSET + Card.HEIGHT;
                int numOfSelectedCards = 1;
                if (ty < yLowerBound - Card.HEIGHT) {
                    numOfSelectedCards += (yLowerBound - ty - Card.HEIGHT) / PAINT_Y_OFFSET + 1;
                }
                Card pointedCard = topCard;
                int yReal = y + (numberOfCadsFaceDown + numberOfCadsFaceUp - numOfSelectedCards) * PAINT_Y_OFFSET;

                for (int i = 1; i < numOfSelectedCards; i++) {
                    pointedCard = pointedCard.link;
                }

                if (!Solitaire.cardIsSelected()) {
                    Solitaire.selectCards(numberInArray, numOfSelectedCards, tx - x, ty - yReal);
                } else if (Solitaire.isSamePile(numberInArray)) {
                    Solitaire.deselectCards();
                } else {
                    if (canTake(Solitaire.getSelectedCard())) {
                        addCards(Solitaire.popSelectedCards());
                    } else {
                        Solitaire.deselectCards();
                    }
                }
            }
        }
    }


    @Override
    public Card pop() {
        numberOfCadsFaceUp--;
        return super.pop();
    }


    @Override
    public void addCards(Card[] cards) {
        super.addCards(cards);
        numberOfCadsFaceUp += cards.length;
    }


    @Override
    public void addCard(Card card) {
        super.addCard(card);
        numberOfCadsFaceUp++;
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
