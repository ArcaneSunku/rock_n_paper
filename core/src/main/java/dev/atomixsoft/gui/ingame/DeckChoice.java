package dev.atomixsoft.gui.ingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.github.tommyettinger.textra.*;

import de.eskalon.commons.screen.transition.impl.BlendingTransition;

import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.atomixsoft.GameMain;
import dev.atomixsoft.game.Card;
import dev.atomixsoft.gui.GroupAdapter;
import dev.atomixsoft.gui.elements.CardDisplay;
import dev.atomixsoft.screens.TitleScreen;

import java.util.concurrent.ThreadLocalRandom;

public class DeckChoice extends GroupAdapter {

    private Card.CardType m_Selected;
    private boolean m_Pressed = false;

    private TextraLabel m_SelectedLabel;
    private TextraButton m_Confirm;
    private boolean m_Confirmed;

    public DeckChoice(GameMain game) {
        super(game);
        m_Selected = null;
    }

    @Override
    public void update() {
        clearChildren();
        Card rock, paper, scissors;

        ThreadLocalRandom current = ThreadLocalRandom.current();
        rock = new Card(1, Card.CardType.ROCK);
        CardDisplay rockDis = CardDisplay.Card_Display(rock);
        rockDis.getColor().a = 0.0f;
        int baseY = 10;
        rockDis.setPosition(32, baseY);

        rockDis.addAction(Actions.sequence(Actions.fadeIn(1.25f), Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(rockDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(rockDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        rockDis.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                m_Selected = Card.CardType.ROCK;
                m_Pressed = true;

                showConfirm();
            }
        });

        paper = new Card(1, Card.CardType.PAPER);
        CardDisplay paperDis = CardDisplay.Card_Display(paper);
        paperDis.getColor().a = 0.0f;
        paperDis.setPosition(rockDis.getX() + rockDis.getWidth() + 32, baseY);

        paperDis.addAction(Actions.sequence(Actions.fadeIn(1.25f), Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(paperDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(paperDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        paperDis.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                m_Selected = Card.CardType.PAPER;
                m_Pressed = true;

                showConfirm();
            }
        });

        scissors = new Card(1, Card.CardType.SCISSORS);
        CardDisplay scissorsDis = CardDisplay.Card_Display(scissors);
        scissorsDis.getColor().a = 0.0f;
        scissorsDis.setPosition(paperDis.getX() + paperDis.getWidth() + 32, baseY);

        scissorsDis.addAction(Actions.sequence(Actions.fadeIn(1.25f), Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(scissorsDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(scissorsDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        scissorsDis.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                m_Selected = Card.CardType.SCISSORS;
                m_Pressed = true;

                showConfirm();
            }
        });

        addActor(rockDis);
        addActor(paperDis);
        addActor(scissorsDis);

        Styles.TextButtonStyle btnStyle = new Styles.TextButtonStyle();
        btnStyle.font = new Font(m_Game.getAssets().get("dePixel18.ttf", BitmapFont.class));

        TextraButton returnBtn = new TypingButton("{WAVE}Return", btnStyle);
        addActor(returnBtn);

        GameMenu parent = (GameMenu) getParent();
        returnBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                deselect();
                m_Pressed = true;
                parent.returnToTitle();
            }
        });

        returnBtn.setPosition(-getX() + 10, -getY() + 10);
    }

    @Override
    public void removeFromParent() {
        super.removeFromParent();

        m_Confirmed = false;
        m_Pressed = false;
        m_Selected = null;
    }

    public void showConfirm() {
        removeActor(m_SelectedLabel);

        Color color = null;
        switch (getSelectedDeck()) {
            case ROCK -> color = Color.DARK_GRAY;
            case PAPER -> color = Color.WHITE;
            case SCISSORS -> color = Color.RED;
        }

        Styles.LabelStyle lblStyle = new Styles.LabelStyle();
        lblStyle.font = new Font(m_Game.getAssets().get("dePixel18.ttf", BitmapFont.class));
        lblStyle.fontColor = color;

        m_SelectedLabel = new TypingLabel("{SQUASH}{FADE}" + getSelectedDeck().name() + " DECK", lblStyle);
        m_SelectedLabel.setPosition((getWidth() - m_SelectedLabel.getWidth()) / 2f, -getHeight());

        addActor(m_SelectedLabel);
        if(m_Confirm != null) return;

        Styles.TextButtonStyle style = new Styles.TextButtonStyle();
        style.downFontColor = Color.BLACK;
        style.font = new Font(m_Game.getAssets().get("dePixel18.ttf", BitmapFont.class));
        m_Confirm = new TypingButton("{SLIDE}Confirm", style);
        m_Confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                m_Pressed = true;
                m_Confirmed = true;
                removeFromParent();
            }
        });

        m_Confirm.setPosition((getWidth() - m_Confirm.getWidth()) / 2f, m_SelectedLabel.getY() - 50f);
        addActor(m_Confirm);
    }

    public void hideConfirm() {
        if(m_Confirm == null) return;

        removeActor(m_SelectedLabel);
        removeActor(m_Confirm);

        m_SelectedLabel = null;
        m_Confirm = null;
    }

    public void deselect() {
        if(m_Selected == null) return;

        if(m_Pressed) {
            m_Pressed = false;
            return;
        }

        m_Selected = null;
        hideConfirm();
    }

    public Card.CardType getSelectedDeck() {
        return m_Selected;
    }

    public boolean isConfirmed() {
        return m_Confirmed;
    }

}
