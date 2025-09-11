package dev.atomixsoft.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.github.tommyettinger.textra.*;

import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingInTransition;
import dev.atomixsoft.GameMain;
import dev.atomixsoft.game.Card;
import dev.atomixsoft.gui.elements.CardDisplay;
import dev.atomixsoft.screens.TitleScreen;

import java.util.concurrent.ThreadLocalRandom;

public class DeckChoice extends GroupAdapter {

    private final GameMain m_Game;

    private Card.CardType m_Selected;
    private boolean m_Pressed = false;

    private TextraLabel m_SelectedLabel;
    private TextraButton m_Confirm;
    private boolean m_Confirmed;

    public DeckChoice() {
        super();

        m_Game = (GameMain) Gdx.app.getApplicationListener();
        m_Selected = null;
    }

    @Override
    public void update() {
        AssetManager assets = m_Game.getAssets();
        Card rock, paper, scissors;

        Styles.TextTooltipStyle ttStyle = new Styles.TextTooltipStyle();
        ttStyle.label = new Styles.LabelStyle(new Font(assets.get("dePixel18.ttf", BitmapFont.class)), Color.BLACK);

        rock = new Card(1, Card.CardType.ROCK);
        CardDisplay rockDis = CardDisplay.Card_Display(rock);

        int baseY = 10;
        rockDis.setPosition(32, baseY);
        TextraTooltip ttRock = new TextraTooltip("{EASE}{WAVE}Rock Deck", ttStyle);
        ttRock.setInstant(true);

        ThreadLocalRandom current = ThreadLocalRandom.current();

        rockDis.addAction(Actions.sequence(Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(rockDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(rockDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        rockDis.addListener(ttRock);
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
        paperDis.setPosition(rockDis.getX() + rockDis.getWidth() + 32, baseY);
        TextraTooltip ttPaper = new TextraTooltip("{EASE}{WAVE}Paper Deck", ttStyle);
        ttPaper.setInstant(true);

        paperDis.addAction(Actions.sequence(Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(paperDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(paperDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        paperDis.addListener(ttPaper);
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
        scissorsDis.setPosition(paperDis.getX() + paperDis.getWidth() + 32, baseY);
        TextraTooltip ttScissors = new TextraTooltip("{EASE}{WAVE}Scissors Deck", ttStyle);
        ttScissors.setInstant(true);

        scissorsDis.addAction(Actions.sequence(Actions.delay(current.nextFloat(1.25f)),
            Actions.forever(Actions.sequence(Actions.moveTo(scissorsDis.getX(), baseY + 10f, 1.0f, Interpolation.fastSlow),
            Actions.moveTo(scissorsDis.getX(), baseY - 10f, 1.0f, Interpolation.pow2In)))));

        scissorsDis.addListener(ttScissors);
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
        btnStyle.font = ttStyle.label.font;

        TextraButton returnBtn = new TypingButton("{WAVE}Return", btnStyle);
        addActor(returnBtn);
        returnBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                deselect();
                m_Pressed = true;
                returnToTitle();
            }
        });

        returnBtn.setPosition(-getX() + 10, -getY() + 10);
    }

    public void showConfirm() {
        removeActor(m_SelectedLabel);

        Color color = null;
        switch (getSelectedDeck()) {
            case ROCK -> color = Color.BLACK;
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

    private void returnToTitle() {
        m_Game.getScreenManager().pushScreen(new TitleScreen(), new SlidingInTransition(m_Game.getBatch(), SlidingDirection.UP, 0.25f, Interpolation.pow3In));
    }

    public Card.CardType getSelectedDeck() {
        return m_Selected;
    }

    public boolean isConfirmed() {
        return m_Confirmed;
    }

}
