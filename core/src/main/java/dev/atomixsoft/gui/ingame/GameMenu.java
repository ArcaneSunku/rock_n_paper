package dev.atomixsoft.gui.ingame;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.atomixsoft.gui.GroupAdapter;
import dev.atomixsoft.screens.TitleScreen;

public class GameMenu extends GroupAdapter {
    private GameState m_State;

    public GameMenu() {
        super();
    }

    @Override
    public void update() {
        if(m_State == null) return;
        reset();

        Stage stage = getStage();
        Camera camera = stage.getCamera();

        switch (m_State) {
            case DECK_SELECT -> {
                DeckChoice deckChoice = new DeckChoice(m_Game);
                addActor(deckChoice);

                deckChoice.setSize(64 * 5, 96);
                getStage().addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);

                        if(getChildren().contains(deckChoice, false))
                            deckChoice.deselect();
                    }
                });

                deckChoice.setPosition((camera.viewportWidth - deckChoice.getWidth()) / 2f, (camera.viewportHeight - deckChoice.getHeight()) / 2f);
                deckChoice.update();
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

    @Override
    protected void reset() {
        clearListeners();
        super.reset();
    }

    public void returnToTitle() {
        m_Game.getScreenManager().pushScreen(new TitleScreen(), new SlidingOutTransition(m_Game.getBatch(), SlidingDirection.DOWN, 0.25f, Interpolation.smooth));
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
