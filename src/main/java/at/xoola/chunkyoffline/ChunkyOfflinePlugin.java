package at.xoola.chunkyoffline;

import org.bukkit.plugin.java.JavaPlugin;

import at.xoola.chunkyoffline.commands.MainCommandExecutor;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.language.SimpleLanguageManager;
import at.xoola.chunkyoffline.listener.ServerListener;
import at.xoola.chunkyoffline.manager.ChunkyManager;

public class ChunkyOfflinePlugin extends JavaPlugin {

    private static ChunkyOfflinePlugin instance;
    private ConfigManager configManager;
    private ChunkyManager chunkyManager;
    private SimpleLanguageManager languageManager;
    private boolean periodicTaskRunning = false;

    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config
        saveDefaultConfig();
        
        // Initialize language manager
        this.languageManager = new SimpleLanguageManager(this);
        
        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.chunkyManager = new ChunkyManager(this, configManager);
        
        // Register commands
        registerCommands();
        
        // Register listeners
        registerListeners();
        
        // Start periodic check task
        startPeriodicTask();
        
        getLogger().info(languageManager.getMessage("plugin.enabled"));
    }

    @Override
    public void onDisable() {
        // Stop periodic task
        periodicTaskRunning = false;
        
        // Cleanup if needed
        getLogger().info(languageManager.getMessage("plugin.disabled"));
    }

    private void registerCommands() {
        // Register the main command handler
        org.bukkit.command.PluginCommand cmd = getCommand("chunkyoffline");
        if (cmd != null) {
            MainCommandExecutor mainCommand = new MainCommandExecutor(this, configManager, chunkyManager);
            cmd.setExecutor(mainCommand);
            cmd.setTabCompleter(mainCommand);
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ServerListener(this, configManager, chunkyManager), this);
    }

    private void startPeriodicTask() {
        periodicTaskRunning = true;
        
        // Use a repeating task every second (20 ticks) - similar to datapack but less frequent
        // Based on original datapack logic: check players every tick and react accordingly
        Thread.ofVirtual().name("ChunkyOffline-Monitor").start(() -> {
            getLogger().info(languageManager.getMessage("monitor.started"));
            
            while (periodicTaskRunning && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000); // Check every second instead of every minute
                    
                    if (!periodicTaskRunning) break;
                    
                    // Implement datapack-style logic: check status and react
                    performDatapackStyleCheck();
                    
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    getLogger().info(languageManager.getMessage("monitor.interrupted"));
                    break;
                } catch (Exception e) {
                    getLogger().warning(languageManager.getMessage("monitor.error", e.getMessage()));
                }
            }
            
            getLogger().info(languageManager.getMessage("monitor.stopped"));
        });
    }

    private void performDatapackStyleCheck() {
        // Schedule the check on the main thread to avoid Folia async command issues
        getServer().getGlobalRegionScheduler().run(this, (task) -> {
            try {
                boolean hasPlayers = !getServer().getOnlinePlayers().isEmpty();
                boolean autoModeEnabled = chunkyManager.isAutoModeEnabled();
                boolean currentlyGenerating = chunkyManager.isGenerating();
                
                // Only proceed if auto mode is enabled
                if (!autoModeEnabled) {
                    return;
                }
                
                // Datapack logic translation with spam prevention:
                // Only perform action if status change is needed
                
                // Case 1: Currently generating but players are online → need to pause
                if (currentlyGenerating && hasPlayers) {
                    chunkyManager.pause();
                    // Status is set inside pause() method
                    return;
                }
                
                // Case 2: Currently NOT generating but server is empty → need to resume
                if (!currentlyGenerating && !hasPlayers) {
                    chunkyManager.resume();
                    // Status is set inside resume() method
                    return;
                }
                
                // All other cases: no action needed (correct state)
                
            } catch (Exception e) {
                getLogger().warning(languageManager.getMessage("error.datapack_check", e.getMessage()));
            }
        });
    }

    public static ChunkyOfflinePlugin getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ChunkyManager getChunkyManager() {
        return chunkyManager;
    }

    public SimpleLanguageManager getLanguageManager() {
        return languageManager;
    }
}
