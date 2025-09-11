package dev.atomixsoft.gui;

import com.badlogic.gdx.scenes.scene2d.Group;

public abstract class GroupAdapter extends Group {

    public abstract void update();

    public void removeFromParent() {
        if(!hasParent()) return;
        getParent().removeActor(this);
    }

}
