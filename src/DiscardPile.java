public class DiscardPile extends CardPile {

    DiscardPile(final int x, final int y) {
        super(x, y);
    }


    public void addCard(final Card card) {
        if (!card.isFaceUp()) {
            card.flip();
        }
        super.addCard(card);
    }


    public void select(final int tx, final int ty) {
        if(Solitaire.cardIsSelected() && Solitaire.isSamePile(1)){
            Solitaire.deselectCards();
            return;
        }
        if (isEmpty()) {
            return;
        }

        Card topCard = getTop();

        if (!Solitaire.cardIsSelected()) {
            Solitaire.selectCards(1, 1, tx - x, ty - y);
        } else if (Solitaire.getSelectedCard() == topCard) {
            Solitaire.deselectCards();
        }
    }
}
