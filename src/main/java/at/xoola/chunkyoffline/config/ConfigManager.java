package at.xoola.chunkyoffline.config;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final ChunkyOfflinePlugin plugin;
    private FileConfiguration config;

    public ConfigManager(ChunkyOfflinePlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public int getRadius() {
        return config.getInt("chunk-generation.radius", 10000);
    }

    public void setRadius(int radius) {
        config.set("chunk-generation.radius", radius);
        saveConfig();
    }

    public int getCenterX() {
        return config.getInt("chunk-generation.center.x", 0);
    }

    public void setCenterX(int x) {
        config.set("chunk-generation.center.x", x);
        saveConfig();
    }

    public int getCenterZ() {
        return config.getInt("chunk-generation.center.z", 0);
    }

    public void setCenterZ(int z) {
        config.set("chunk-generation.center.z", z);
        saveConfig();
    }

    public void setCenter(int x, int z) {
        config.set("chunk-generation.center.x", x);
        config.set("chunk-generation.center.z", z);
        saveConfig();
    }

    public boolean isGlobalMessagesEnabled() {
        return config.getBoolean("chunk-generation.global-messages", true);
    }

    public void setGlobalMessagesEnabled(boolean enabled) {
        config.set("chunk-generation.global-messages", enabled);
        saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
