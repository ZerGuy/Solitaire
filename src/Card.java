import java.awt.*;


public class Card {
    // data fields for colors and suits
    public final static int WIDTH = 50;
    public final static int HEIGHT = 70;
    public final static int RED = 0;
    public final static int BLACK = 1;
    public final static int HEART = 0;
    public final static int SPADE = 1;
    public final static int DIAMOND = 2;
    public final static int CLUB = 3;

    public Card link;
    //private static String names[] = {"A", "2", "3", "4", "5", "6",
    //	"7", "8", "9", "10", "J", "Q", "K"};
    // data fields

    private boolean faceup;
    private int rank;
    private int suit;


    // constructor
    Card(final int suitValue, final int rankValue) {
        suit = suitValue;
        rank = rankValue;
        faceup = false;
    }


    // access attributes of card
    public int getRank() {
        return rank;
    }


    public int getSuit() {
        return suit;
    }


    public boolean isFaceUp() {
        return faceup;
    }


    public void flip() {
        faceup = !faceup;
    }


    public int getColor() {
        if (getSuit() == HEART || getSuit() == DIAMOND) {
            return RED;
        }
        return BLACK;
    }


    public void draw(final Graphics g, final int x, final int y) {
        String names[] = {"A", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "J", "Q", "K"};
        // clear rectangle, draw border
        g.clearRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.black);
        g.drawRect(x, y, WIDTH, HEIGHT);
        // draw body of card
        if (isFaceUp()) {
            if (getColor() == RED) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.blue);
            }
            g.drawString(names[getRank()], x + 3, y + 15);
            if (getSuit() == HEART) {
                g.drawLine(x + 25, y + 30, x + 35, y + 20);
                g.drawLine(x + 35, y + 20, x + 45, y + 30);
                g.drawLine(x + 45, y + 30, x + 25, y + 60);
                g.drawLine(x + 25, y + 60, x + 5, y + 30);
                g.drawLine(x + 5, y + 30, x + 15, y + 20);
                g.drawLine(x + 15, y + 20, x + 25, y + 30);
            } else if (getSuit() == SPADE) {
                g.drawLine(x + 25, y + 20, x + 40, y + 50);
                g.drawLine(x + 40, y + 50, x + 10, y + 50);
                g.drawLine(x + 10, y + 50, x + 25, y + 20);
                g.drawLine(x + 23, y + 45, x + 20, y + 60);
                g.drawLine(x + 20, y + 60, x + 30, y + 60);
                g.drawLine(x + 30, y + 60, x + 27, y + 45);
            } else if (getSuit() == DIAMOND) {
                g.drawLine(x + 25, y + 20, x + 40, y + 40);
                g.drawLine(x + 40, y + 40, x + 25, y + 60);
                g.drawLine(x + 25, y + 60, x + 10, y + 40);
                g.drawLine(x + 10, y + 40, x + 25, y + 20);
            } else if (getSuit() == CLUB) {
                g.drawOval(x + 20, y + 25, 10, 10);
                g.drawOval(x + 25, y + 35, 10, 10);
                g.drawOval(x + 15, y + 35, 10, 10);
                g.drawLine(x + 23, y + 45, x + 20, y + 55);
                g.drawLine(x + 20, y + 55, x + 30, y + 55);
                g.drawLine(x + 30, y + 55, x + 27, y + 45);
            }
        } else { // face down
            g.setColor(Color.DARK_GRAY);
            g.drawLine(x + 15, y + 5, x + 15, y + 65);
            g.drawLine(x + 35, y + 5, x + 35, y + 65);
            g.drawLine(x + 5, y + 20, x + 45, y + 20);
            g.drawLine(x + 5, y + 35, x + 45, y + 35);
            g.drawLine(x + 5, y + 50, x + 45, y + 50);
        }
    }


    public boolean isAce() {
        return getRank() == 0;
    }


    public boolean isKing() {
        return getRank() == 12;
    }
}
