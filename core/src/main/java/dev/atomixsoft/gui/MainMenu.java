package dev.atomixsoft.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.tommyettinger.textra.*;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingInTransition;
import dev.atomixsoft.GameMain;
import dev.atomixsoft.screens.GameScreen;

public class MainMenu extends GroupAdapter {

    private State m_MenuState;

    private TextraLabel title;
    private TextraButton m_Play, m_About, m_Settings, m_Exit;

    public MainMenu() {
        super();
    }

    @Override
    public void update() {
        reset();

        final AssetManager assets = m_Game.getAssets();
        final ScreenManager<ManagedScreen, ScreenTransition> screenManager = m_Game.getScreenManager();

        final Stage stage = getStage();
        switch (m_MenuState) {
            case TITLE -> {
                Styles.LabelStyle lblStyle = new Styles.LabelStyle();
                lblStyle.font = new Font(assets.get("dePixel36.ttf", BitmapFont.class));
                lblStyle.fontColor = Color.WHITE;

                title = new TypingLabel("{EASE}Rock N Paper: First Cut", lblStyle);
                title.setPosition((stage.getWidth() - title.getWidth()) / 2f, 0f);
                title.addAction(Actions.sequence(Actions.moveTo(title.getX(), (stage.getHeight() - title.getHeight()) / 2f, 1.5f),
                    Actions.run(()-> {
                        m_Play.setPosition((stage.getWidth() - m_Play.getWidth()) / 2f, title.getY() - (m_Play.getHeight() * 4f));
                        addActor(m_Play);

                        m_About.setPosition((stage.getWidth() - m_About.getWidth()) / 2f, m_Play.getY() - (m_About.getHeight() * 2f));
                        addActor(m_About);

                        m_Settings.setPosition((stage.getWidth() - m_Settings.getWidth()) / 2f, m_About.getY() - (m_Settings.getHeight() * 2f));
                        addActor(m_Settings);

                        m_Exit.setPosition((stage.getWidth() - m_Exit.getWidth()) / 2f, m_Settings.getY() - (m_Exit.getHeight() * 2f));
                        addActor(m_Exit);
                    })));
                addActor(title);

                Styles.TextButtonStyle btnStyle = new Styles.TextButtonStyle();
                btnStyle.font = new Font(assets.get("dePixel18.ttf", BitmapFont.class));

                Styles.TextButtonStyle btnStyleDisabled = new Styles.TextButtonStyle();
                btnStyleDisabled.font = btnStyle.font;
                btnStyleDisabled.fontColor = Color.DARK_GRAY;

                m_Play = new TypingButton("{SLIDE}Play", btnStyle);
                m_Play.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        reset();
                        screenManager.pushScreen(new GameScreen(), new SlidingInTransition(m_Game.getBatch(), SlidingDirection.DOWN, 0.25f, Interpolation.pow3In));
                    }
                });

                m_About = new TypingButton("{SLIDE}About", btnStyle);
                m_About.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setState(State.ABOUT);
                        update();
                    }
                });

                Styles.TextTooltipStyle ttStyle = new Styles.TextTooltipStyle();
                ttStyle.wrapWidth = 248;

                ttStyle.label = new Styles.LabelStyle();
                ttStyle.label.font = btnStyle.font;
                ttStyle.label.fontColor = Color.RED;

                TypingTooltip wip = new TypingTooltip("Not Yet Implemented!", ttStyle);
                wip.setInstant(true);

                m_Settings = new TypingButton("{SLIDE}Options", btnStyleDisabled);
                m_Settings.addListener(wip);

                m_Exit = new TypingButton("{SLIDE}Exit", btnStyle);
                m_Exit.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.exit();
                    }
                });
            }
            case ABOUT -> {
                Styles.LabelStyle style = new Styles.LabelStyle();
                style.font = new Font(assets.get("dePixel36.ttf", BitmapFont.class));
                style.fontColor = Color.WHITE;

                String about_text = """
                    {SLIDE}Programmer - {SICK}{JOLT}ArcaneSunku{ENDJOLT}{ENDSICK}
                    Artist - {HEARTBEAT}{RAINBOW}ArcaneSunku{ENDRAINBOW}{ENDHEARTBEAT}
                    Design - {CROWD}{SHAKE}ArcaneSunku{ENDSHAKE}{ENDCROWD}{ENDSLIDE}
                """;

                TextraLabel about = new TypingLabel(about_text, style);
                about.setPosition((stage.getWidth() - about.getWidth()) / 2f, (stage.getHeight() - about.getHeight()) / 2f);
                addActor(about);

                Styles.TextButtonStyle btnStyle = new Styles.TextButtonStyle();
                btnStyle.font = new Font(assets.get("dePixel18.ttf", BitmapFont.class));

                TextraButton returnToTitle = new TypingButton("{EASE}{WAVE}Return", btnStyle);
                returnToTitle.setPosition(10, 10);

                returnToTitle.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        setState(State.TITLE);
                        update();
                    }
                });

                addActor(returnToTitle);
            }
            case SETTINGS -> {
                // TODO: Implement settings menu
            }
        }
    }

    public void setState(State state) {
        m_MenuState = state;
    }

    public State getMenuState() {
        return m_MenuState;
    }

    public enum State {
        TITLE,
        ABOUT,
        SETTINGS
    }

}
