import java.awt.*;


public class CardPile {

    // coordinates of the card pile
    protected int x;

    // access to cards are not overridden
    protected int y;
    private Card firstCard;


    CardPile(final int xl, final int yl) {
        x = xl;
        y = yl;
        firstCard = null;
    }

    // the following are sometimes overridden


    public Card getTop() {
        return firstCard;
    }


    public boolean isEmpty() {
        return firstCard == null;
    }


    public Card pop() {
        Card result = null;
        if (firstCard != null) {
            result = firstCard;
            firstCard = firstCard.link;
        }
        return result;
    }


    public boolean includes(final int tx, final int ty) {
        return x <= tx && tx <= x + Card.WIDTH &&
                y <= ty && ty <= y + Card.HEIGHT;
    }


    public void select(final int tx, final int ty) {
        // do nothing
    }


    public void addCards(final Card[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            cards[i].link = firstCard;
            firstCard = cards[i];
        }
    }


    public void addCard(final Card card) {
        card.link = firstCard;
        firstCard = card;
    }


    public void display(final Graphics g) {
        g.setColor(Color.black);
        if (firstCard == null) {
            g.drawRect(x, y, Card.WIDTH, Card.HEIGHT);
        } else {
            firstCard.draw(g, x, y);
        }
    }


    public boolean canTake(final Card aCard) {
        return false;
    }
}
