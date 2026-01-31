package at.xoola.chunkyoffline.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;

public class ServerListener implements Listener {

    private final ChunkyOfflinePlugin plugin;
    private final ConfigManager configManager;
    private final ChunkyManager chunkyManager;

    public ServerListener(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.chunkyManager = chunkyManager;
    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        // For Paper 1.21.11, use getType() instead of getLoadType()
        try {
            if (event.getType() != ServerLoadEvent.LoadType.STARTUP) {
                return;
            }
        } catch (NoSuchMethodError e) {
            // Fallback for older Paper versions - just proceed
            plugin.getLogger().info(plugin.getLanguageManager().getMessage("plugin.startup_detection"));
        }

        // Server loaded - ChunkyOffline is ready!
        plugin.getLogger().info(plugin.getLanguageManager().getMessage("plugin.ready"));
        plugin.getLogger().info(plugin.getLanguageManager().getMessage("plugin.ready.use"));
        
        // Send info to online players with permission
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("chunkyoffline.admin.info")) {
                plugin.getLanguageManager().sendPlayerMessage(player, "plugin.ready");
                plugin.getLanguageManager().sendPlayerMessage(player, "plugin.ready.use");
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Provide admin info to joining players with permission
        if (event.getPlayer().hasPermission("chunkyoffline.admin.info")) {
            // Inform about current status
            boolean autoMode = chunkyManager.isAutoModeEnabled();
            boolean generating = chunkyManager.isGenerating();
            
            if (autoMode) {
                if (generating) {
                    plugin.getLanguageManager().sendPlayerMessage(event.getPlayer(), "status.will_pause");
                } else {
                    plugin.getLanguageManager().sendPlayerMessage(event.getPlayer(), "status.auto_enabled");
                }
            } else {
                plugin.getLanguageManager().sendPlayerMessage(event.getPlayer(), "status.auto_disabled");
            }
        }
    }

    @EventHandler  
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Players leaving will be handled by the main monitor loop
        // Just log for debugging purposes
        if (chunkyManager.isAutoModeEnabled()) {
            plugin.getLogger().info(plugin.getLanguageManager().getMessage("player.left", event.getPlayer().getName()));
        }
    }
}
