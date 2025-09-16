package dev.atomixsoft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Interpolation;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;

import dev.atomixsoft.game.utils.GameAssets;
import dev.atomixsoft.screens.TitleScreen;

public class GameMain extends ManagedGame<ManagedScreen, ScreenTransition> {

    private AssetManager assets;
    private SpriteBatch batch;
    private Stage stage;

    @Override
    public final void create() {
        super.create();
        FileHandleResolver resolver = new InternalFileHandleResolver();

        assets = new AssetManager(resolver, true);
        assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        inputProcessor.addProcessor(stage);

        FreetypeFontLoader.FreeTypeFontLoaderParameter largeFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        largeFont.fontFileName = "fonts/dePixel.ttf";
        largeFont.fontParameters.size = 36;
        assets.load("dePixel36.ttf", BitmapFont.class, largeFont);

        FreetypeFontLoader.FreeTypeFontLoaderParameter smallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        smallFont.fontFileName = "fonts/dePixel.ttf";
        smallFont.fontParameters.size = 18;
        assets.load("dePixel18.ttf", BitmapFont.class, smallFont);

        assets.load("textures/libgdx.png", Texture.class);
        assets.load("textures/icons.png", Texture.class);
        assets.load("textures/cards.png", Texture.class);

        GameAssets.initialize(assets);

        screenManager.setAutoDispose(true, true);
        screenManager.pushScreen(new TitleScreen(), new BlendingTransition(batch, 1F, Interpolation.pow2In));
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        super.render();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();

        GameAssets.dispose();
        assets.dispose();

        batch.dispose();
        stage.dispose();
    }

    public AssetManager getAssets() {
        return assets;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Stage getStage() {
        return stage;
    }

}
