package dev.atomixsoft.game.utils;

public class GameData {

    private int m_LevelModifier;

    public GameData() {
        m_LevelModifier = 0;
    }

    public void setLevelModifier(int levelMod) {
        m_LevelModifier = levelMod;
    }

    public int getLevelModifier() {
        return m_LevelModifier;
    }

}
