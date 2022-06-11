package com.gmail.willramanand.RamMMO.boss;

import java.util.List;
import java.util.Random;

public enum Bosses {
    HEADLESS_HORSEMAN("&4The Headless Horseman"),
    THE_MINOTAUR("&4The Minotaur"),
    THE_GHOST("&4The Ghost"),
    ENDER_PRIEST("&4The Ender Priest"),
    THE_COLOSSUS("&4The Colossus"),
    THE_LEVIATHAN("&4The Leviathan"),

    ;

    private final String displayName;

    private static final List<Bosses> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();


    Bosses(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    public static Bosses randomBoss() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public static Bosses matchBoss(String name) {
        for (Bosses bosses : values()) {
            if (bosses.name().toLowerCase().equalsIgnoreCase(name)) return bosses;
        }
        return null;
    }
}
