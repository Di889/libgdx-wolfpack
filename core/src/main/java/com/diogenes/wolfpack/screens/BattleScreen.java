package com.diogenes.wolfpack.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.utils.ScreenUtils;
import com.diogenes.wolfpack.WolfPack;
import com.diogenes.wolfpack.battle.BattleAction;
import com.diogenes.wolfpack.battle.BattleManager;
import com.diogenes.wolfpack.battle.BattleState;
import com.diogenes.wolfpack.entities.*;
import com.diogenes.wolfpack.skills.Skill;
import com.diogenes.wolfpack.skills.TargetingType;

import java.util.ArrayList;
import java.util.List;


// width: 640 height:480
public class BattleScreen implements Screen {

    final WolfPack game;
    private BattleManager battleManager;
    private List<Wolf> wolves;
    private List<Enemy> enemies;
    private BattleState currentState;

    private Skill selectedSkill;
    private Unit selectedTarget;
    private int currentSkillIndex;
    private int currentTargetIndex;
    private List<? extends Unit> selectedTargetsList;
    private Unit lastTurnStartProcessedFor;

    public BattleScreen(final WolfPack game) {
        this.game = game;
    }

    @Override
    public void show() {
        initUnits();

        battleManager = new BattleManager(wolves, enemies);

        currentSkillIndex = 0;
        currentTargetIndex = 0;
        lastTurnStartProcessedFor = null;

        beginCurrentUnitTurn();
    }

    @Override
    public void render(float delta) {

        handleInput();
        update();

        draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    // test setup
    private void initUnits(){
        wolves = new ArrayList<>();
        enemies = new ArrayList<>();

        wolves.add(new Alpha());
        wolves.add(new Healer());
        wolves.add(new Scout());
        enemies.add(new Boar());
    }

    private void update(){
        if(checkBattleOver()) return;

        if (battleManager.getCurrentUnit() instanceof Wolf) {

            if (currentState != BattleState.SELECT_SKILL && currentState != BattleState.SELECT_TARGET) {
                currentState = BattleState.SELECT_SKILL;
            }

        } else {
            currentState = BattleState.ENEMY_TURN;
            handleEnemyTurn();
            advanceToNextTurn();
        }
    }

    private void beginCurrentUnitTurn(){
        Unit currentUnit = battleManager.getCurrentUnit();

        if(currentUnit != lastTurnStartProcessedFor){
            battleManager.processTurnStart(currentUnit);
            lastTurnStartProcessedFor = currentUnit;
        }

        currentState = (currentUnit instanceof Wolf) ? BattleState.SELECT_SKILL : BattleState.ENEMY_TURN;
    }

    private void advanceToNextTurn(){
        battleManager.nextTurn();
        beginCurrentUnitTurn();
    }

    private boolean checkBattleOver(){
        if(battleManager.playerLost()) {
            currentState = BattleState.DEFEAT;
            return true;
        }
        if(battleManager.playerWon()) {
            currentState = BattleState.VICTORY;
            return true;
        }
        return false;
    }

    // need to add a waiting time, or "space to continue"
    private void handleEnemyTurn(){
        BattleAction action = ((Enemy) battleManager.getCurrentUnit()).chooseAction(battleManager.getWolves());
        battleManager.executeAction(battleManager.getCurrentUnit(), action);
    }

    private void handleInput() {
        if(currentState == BattleState.SELECT_SKILL){

            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
                if(currentSkillIndex > 0){
                    currentSkillIndex--;
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
                if(currentSkillIndex < (battleManager.getCurrentUnit().getSkills().size()-1)){
                    currentSkillIndex++;
                }
            }

            selectedSkill = battleManager.getCurrentUnit().getSkills().get(currentSkillIndex);

            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                TargetingType targetingType = selectedSkill.getTargetingType();

                if(targetingType == TargetingType.ALL_ENEMIES
                    || targetingType == TargetingType.ALL_ALLIES
                    || targetingType == TargetingType.SELF){
                    // pass null target to battlemanager as he has logic to handle it
                    confirmAndExecute(null);
                } else {
                    selectedTargetsList = (targetingType == TargetingType.SINGLE_ALLY)
                        ? battleManager.getWolves()
                        : battleManager.getEnemies();
                    currentTargetIndex = 0;
                    currentState = BattleState.SELECT_TARGET;
                }
            }
        }

        else if(currentState == BattleState.SELECT_TARGET){

            if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
                if(currentTargetIndex > 0){
                    currentTargetIndex--;
                }
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
                if(currentTargetIndex < (selectedTargetsList.size()-1)){
                    currentTargetIndex++;
                }
            }
            selectedTarget = selectedTargetsList.get(currentTargetIndex);
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                confirmAndExecute(selectedTarget);
            }

        }

    }

    // execute the selected skill and advances turn
    private void confirmAndExecute(Unit target){
        battleManager.executeAction(battleManager.getCurrentUnit(), new BattleAction(selectedSkill, target));
        currentSkillIndex = 0;
        currentTargetIndex = 0;
        advanceToNextTurn();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();

        // always draw base infos
        drawTurnInfo();
        drawWolves();
        drawEnemies();

        // draw contextual ui based on the state
        if (currentState == BattleState.SELECT_SKILL) {
            drawSkillSelection();
        } else if (currentState == BattleState.SELECT_TARGET) {
            drawTargetSelection();
        } else if (currentState == BattleState.VICTORY) {
            game.font.draw(game.batch, "VITORIA! A Matilha sobrevive.", 240, 240);
        } else if (currentState == BattleState.DEFEAT) {
            game.font.draw(game.batch, "DERROTA... A Matilha cai.", 240, 240);
        }

        game.batch.end();
    }

    private void drawTurnInfo() {
        game.font.draw(
            game.batch,
            "Turno Atual: " + battleManager.getCurrentUnit().getName(),
            10,
            470
        );
    }

    private void drawWolves() {
        int y = 430;
        game.font.draw(game.batch, "--- LOBOS ---", 10, y);
        y -= 20;

        for (Wolf w : battleManager.getWolves()) {
            game.font.draw(
                game.batch,
                String.format("%s: %d/%d HP", w.getName(), w.getHp(), w.getMaxHp()),
                10,
                y
            );
            y -= 20;
        }
    }

    private void drawEnemies() {
        int y = 430;
        int x = 400;

        game.font.draw(game.batch, "--- INIMIGOS ---", x, y);
        y -= 20;

        for (Enemy e : battleManager.getEnemies()) {
            game.font.draw(
                game.batch,
                String.format("%s: %d/%d HP", e.getName(), e.getHp(), e.getMaxHp()),
                x,
                y
            );
            y -= 20;
        }
    }

    private void drawSkillSelection() {
        int y = 200;
        game.font.draw(game.batch, "Escolha a Habilidade (Setas Esquerda/Direita, Espaço para confirmar):", 10, y);
        y -= 30;

        List<Skill> skills = battleManager.getCurrentUnit().getSkills();
        for (int i = 0; i < skills.size(); i++) {
            String cursor = (i == currentSkillIndex) ? "> " : "  ";
            game.font.draw(
                game.batch,
                cursor + skills.get(i).getName(),
                10,
                y
            );
            y -= 20;
        }
    }

    private void drawTargetSelection() {
        int y = 200;
        game.font.draw(game.batch, "Escolha o Alvo (Setas Esquerda/Direita, Espaço para confirmar):", 10, y);
        y -= 30;

        for (int i = 0; i < selectedTargetsList.size(); i++) {
            String cursor = (i == currentTargetIndex) ? "> " : "  ";
            game.font.draw(
                game.batch,
                cursor + selectedTargetsList.get(i).getName(),
                10,
                y
            );
            y -= 20;
        }
    }


}
