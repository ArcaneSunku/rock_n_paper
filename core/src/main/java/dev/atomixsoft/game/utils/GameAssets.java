package dev.atomixsoft.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class GameAssets {

    private static final HashMap<String, Sprite> GAME_SPRITES = new HashMap<>();

    public static void initialize(AssetManager assets) {
        // Creates Sprites for our Rock, Paper, and Scissors icons
        for(int i = 0; i < 3; i++) {
            TextureRegion region = new TextureRegion(assets.get("textures/icons.png", Texture.class));
            region.setRegion(i * 32, 0, 32, 32);

            String spriteName = "";
            switch (i) {
                case 0 -> spriteName = "sprPaper";
                case 1 -> spriteName = "sprRock";
                case 2 -> spriteName = "sprScissors";
            }

            final Sprite sprite = new Sprite(region);
            sprite.setSize(32, 32);

            GAME_SPRITES.put(spriteName, sprite);
        }

        // Creates Sprites for our Cards, both front and back
        for(int i = 0; i < 4; i++) {
            TextureRegion region = new TextureRegion(assets.get("textures/cards.png", Texture.class));
            region.setRegion(i * 32, 0, 32, 48);

            String spriteName = "";
            switch (i) {
                case 0 -> spriteName = "sprCardT3";
                case 1 -> spriteName = "sprCardT2";
                case 2 -> spriteName = "sprCardT1";
                case 3 -> spriteName = "sprCardBack";
            }

            final Sprite sprite = new Sprite(region);
            sprite.setSize(32 * 2, 48 * 2);

            GAME_SPRITES.put(spriteName, sprite);
        }
    }

    public static void dispose() {
        GAME_SPRITES.clear();
    }

    public static Sprite Get_Sprite(String name) {
        return new Sprite(GAME_SPRITES.get(name));
    }

}
