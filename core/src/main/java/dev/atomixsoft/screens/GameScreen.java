package dev.atomixsoft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.atomixsoft.GameMain;
import dev.atomixsoft.gui.ingame.GameMenu;
import dev.atomixsoft.gui.ingame.GameMenu.GameState;

import org.jspecify.annotations.Nullable;

public class GameScreen extends ManagedScreen {

    private final GameMain m_Game;
    private GameMenu m_GameMenu;

    public GameScreen() {
        m_Game = (GameMain) Gdx.app.getApplicationListener();
    }

    @Override
    public void show() {
        Stage stage = m_Game.getStage();

        m_GameMenu = new GameMenu();
        stage.addActor(m_GameMenu);

        m_GameMenu.setState(GameState.DECK_SELECT);
        m_GameMenu.update();
    }

    @Override
    public void hide() {
        m_GameMenu.removeFromParent();
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            m_GameMenu.returnToTitle();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public @Nullable Color getClearColor() {
        return Color.SLATE;
    }

}
