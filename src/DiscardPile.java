public class DiscardPile extends CardPile {

    DiscardPile(final int x, final int y) {
        super(x, y);
    }


    public void addCard(final Card aCard) {
        if (!aCard.isFaceUp()) {
            aCard.flip();
        }
        super.addCard(aCard);
    }


    public void select(final int tx, final int ty) {
        if (isEmpty()) {
            return;
        }

        Card topCard = getTop();

        if (!Solitaire.cardIsSelected()) {
            Solitaire.selectCard(topCard, 1);
        } else if (Solitaire.getSelectedCard() == topCard) {
            Solitaire.deselectCard();
        }
    }
}
