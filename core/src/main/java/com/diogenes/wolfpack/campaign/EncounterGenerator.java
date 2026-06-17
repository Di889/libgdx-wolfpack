package com.diogenes.wolfpack.campaign;

import com.diogenes.wolfpack.entities.Bear;
import com.diogenes.wolfpack.entities.Boar;
import com.diogenes.wolfpack.entities.BossBear;
import com.diogenes.wolfpack.entities.Deer;
import com.diogenes.wolfpack.entities.Enemy;
import com.diogenes.wolfpack.entities.Fox;
import com.diogenes.wolfpack.entities.Rabbit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EncounterGenerator {

    private static final double RABBIT_APPEARANCE_CHANCE = 0.20;

    private final Random random;

    public EncounterGenerator() {
        this.random = new Random();
    }

    public List<Enemy> generateEncounter(int day) {
        List<Enemy> encounter = buildBaseEncounter(day);

        if (random.nextDouble() < RABBIT_APPEARANCE_CHANCE) {
            encounter.add(new Rabbit());
        }

        return encounter;
    }

    private List<Enemy> buildBaseEncounter(int day) {
        List<Enemy> encounter = new ArrayList<>();

        switch (day) {
            case 1:
                encounter.add(new Boar());
                break;

            case 2:
                encounter.add(new Fox());
                encounter.add(new Fox());
                break;

            case 3:
                encounter.add(new Boar());
                encounter.add(new Fox());
                break;

            case 4:
                if (random.nextDouble() < 0.60) {
                    encounter.add(new Deer());
                    encounter.add(new Fox());
                } else {
                    encounter.add(new Boar());
                    encounter.add(new Fox());
                }
                break;

            case 5:
                if (random.nextDouble() < 0.50) {
                    encounter.add(new Deer());
                    encounter.add(new Fox());
                } else {
                    encounter.add(new Bear());
                }
                break;

            case 6:
                encounter.add(new Bear());
                encounter.add(new Fox());
                break;

            case 7:
                encounter.add(new BossBear());
                break;

            default:
                throw new IllegalArgumentException("dia sem encontros, dia: " + day);
        }

        return encounter;
    }
}
