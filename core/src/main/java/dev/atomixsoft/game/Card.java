package dev.atomixsoft.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.atomixsoft.game.utils.GameAssets;

/**
 * <p>Represents a Card that is meant to be inside a Deck.</br></br>
 *
 * The Card has a few interesting things about it, such as its {@link CardType}</br>
 * This lets us know if the Card represents: Rock, Paper, or Scissors</p>
 */
public class Card {

    private final Vector2 m_Position;
    private final Sprite m_Back;

    private int m_Tier;
    private CardType m_Type;
    private boolean m_Visible;

    private Sprite m_Icon;
    private Sprite m_Front;

    public Card(CardType type) {
        this(1, type);
    }

    public Card(int tier, CardType type) {
        m_Position = new Vector2();
        m_Back = GameAssets.Get_Sprite("sprCardBack");

        m_Tier = tier;
        m_Type = type;
        m_Visible = true;

        setIconFromType();
        setFrontFromTier();
    }

    public void draw(SpriteBatch batch) {
        Sprite card = getCardFace();
        adjustIconToCard(card);

        card.draw(batch);
        if(isVisible()) getIcon().draw(batch);
    }

    public void adjustIconToCard(Sprite cardFace) {
        m_Icon.setPosition(cardFace.getX() + (cardFace.getWidth() - m_Icon.getWidth()) / 2f, cardFace.getY() + (cardFace.getHeight() - m_Icon.getHeight()) / 2f);
    }

    public void setIconFromType() {
        switch (m_Type) {
            case ROCK -> m_Icon = GameAssets.Get_Sprite("sprRock");
            case PAPER -> m_Icon = GameAssets.Get_Sprite("sprPaper");
            case SCISSORS -> m_Icon = GameAssets.Get_Sprite("sprScissors");
        }
    }

    public void setFrontFromTier() {
        switch (m_Tier) {
            case 1 -> m_Front = GameAssets.Get_Sprite("sprCardT1");
            case 2 -> m_Front = GameAssets.Get_Sprite("sprCardT2");
            default -> m_Front = GameAssets.Get_Sprite("sprCardT3");
        }
    }

    public void setPosition(float x, float y) {
        m_Position.set(x, y);

        m_Front.setPosition(x, y);
        m_Back.setPosition(x, y);
    }

    public void setTier(int tier) {
        m_Tier = tier;
        setFrontFromTier();
    }

    public void setType(CardType type) {
        m_Type = type;
        setIconFromType();
    }

    public void setVisible(boolean visible) {
        m_Visible = visible;
    }

    public Vector2 getPosition() {
        return m_Position;
    }

    public int getTier() {
        return m_Tier;
    }

    public CardType getType() {
        return m_Type;
    }

    public boolean isVisible() {
        return m_Visible;
    }

    public Sprite getIcon() {
        return m_Icon;
    }

    public Sprite getCardFace() {
        return isVisible() ? m_Front : m_Back;
    }

    /**
     * <p>The classic choices of Rock, Paper, Scissors.</p>
     */
    public enum CardType {
        ROCK, PAPER, SCISSORS
    }

}
