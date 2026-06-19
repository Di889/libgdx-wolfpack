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
import com.diogenes.wolfpack.battle.BattleState;
import com.diogenes.wolfpack.campaign.Campaign;
import com.diogenes.wolfpack.campaign.CampActionType;
import com.diogenes.wolfpack.campaign.CampManager;
import com.diogenes.wolfpack.entities.Enemy;
import com.diogenes.wolfpack.entities.Wolf;

import java.util.ArrayList;
import java.util.List;

public class CampScreen implements Screen {

    final WolfPack game;
    private final Campaign campaign;
    private final CampManager campManager;
    private final List<Wolf> allWolves;
    private final AssetLoader assets;
    private final Viewport viewport;

    private BattleState currentState;

    private List<CampActionType> availableCampActions;
    private int currentCampActionIndex;
    private CampActionType selectedCampAction;
    private int currentCampTargetIndex;
    private String campResultMessage;
    private final List<Enemy> battleEnemies;

    // --- Configurações de Interface Alinhadas com o Padrão de Batalha ---
    private static final float BOTTOM_Y = 180;
    private static final float PANEL_WIDTH_PERCENT = 0.50f;
    private static final float STATUS_ICON_SIZE = 16;

    public CampScreen(final WolfPack game, final Campaign campaign, final List<Enemy> battleEnemies, final AssetLoader assets) {
        this.game = game;
        this.campaign = campaign;
        this.campManager = new CampManager();
        this.allWolves = campaign.getWolves();
        this.battleEnemies = battleEnemies;
        this.assets = assets;
        this.viewport = new FitViewport(WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
    }

    @Override
    public void show() {
        // Processa fim do dia (comida das carcaças e dano de fome)
        campManager.resolveDayEnd(battleEnemies, campaign);

        // Popula as ações com base estrita nas 4 opções principais obrigatórias
        availableCampActions = new ArrayList<>();
        availableCampActions.add(CampActionType.FEED);
        availableCampActions.add(CampActionType.TRAIN_ATTACK);
        availableCampActions.add(CampActionType.TRAIN_DEFENSE);
        availableCampActions.add(CampActionType.TRAIN_MAX_HP);

        currentCampActionIndex = 0;
        currentCampTargetIndex = 0;
        campResultMessage = null;
        currentState = BattleState.CAMP_SELECT_ACTION;
    }

    @Override
    public void render(float delta) {
        handleInput();
        draw();
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

    // Retorna apenas a alcateia sobrevivente para listagem e seleção
    private List<Wolf> getAliveWolves() {
        List<Wolf> alive = new ArrayList<>();
        for (Wolf wolf : allWolves) {
            if (wolf.isAlive()) {
                alive.add(wolf);
            }
        }
        return alive;
    }

    // Validador preventivo para checar se o lobo já concluiu aquele tipo de treinamento
    private boolean isAlreadyTrained(CampActionType action, Wolf wolf) {
        switch (action) {
            case TRAIN_ATTACK: return wolf.hasTrainedAttack();
            case TRAIN_DEFENSE: return wolf.hasTrainedDefense();
            case TRAIN_MAX_HP: return wolf.hasTrainedHealth();
            default: return false; // Alimentação sempre permitida
        }
    }

    private void handleInput() {
        List<Wolf> targets = getAliveWolves();

        if (currentState == BattleState.CAMP_SELECT_ACTION) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (currentCampActionIndex > 0) currentCampActionIndex--;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (currentCampActionIndex < availableCampActions.size() - 1) currentCampActionIndex++;
            }

            selectedCampAction = availableCampActions.get(currentCampActionIndex);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (!campaign.canAfford(selectedCampAction.getCost())) {
                    campResultMessage = "Recursos insuficientes para esta ação!";
                } else if (targets.isEmpty()) {
                    campResultMessage = "Toda a matilha sucumbiu...";
                } else {
                    campResultMessage = null;
                    currentCampTargetIndex = 0;
                    currentState = BattleState.CAMP_SELECT_TARGET;
                }
            }

        } else if (currentState == BattleState.CAMP_SELECT_TARGET) {
            // Permite retroceder ao menu de ações caso queira mudar de ideia
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                campResultMessage = null;
                currentState = BattleState.CAMP_SELECT_ACTION;
                return;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                if (currentCampTargetIndex > 0) currentCampTargetIndex--;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                if (currentCampTargetIndex < targets.size() - 1) currentCampTargetIndex++;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                Wolf target = targets.get(currentCampTargetIndex);

                // SALVAGUARDA: Se já tiver treinado, bloqueia a execução ANTES de gastar comida
                if (isAlreadyTrained(selectedCampAction, target)) {
                    campResultMessage = target.getName() + " já realizou este treinamento específico!";
                } else {
                    applyCampAction(selectedCampAction, target);
                    advanceToNextDay();
                }
            }
        }
    }

    private void applyCampAction(CampActionType type, Wolf target) {
        boolean success;
        switch (type) {
            case FEED:
                success = campManager.feed(target, campaign);
                break;
            case TRAIN_ATTACK:
                success = campManager.trainAttack(target, campaign);
                break;
            case TRAIN_DEFENSE:
                success = campManager.trainDefense(target, campaign);
                break;
            case TRAIN_MAX_HP:
                success = campManager.trainMaxHp(target, campaign);
                break;
            default:
                success = false;
        }

        if (!success) {
            campResultMessage = "Falha ao aplicar a ação. Verifique recursos ou restrições.";
        }
    }

    private void advanceToNextDay() {
        campaign.advanceDay();
        game.setScreen(new BattleScreen(game, campaign, assets));
    }

    // ===================== DESENHO DA INTERFACE =====================

    private void draw() {
        viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        game.batch.begin();

        // 1. Renderizar Cenário de Fundo do Acampamento
        if (assets.campBackground != null) {
            game.batch.draw(assets.campBackground, 0, 0, WolfPack.WORLD_WIDTH, WolfPack.WORLD_HEIGHT);
        }

        // 2. Faixa Escura Superior de Status Global
        drawTopBanner();

        // 3. Console Rebaixado Inferior Dividido em Duas Seções Visuais
        drawBottomConsole();

        game.batch.end();
    }

    private void drawTopBanner() {
        game.batch.setColor(new Color(0f, 0f, 0f, 0.65f));
        game.batch.draw(assets.whitePixel, 0, 665, WolfPack.WORLD_WIDTH, 55);
        game.batch.setColor(Color.WHITE);

        game.font.setColor(Color.GOLD);
        game.font.draw(game.batch, "Fase de Acampamento (Preparação para o Próximo Dia)", 40, 698);

        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Comida em Estoque: ", 910, 698);
        game.font.setColor(Color.GREEN);
        game.font.draw(game.batch, String.valueOf(campaign.getFood()), 1080, 698);
        game.font.setColor(Color.WHITE);
    }

    private void drawBottomConsole() {
        float panelWidth = WolfPack.WORLD_WIDTH * PANEL_WIDTH_PERCENT;

        // Fundo opaco unificado na base para garantir contraste sobre arbustos
        game.batch.setColor(new Color(0.02f, 0.02f, 0.04f, 0.98f));
        game.batch.draw(assets.whitePixel, 0, 0, WolfPack.WORLD_WIDTH, BOTTOM_Y);
        game.batch.setColor(Color.WHITE);

        // Divisória Central entre Painel de Ações (Esquerda) e Seleção de Alvos (Direita)
        game.batch.setColor(Color.DARK_GRAY);
        game.batch.draw(assets.whitePixel, panelWidth, 0, 2, BOTTOM_Y);
        game.batch.setColor(Color.WHITE);

        // --- COLUNA ESQUERDA: SELEÇÃO DE AÇÃO ---
        drawActionSelectionColumn(panelWidth);

        // --- COLUNA DIREITA: SELEÇÃO DE ALVO ---
        drawTargetSelectionColumn(panelWidth);
    }

    private void drawActionSelectionColumn(float width) {
        float x = 30;
        float y = BOTTOM_Y - 15;

        game.font.setColor(Color.LIGHT_GRAY);
        game.font.draw(game.batch, "ESCOLHA A AÇÃO DIÁRIA (<- ->, ESPAÇO):", x, y);
        y -= 32;

        for (int i = 0; i < availableCampActions.size(); i++) {
            CampActionType type = availableCampActions.get(i);
            boolean isSelected = (currentState == BattleState.CAMP_SELECT_ACTION && i == currentCampActionIndex);

            String cursor = isSelected ? "> " : "  ";
            Color itemColor = isSelected ? Color.YELLOW : Color.WHITE;

            game.font.setColor(itemColor);
            game.font.draw(game.batch, cursor + type.getLabel() + " (Custo: " + type.getCost() + " Comida)", x, y);
            y -= 25;
        }

        // Rodapé de mensagens de alerta do sistema na base esquerda
        if (campResultMessage != null) {
            game.font.setColor(Color.ORANGE);
            game.font.draw(game.batch, campResultMessage, x, 25);
        }
        game.font.setColor(Color.WHITE);
    }

    private void drawTargetSelectionColumn(float width) {
        float x = width + 30;
        float y = BOTTOM_Y - 15;

        List<Wolf> activeWolves = getAliveWolves();

        if (currentState == BattleState.CAMP_SELECT_ACTION) {
            game.font.setColor(Color.GRAY);
            game.font.draw(game.batch, "Aguardando confirmação da ação...", x, y);
            y -= 32;
        } else {
            game.font.setColor(Color.LIGHT_GRAY);
            game.font.draw(game.batch, "SELECIONE O LOBO ALVO (<- ->, ESPAÇO):", x, y);
            y -= 32;
        }

        for (int i = 0; i < activeWolves.size(); i++) {
            Wolf w = activeWolves.get(i);
            boolean isSelected = (currentState == BattleState.CAMP_SELECT_TARGET && i == currentCampTargetIndex);
            boolean alreadyHasTrainedThisStat = (selectedCampAction != null && isAlreadyTrained(selectedCampAction, w));

            String cursor = isSelected ? "> " : "  ";

            // Define a cor do item na lista com base no estado de seleção
            Color textColor;
            if (currentState == BattleState.CAMP_SELECT_ACTION) {
                textColor = Color.GRAY;
            } else {
                textColor = isSelected ? Color.YELLOW : Color.WHITE;
            }

            game.font.setColor(textColor);
            String statusText = String.format("%s%s: %d/%d HP", cursor, w.getName(), w.getHp(), w.getMaxHp());
            game.font.draw(game.batch, statusText, x, y);

            // Se o lobo já fez este treino específico, carimba o iconOkay ao lado do nome
            if (alreadyHasTrainedThisStat && assets.iconOkay != null) {
                // Posiciona o ícone logo após o texto médio estimado do nome/vida
                game.batch.draw(assets.iconOkay, x + 230, y - 13, STATUS_ICON_SIZE, STATUS_ICON_SIZE);
            }

            y -= 25;
        }

        // Instrução de cancelamento ativa na base direita
        if (currentState == BattleState.CAMP_SELECT_TARGET) {
            game.font.setColor(Color.GRAY);
            game.font.draw(game.batch, "[ESC / BACKSPACE] para voltar", x, 25);
        }
        game.font.setColor(Color.WHITE);
    }
}
