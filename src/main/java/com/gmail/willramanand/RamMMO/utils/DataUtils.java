package com.gmail.willramanand.RamMMO.utils;

import com.gmail.willramanand.RamMMO.RamMMO;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;


public class DataUtils {

    private final static RamMMO plugin = RamMMO.getInstance();

    public static <T, Z> Z get(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().get(new NamespacedKey(plugin, key), type);
    }

    public static <T, Z> void set(PersistentDataHolder holder, String key, PersistentDataType<T, Z> type, Z value) {
        holder.getPersistentDataContainer().set(new NamespacedKey(plugin, key), type, value);
    }

    public static boolean has(PersistentDataHolder holder, String key) {
        return holder.getPersistentDataContainer().has(new NamespacedKey(plugin, key));
    }
}
