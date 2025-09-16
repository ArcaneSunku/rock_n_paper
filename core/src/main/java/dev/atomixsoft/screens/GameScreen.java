package dev.atomixsoft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.scenes.scene2d.Stage;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

import dev.atomixsoft.GameMain;
import dev.atomixsoft.game.Card;
import dev.atomixsoft.game.Card.CardType;

import dev.atomixsoft.gui.GameMenu;
import dev.atomixsoft.gui.GameMenu.GameState;
import org.jspecify.annotations.Nullable;

public class GameScreen extends ManagedScreen {

    private final GameMain m_Game;
    private final OrthographicCamera m_Camera;

    private Card card;
    private GameMenu m_GameMenu;

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

        m_GameMenu = new GameMenu();
        stage.addActor(m_GameMenu);

        m_GameMenu.setState(GameState.DECK_SELECT);
        m_GameMenu.update();
    }

    @Override
    public void hide() {
        m_GameMenu.removeFromParent();
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
        m_Game.getScreenManager().pushScreen(new TitleScreen(), new BlendingTransition(m_Game.getBatch(), 0.25f, Interpolation.pow3In));
    }

}
