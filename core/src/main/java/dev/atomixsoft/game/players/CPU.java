package dev.atomixsoft.game.players;

public class CPU extends Player {

    private int m_Tier;

    public CPU(int tier) {
        super();

        m_Tier = tier;
        setCPU(true);
    }

    public CPU(int tier, int maxHandSize) {
        super(maxHandSize);

        m_Tier = tier;
        setCPU(true);
    }

    public int getTier() {
        return m_Tier;
    }

}
