package dev.atomixsoft.game;

import dev.atomixsoft.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {

    private static final int VS_PLAYER = -1;

    private final List<Player> m_Players;

    public Board() {
        m_Players = new ArrayList<>(2);
    }

    public void initializeMatch(int level) {
        if(level == VS_PLAYER && m_Players.get(1).isCPU())
            throw new IllegalStateException("VS_PLAYER flag enabled when there is a CPU in the game");
    }

    public void addPlayer(Player player, boolean isCPU) {
        if(m_Players.size() >= 2)
            throw new IllegalStateException("Can't have more than 2 Players in a Match!");

        player.setCPU(isCPU);
        m_Players.add(player);
    }

    public List<Player> getPlayers() {
        return m_Players;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(m_Players, board.m_Players);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(m_Players);
    }
}
