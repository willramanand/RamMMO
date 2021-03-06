package com.gmail.willramanand.RamMMO.player;

import com.gmail.willramanand.RamMMO.RamMMO;
import com.gmail.willramanand.RamMMO.enums.Passive;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MMOPlayer {

    private final RamMMO plugin;

    private final Player player;
    private final UUID uuid;

    private final Map<Passive, Boolean> passives;
    private final Map<Passive, Integer> passiveLvl;

    private int personalDifficulty;

    private boolean saving;
    private boolean shouldSave;

    public MMOPlayer(RamMMO plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.uuid = this.player.getUniqueId();
        this.personalDifficulty = 0;
        this.saving = false;
        this.shouldSave = true;
        this.passives = new HashMap<>();
        this.passiveLvl = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setPassives(Passive passive, boolean enabled) {
        this.passives.put(passive, enabled);
    }

    public Boolean getPassives(Passive passive) {
        return passives.getOrDefault(passive, true);
    }

    public void setPersonalDifficulty(int difficulty) {
        personalDifficulty = difficulty;
    }

    public int getPersonalDifficulty() {
        return personalDifficulty;
    }

    public boolean isSaving() {
        return saving;
    }

    public void setSaving(boolean saving) {
        this.saving = saving;
    }

    public boolean shouldNotSave() {
        return !shouldSave;
    }

    public void setShouldSave(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

}
