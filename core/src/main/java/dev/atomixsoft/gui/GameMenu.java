package dev.atomixsoft.gui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameMenu extends GroupAdapter {
    private final DeckChoice m_DeckChoice;

    private GameState m_State;

    public GameMenu() {
        super();

        m_DeckChoice = new DeckChoice(m_Game);
        m_DeckChoice.setSize(64 * 5, 96);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                if(getChildren().contains(m_DeckChoice, false))
                    m_DeckChoice.deselect();
            }
        });
    }

    @Override
    public void update() {
        if(m_State == null) return;
        reset();

        Stage stage = getStage();
        Camera camera = stage.getCamera();

        switch (m_State) {
            case DECK_SELECT -> {
                addActor(m_DeckChoice);
                m_DeckChoice.setPosition((camera.viewportWidth - m_DeckChoice.getWidth()) / 2f, (camera.viewportHeight - m_DeckChoice.getHeight()) / 2f);
                m_DeckChoice.update();
            }
            case ENEMY_SELECT -> {
                // TODO: Handle ENEMY_SELECT state
            }
            case ACTIVE_MATCH -> {
                // TODO: Handle ACTIVE_MATCH state
            }
            case SHOPPING -> {
                // TODO: Handle SHOPPING state
            }
        }
    }

    public void setState(GameState state) {
        m_State = state;
    }

    public GameState getState() {
        return m_State;
    }

    public enum GameState {
        DECK_SELECT,
        ENEMY_SELECT,
        ACTIVE_MATCH,
        SHOPPING
    }
}
