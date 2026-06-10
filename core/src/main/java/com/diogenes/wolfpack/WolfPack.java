package com.diogenes.wolfpack;

import com.badlogic.gdx.Game;
import com.diogenes.wolfpack.screens.BattleScreen;

public class WolfPack extends Game {

    @Override
    public void create() {
        this.setScreen(new BattleScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }
}
