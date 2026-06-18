package com.diogenes.wolfpack.campaign;

public enum CampActionType {
    FEED("Alimentar", CampManager.FEED_COST),
    TRAIN_ATTACK("Treinar Ataque", CampManager.TRAIN_ATTACK_COST),
    TRAIN_DEFENSE("Treinar Defesa", CampManager.TRAIN_DEFENSE_COST),
    TRAIN_MAX_HP("Treinar Vida", CampManager.TRAIN_MAX_HP_COST),
    SKIP("Avançar", 0);

    private final String label;
    private final int cost;

    CampActionType(String label, int cost) {
        this.label = label;
        this.cost = cost;
    }

    public String getLabel() {
        return label;
    }

    public int getCost() {
        return cost;
    }
}
