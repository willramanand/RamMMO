package com.gmail.willramanand.RamMMO.enums;

public enum Commands {
    MAIN("mmo"),
    BOSS("boss"),
    VERSION("version"),
    MOBS("mobs"),
    HELP("help"),
    PASSIVE("passive"),
    ITEM("item")

    ;


    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
