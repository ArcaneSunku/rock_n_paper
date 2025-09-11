package dev.atomixsoft.gui.elements;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import dev.atomixsoft.game.Card;

public class CardDisplay extends Group {

    public static CardDisplay Card_Display(Card card) {
        CardDisplay result = new CardDisplay(card);

        ImageButton face = result.getFace();
        Image icon = result.getIcon();
        icon.setPosition(face.getX() + (face.getWidth() - icon.getWidth()) / 2f, face.getY() + (face.getHeight() - icon.getHeight()) / 2f);

        result.addActor(face);
        result.addActor(icon);

        return result;
    }

    private final Card m_Card;

    private CardDisplay(Card card) {
        m_Card = card;
        setSize(m_Card.getCardFace().getWidth(), m_Card.getCardFace().getHeight());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        m_Card.setPosition(x, y);
    }

    public Image getIcon() {
        return new Image(m_Card.getIcon());
    }

    public ImageButton getFace() {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new SpriteDrawable(m_Card.getCardFace());
        style.down = new SpriteDrawable(m_Card.getCardFace());
        style.over = new SpriteDrawable(m_Card.getCardFace());
        return new ImageButton(style);
    }

}
