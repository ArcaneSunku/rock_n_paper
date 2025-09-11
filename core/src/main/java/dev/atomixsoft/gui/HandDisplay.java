package dev.atomixsoft.gui;

import dev.atomixsoft.game.Player;

public class HandDisplay extends GroupAdapter {

    private Player m_Player;

    public HandDisplay() {
        super();
    }

    @Override
    public void update() {

    }

    public void setPlayer(Player player) {
        m_Player = player;
    }

    public Player getPlayer() {
        return m_Player;
    }

}
