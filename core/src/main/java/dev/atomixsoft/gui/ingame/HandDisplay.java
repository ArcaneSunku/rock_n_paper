package dev.atomixsoft.gui.ingame;

import dev.atomixsoft.game.players.Player;
import dev.atomixsoft.gui.GroupAdapter;

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
