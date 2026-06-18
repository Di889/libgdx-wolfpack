package com.diogenes.wolfpack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.diogenes.wolfpack.campaign.Campaign;
import com.diogenes.wolfpack.entities.Alpha;
import com.diogenes.wolfpack.entities.Healer;
import com.diogenes.wolfpack.entities.Scout;
import com.diogenes.wolfpack.entities.Wolf;
import com.diogenes.wolfpack.screens.BattleScreen;

import java.util.ArrayList;
import java.util.List;

public class WolfPack extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        Campaign campaign = new Campaign(createStartingWolves());

        this.setScreen(new BattleScreen(this, campaign));
    }

    private List<Wolf> createStartingWolves() {
        List<Wolf> wolves = new ArrayList<>();
        wolves.add(new Alpha());
        wolves.add(new Healer());
        wolves.add(new Scout());
        return wolves;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
