package dev.atomixsoft.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import dev.atomixsoft.GameMain;

public abstract class GroupAdapter extends Group {

    protected final GameMain m_Game;

    public GroupAdapter(GameMain game) {
        super();
        m_Game = game;
    }

    public GroupAdapter() {
        this((GameMain) Gdx.app.getApplicationListener());
    }

    public abstract void update();

    public void removeFromParent() {
        if(!hasParent()) return;
        getParent().removeActor(this);
    }

    protected void reset() {
        clearChildren(true);
    }

}
