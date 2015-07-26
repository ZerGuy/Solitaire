public class SuitPile extends CardPile {

    SuitPile(final int x, final int y) {
        super(x, y);
    }


    public boolean canTake(final Card aCard) {
        if (isEmpty()) {
            return aCard.isAce();
        }
        Card topCard = getTop();
        return (aCard.getSuit() == topCard.getSuit()) &&
                (aCard.getRank() == 1 + topCard.getRank());
    }


    @Override
    public void select(int tx, int ty) {
        if(Solitaire.cardIsSelected()){
            if(canTake(Solitaire.getSelectedCard())){
                addCard(Solitaire.popSelectedCard());
            }
        }
    }
}
