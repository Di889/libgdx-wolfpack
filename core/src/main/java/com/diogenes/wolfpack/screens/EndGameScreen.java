package com.diogenes.wolfpack.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diogenes.wolfpack.WolfPack;
import com.diogenes.wolfpack.assets.AssetLoader;

public class EndGameScreen implements Screen {

    private final WolfPack game;
    private final AssetLoader assets;
    private final Viewport viewport;
    private final int daysSurvived;
    private final boolean isVictory;

    public EndGameScreen(final WolfPack game, final AssetLoader assets, int daysSurvived, boolean isVictory) {
        this.game = game;
        this.assets = assets;
        this.daysSurvived = daysSurvived;
        this.isVictory = isVictory;
        this.viewport = new FitViewport(WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        handleInput();
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MainMenuScreen(game, assets));
        }
    }

    private void draw() {
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        game.batch.begin();

        if (assets.menuBackground != null) {
            game.batch.draw(assets.menuBackground, 0, 0, WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
        }

        if (isVictory) {
            game.font.setColor(Color.GOLD);
            game.font.draw(game.batch, "VITÓRIA ABSOLUTA", 515, 450);
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Sua matilha sobreviveu aos 7 dias de inverno!", 420, 380);
        } else {
            game.font.setColor(Color.RED);
            game.font.draw(game.batch, "A MATILHA CAIU", 540, 450);
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Dias sobrevividos: " + daysSurvived, 530, 380);
        }

        game.font.setColor(Color.LIGHT_GRAY);
        game.font.draw(game.batch, "Pressione ESPACO para voltar ao Menu Principal", 415, 240);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
