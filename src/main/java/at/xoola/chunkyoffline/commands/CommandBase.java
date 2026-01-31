package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class CommandBase implements CommandExecutor {

    protected final ChunkyOfflinePlugin plugin;
    protected final ConfigManager configManager;
    protected final ChunkyManager chunkyManager;
    protected final String permission;
    protected final String commandName;

    public CommandBase(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager, String commandName, String permission) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.chunkyManager = chunkyManager;
        this.commandName = commandName;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("command.no_permission"));
            return true;
        }

        return executeCommand(sender, args);
    }

    protected abstract boolean executeCommand(CommandSender sender, String[] args);

    protected void sendMessage(CommandSender sender, String message) {
        if (sender.hasPermission("chunkyoffline.admin.info")) {
            sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + " §f" + message);
        }
    }

    protected void sendError(CommandSender sender, String message) {
        sender.sendMessage(plugin.getLanguageManager().getMessage("prefix") + " §c" + message);
    }
    
    protected void sendInfoBroadcast(String message) {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("chunkyoffline.admin.info")) {
                player.sendMessage(plugin.getLanguageManager().getMessage("prefix") + " §f" + message);
            }
        });
        // Also send to console
        plugin.getLogger().info(message);
    }
}
