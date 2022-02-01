package com.gmail.willramanand.RamMMO.commands;

public enum Commands {
    MAIN("mmo"),
    BOSS("boss"),
    LOCATEBOSS("locateboss"),
    VERSION("version"),
    MOBS("mobs"),
    HELP("help"),
    PASSIVE("passive"),
    ITEM("item"),

    ;


    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
