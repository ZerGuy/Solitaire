/*
	Simple Solitaire Card Game in Java
	Written by Tim Budd, Oregon State University, 1996
*/

import java.applet.Applet;
import java.awt.*;


public class Solitaire extends Applet {
    static DeckPile deckPile;
    static DiscardPile discardPile;
    static TablePile tableau[];
    static SuitPile suitPile[];
    static CardPile allPiles[];

    static int selectedPile = -1;
    static int numberOfSelectedCards = 0;

    static final int WIDTH = 450;
    static final int HEIGHT = 550;


    public static boolean cardIsSelected() {
        return selectedPile != -1;
    }


    public static Card getSelectedCard() {
        Card card = allPiles[selectedPile].getTop();
        for (int i = 1; i < numberOfSelectedCards; i++) {
            card = card.link;
        }
        return card;
    }


    public static Card[] popSelectedCards() {
        Card cards[] = new Card[numberOfSelectedCards];
        for (int i = 0; i < numberOfSelectedCards; i++) {
            Card tmp = allPiles[selectedPile].pop();
            cards[i] = tmp;
            tmp.setSelected(false);
        }
        selectedPile = -1;
        numberOfSelectedCards = 0;
        return cards;
    }


    public static void selectCards(Card card, int pileNo, int num) {
        System.out.println("Number Sel: " + num);
        Card tmp = card;
        for (int i = 0; i < num; i++) {
            tmp.setSelected(true);
            tmp = tmp.link;
        }
        selectedPile = pileNo;
        numberOfSelectedCards = num;
    }


    public static void deselectCards() {
        if (cardIsSelected()) {
            Card tmp = allPiles[selectedPile].getTop();
            for (int i = 0; i < numberOfSelectedCards; i++) {
                tmp.setSelected(false);
                tmp = tmp.link;
            }
        }
        selectedPile = -1;
        numberOfSelectedCards = 0;
    }


    public void init() {
        setSize(WIDTH, HEIGHT);
        // first allocate the arrays
        allPiles = new CardPile[13];
        suitPile = new SuitPile[4];
        tableau = new TablePile[7];
        // then fill them in
        allPiles[0] = deckPile = new DeckPile(335, 5);
        allPiles[1] = discardPile = new DiscardPile(268, 5);
        for (int i = 0; i < 4; i++) {
            allPiles[2 + i] = suitPile[i] = new SuitPile(15 + 60 * i, 5);
        }
        for (int i = 0; i < 7; i++) {
            allPiles[6 + i] = tableau[i] = new TablePile(5 + 55 * i, 80, i + 1);
        }
    }


    public void paint(final Graphics g) {
        Dimension dim = getSize();
        Image offscreen = createImage(dim.width, dim.height);
        Graphics offgr = offscreen.getGraphics();

        for (int i = 0; i < 13; i++) {
            allPiles[i].display(offgr);
        }

        g.drawImage(offscreen, 0, 0, this);
    }


    public boolean mouseDown(final Event evt, final int x, final int y) {
        for (int i = 0; i < 13; i++) {
            if (allPiles[i].includes(x, y)) {
                allPiles[i].select(x, y);
                repaint();
                return true;
            }
        }
        return true;
    }
}