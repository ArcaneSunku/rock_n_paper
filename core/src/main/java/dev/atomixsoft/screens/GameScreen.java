package dev.atomixsoft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingInTransition;

import dev.atomixsoft.GameMain;
import dev.atomixsoft.game.Board;
import dev.atomixsoft.game.Card;
import dev.atomixsoft.game.Card.CardType;

import dev.atomixsoft.game.Player;
import dev.atomixsoft.gui.DeckChoice;
import org.jspecify.annotations.Nullable;

public class GameScreen extends ManagedScreen {

    private final GameMain m_Game;
    private final OrthographicCamera m_Camera;

    private Card card;
    private DeckChoice m_StartChoice;

    public GameScreen() {
        m_Game = (GameMain) Gdx.app.getApplicationListener();
        m_Camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        card = new Card(CardType.SCISSORS);
        Sprite cardFace = card.getCardFace();
        card.setPosition((m_Camera.viewportWidth - cardFace.getWidth()) / 2f, (m_Camera.viewportHeight - cardFace.getHeight()) / 2f);

        Stage stage = m_Game.getStage();

        m_StartChoice = new DeckChoice();
        m_StartChoice.setSize(64 * 5, 96);
        stage.addActor(m_StartChoice);
        m_StartChoice.setPosition((m_Camera.viewportWidth - m_StartChoice.getWidth()) / 2f, (m_Camera.viewportHeight - m_StartChoice.getHeight()) / 2f);
        m_StartChoice.update();

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(stage.getActors().contains(m_StartChoice, false))
                    m_StartChoice.deselect();

            }
        });
    }

    @Override
    public void hide() {
        m_StartChoice.removeFromParent();
        card = null;
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            returnToTitle();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            card.setVisible(!card.isVisible());

        m_Camera.update();

        SpriteBatch batch = m_Game.getBatch();
        batch.setProjectionMatrix(m_Camera.combined);

        batch.begin();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        m_Camera.setToOrtho(false, width, height);
    }

    @Override
    public void dispose() {

    }

    @Override
    public @Nullable Color getClearColor() {
        return Color.SLATE;
    }

    private void returnToTitle() {
        m_Game.getScreenManager().pushScreen(new TitleScreen(), new SlidingInTransition(m_Game.getBatch(), SlidingDirection.UP, 0.25f, Interpolation.pow3In));
    }

}
