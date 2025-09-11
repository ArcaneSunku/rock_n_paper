package dev.atomixsoft.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.scenes.scene2d.Stage;
import de.eskalon.commons.screen.ManagedScreen;

import dev.atomixsoft.GameMain;
import dev.atomixsoft.gui.MainMenu;

public class TitleScreen extends ManagedScreen {

    private final GameMain m_Game;
    private MainMenu m_Menu;

    public TitleScreen() {
        this.m_Game = (GameMain) Gdx.app.getApplicationListener();
    }

    @Override
    public void show() {
        Stage stage = m_Game.getStage();
        m_Menu = new MainMenu();
        m_Menu.setState(MainMenu.State.TITLE);

        stage.addActor(m_Menu);
        m_Menu.update();
    }

    @Override
    public void hide() {
        m_Menu.removeFromParent();
    }

    @Override
    public void render(float delta) {
        if(m_Menu.getMenuState() == MainMenu.State.TITLE && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

}
