package com.diogenes.wolfpack.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.diogenes.wolfpack.WolfPack;
import com.diogenes.wolfpack.assets.AssetLoader;
import com.diogenes.wolfpack.campaign.Campaign;

public class MainMenuScreen implements Screen {

    private final WolfPack game;
    private final AssetLoader assets;
    private final Viewport viewport;
    private String playerName = "";

    public MainMenuScreen(final WolfPack game, final AssetLoader assets) {
        this.game = game;
        this.assets = assets;
        this.viewport = new FitViewport(WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (character == '\b') {
                    if (!playerName.isEmpty()) {
                        playerName = playerName.substring(0, playerName.length() - 1);
                    }
                } else if (character == '\r' || character == '\n') {
                    if (!playerName.trim().isEmpty()) {
                        startGame();
                    }
                } else if (playerName.length() < 14 && (Character.isLetterOrDigit(character) || character == ' ')) {
                    playerName += character;
                }
                return false;
            }
        });
    }

    private void startGame() {
        Gdx.input.setInputProcessor(null);
        Campaign campaign = new Campaign(game.createStartingWolves());
        campaign.setPlayerName(playerName);
        game.setScreen(new BattleScreen(game, campaign, assets));
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        game.batch.begin();

        if (assets.menuBackground != null) {
            game.batch.draw(assets.menuBackground, 0, 0, WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
        }

        game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "WOLFPACK", 550, 480);

        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "NOME DO JOGADOR:", 545, 360);

        game.font.setColor(Color.YELLOW);
        String cursor = (System.currentTimeMillis() % 1000 < 500) ? "_" : "";
        game.font.draw(game.batch, playerName + cursor, 545, 320);

        if (!playerName.trim().isEmpty()) {
            game.font.setColor(Color.LIGHT_GRAY);
            game.font.draw(game.batch, "Pressione ENTER para iniciar", 480, 200);
        }

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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {}
}
