/*
	Simple Solitaire Card Game in Java
	Written by Tim Budd, Oregon State University, 1996
*/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;


public class Solitaire extends Applet {
    static DeckPile deckPile;
    static DiscardPile discardPile;
    static TablePile tableau[];
    static SuitPile suitPile[];
    static CardPile allPiles[];

    static boolean dragAndDrop = false;
    static int selectedPile = -1;
    static int numberOfSelectedCards = 0;

    static final int WIDTH = 450;
    static final int HEIGHT = 550;

    static Card[] draggingCards = null;
    static int xDragCardOffset;
    static int yDragCardOffset;

    static long prevMouseClick = 0;


    public static boolean cardIsSelected() {
        return selectedPile != -1;
    }


    public static Card getSelectedCard() {
        return draggingCards[numberOfSelectedCards - 1];
    }


    public static Card[] popSelectedCards() {
        for (int i = 0; i < numberOfSelectedCards; i++) {
            draggingCards[i].setSelected(false);
        }
        selectedPile = -1;
        numberOfSelectedCards = 0;
        return draggingCards;
    }


    public static void selectCards(int pileNo, int num, int x, int y) {
        xDragCardOffset = x;
        yDragCardOffset = y;
        draggingCards = new Card[num];
        for (int i = 0; i < num; i++) {
            draggingCards[i] = allPiles[pileNo].pop();
            draggingCards[i].setSelected(true);
        }
        selectedPile = pileNo;
        numberOfSelectedCards = num;
    }


    public static void deselectCards() {
        if (cardIsSelected()) {
            for (int i = numberOfSelectedCards - 1; i >= 0; i--) {
                draggingCards[i].setSelected(false);
                allPiles[selectedPile].addCard(draggingCards[i]);
            }
        }
        draggingCards = null;
        selectedPile = -1;
        numberOfSelectedCards = 0;
    }


    public void init() {
        setSize(WIDTH, HEIGHT);
        prevMouseClick = System.currentTimeMillis();

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
        System.out.println("paint all");
        Dimension dim = getSize();
        Image offscreen = createImage(dim.width, dim.height);
        Graphics offgr = offscreen.getGraphics();

        for (int i = 0; i < 13; i++) {
            allPiles[i].display(offgr);
        }

        if(cardIsSelected()) {
            displayDraggingCards(offgr);
        }

        g.drawImage(offscreen, 0, 0, this);

    }


    public boolean mouseDown(final Event evt, final int x, final int y) {
        long click = System.currentTimeMillis();
        if(click - prevMouseClick < 300){
            mouseDoubleClick(x,y);
        }
        else {
            for (int i = 0; i < 13; i++) {
                if (allPiles[i].includes(x, y)) {
                    allPiles[i].select(x, y);
                    dragAndDrop = true;
                    break;
                }
            }
            repaint();
        }
        prevMouseClick = click;
        return true;
    }


    private void mouseDoubleClick(int x, int y) {
        System.out.println("double");
        if(discardPile.includes(x, y)){
            Card card = discardPile.getTop();
            for (int j = 0; j < 4; j++) {
                if(suitPile[j].canTake(card)){
                    discardPile.pop();
                    suitPile[j].addCard(card);
                    return;
                }
            }
            for (int j = 0; j < 7; j++) {
                if (tableau[j].canTake(card)) {
                    discardPile.pop();
                    tableau[j].addCard(card);
                    return;
                }
            }
        }

        for (int i = 0; i < 7; i++) {
            if (tableau[i].includes(x, y)) {
                Card card = tableau[i].getTop();
                for (int j = 0; j < 4; j++) {
                    if(suitPile[j].canTake(card)){
                        tableau[i].pop();
                        suitPile[j].addCard(card);
                        return;
                    }
                }
                for (int j = 0; j < 7; j++) {
                    if (tableau[j].canTake(card) && (j != i)) {
                        tableau[i].pop();
                        tableau[j].addCard(card);
                        return;
                    }
                }
                break;
            }
        }
    }


    @Override
    public boolean mouseUp(Event evt, int x, int y) {
        dragAndDrop = false;
        if (cardIsSelected()) {
            boolean dropped = false;

            for (int i = 0; i < 13; i++) {
                if (allPiles[i].includes(x, y)) {
                    allPiles[i].select(x, y);
                    dropped = true;
                    break;
                }
            }

            // if dragged somewhere but not in pile
            if (!dropped) {
                deselectCards();
            }
        }
        repaint();
        return true;
    }


    @Override
    public boolean mouseDrag(Event evt, int x, int y) {
        if (dragAndDrop) {
            paint(getGraphics());
        }
        return true;
    }


    private void displayDraggingCards(Graphics g) {
        int x = getMousePosition().x;
        int y = getMousePosition().y;

        for (int i = numberOfSelectedCards - 1; i >= 0; i--) {
            int offset = (numberOfSelectedCards - i - 1) * TablePile.PAINT_Y_OFFSET;
            draggingCards[i].draw(g, x - xDragCardOffset, y - yDragCardOffset + offset);
        }
    }


    public static boolean isSamePile(int pileNumber) {
        return pileNumber == selectedPile;
    }
}