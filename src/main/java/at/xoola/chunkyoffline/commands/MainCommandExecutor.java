package at.xoola.chunkyoffline.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;

public class MainCommandExecutor implements CommandExecutor, TabCompleter {

    private final ChunkyOfflinePlugin plugin;
    private final ConfigManager configManager;
    private final ChunkyManager chunkyManager;
    
    private final StartCommand startCommand;
    private final PauseCommand pauseCommand;
    private final ResumeCommand resumeCommand;
    private final CancelCommand cancelCommand;
    private final ConfigCommand configCommand;

    public MainCommandExecutor(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.chunkyManager = chunkyManager;
        
        // Initialize subcommands
        this.startCommand = new StartCommand(plugin, configManager, chunkyManager);
        this.pauseCommand = new PauseCommand(plugin, configManager, chunkyManager);
        this.resumeCommand = new ResumeCommand(plugin, configManager, chunkyManager);
        this.cancelCommand = new CancelCommand(plugin, configManager, chunkyManager);
        this.configCommand = new ConfigCommand(plugin, configManager, chunkyManager);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "start":
                if (!sender.hasPermission("chunkyoffline.admin.start")) {
                    sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
                    return true;
                }
                return startCommand.executeCommand(sender, args);
                
            case "pause":
                if (!sender.hasPermission("chunkyoffline.admin.pause")) {
                    sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
                    return true;
                }
                return pauseCommand.executeCommand(sender, args);
                
            case "resume":
                if (!sender.hasPermission("chunkyoffline.admin.resume")) {
                    sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
                    return true;
                }
                return resumeCommand.executeCommand(sender, args);
                
            case "cancel":
                if (!sender.hasPermission("chunkyoffline.admin.cancel")) {
                    sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
                    return true;
                }
                return cancelCommand.executeCommand(sender, args);
                
            case "config":
                if (!sender.hasPermission("chunkyoffline.admin.config")) {
                    sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
                    return true;
                }
                return configCommand.executeCommand(sender, args);
                
            default:
                sendError(sender, plugin.getLanguageManager().getMessage("command.unknown", subCommand));
                sendHelp(sender);
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("start", "pause", "resume", "cancel", "config");
            for (String subCommand : subCommands) {
                if (subCommand.startsWith(args[0].toLowerCase())) {
                    // Check permissions
                    if (sender.hasPermission("chunkyoffline.admin." + subCommand)) {
                        completions.add(subCommand);
                    }
                }
            }
        } else if (args.length > 1 && args[0].equalsIgnoreCase("config")) {
            // Handle config tab completion
            if (args.length == 2) {
                if ("set".startsWith(args[1].toLowerCase())) completions.add("set");
                if ("get".startsWith(args[1].toLowerCase())) completions.add("get");
            } else if (args.length == 3 && (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("get"))) {
                List<String> options = Arrays.asList("radius", "center-x", "center-z");
                for (String option : options) {
                    if (option.startsWith(args[2].toLowerCase())) {
                        completions.add(option);
                    }
                }
            }
        }
        
        return completions;
    }
    
    private void sendHelp(CommandSender sender) {
        if (!sender.hasPermission("chunkyoffline.admin.info")) {
            sendError(sender, plugin.getLanguageManager().getMessage("command.no_permission"));
            return;
        }
        
        sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.header"));
        if (sender.hasPermission("chunkyoffline.admin.start")) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.start"));
        }
        if (sender.hasPermission("chunkyoffline.admin.pause")) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.pause"));
        }
        if (sender.hasPermission("chunkyoffline.admin.resume")) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.resume"));
        }
        if (sender.hasPermission("chunkyoffline.admin.cancel")) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.cancel"));
        }
        if (sender.hasPermission("chunkyoffline.admin.config")) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("command.help.config"));
        }
    }

    private void sendMessage(CommandSender sender, String message) {
        String prefix = plugin.getLanguageManager().getMessage("prefix");
        sender.sendMessage(prefix + " §f" + message);
    }

    private void sendError(CommandSender sender, String message) {
        String prefix = plugin.getLanguageManager().getMessage("prefix");
        sender.sendMessage(prefix + " §c" + message);
    }
}