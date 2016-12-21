package game;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class CardButton extends ImageButton {

    private Card card;

    public CardButton(Context context) {

        super(context);
        card = null;
    }

    public CardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isEmptySlot() {
        return this.card == null;
    }
}
