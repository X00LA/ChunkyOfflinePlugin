package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.CommandSender;

public class ConfigCommand extends CommandBase {

    public ConfigCommand(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        super(plugin, configManager, chunkyManager, "chunkyoffline", "chunkyoffline.admin.config");
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sendMessage(sender, plugin.getLanguageManager().getMessage("config.usage.main"));
            sendMessage(sender, plugin.getLanguageManager().getMessage("config.options.info"));
            return true;
        }

        String subCommand = args[1].toLowerCase();

        if (subCommand.equalsIgnoreCase("set")) {
            return handleSet(sender, args);
        } else if (subCommand.equalsIgnoreCase("get")) {
            return handleGet(sender, args);
        } else {
            sendError(sender, plugin.getLanguageManager().getMessage("config.subcommand.unknown", subCommand));
            return true;
        }
    }

    private boolean handleSet(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sendError(sender, plugin.getLanguageManager().getMessage("config.usage.set"));
            return true;
        }

        String option = args[2].toLowerCase();
        String value = args[3];

        try {
            switch (option) {
                case "radius":
                    int radius = Integer.parseInt(value);
                    if (radius <= 0) {
                        sendError(sender, plugin.getLanguageManager().getMessage("config.radius.must_positive"));
                        return true;
                    }
                    configManager.setRadius(radius);
                    sendMessage(sender, plugin.getLanguageManager().getMessage("config.radius.set", radius));
                    break;

                case "center-x":
                    int x = Integer.parseInt(value);
                    configManager.setCenterX(x);
                    sendMessage(sender, plugin.getLanguageManager().getMessage("config.center_x.set", x));
                    break;

                case "center-z":
                    int z = Integer.parseInt(value);
                    configManager.setCenterZ(z);
                    sendMessage(sender, plugin.getLanguageManager().getMessage("config.center_z.set", z));
                    break;

                default:
                    sendError(sender, plugin.getLanguageManager().getMessage("config.option.unknown", option));
                    return true;
            }
        } catch (NumberFormatException e) {
            sendError(sender, plugin.getLanguageManager().getMessage("config.number.invalid", option));
            return true;
        }

        return true;
    }

    private boolean handleGet(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sendError(sender, plugin.getLanguageManager().getMessage("config.usage.get"));
            return true;
        }

        String option = args[2].toLowerCase();

        switch (option) {
            case "radius":
                sendMessage(sender, plugin.getLanguageManager().getMessage("config.radius.current", configManager.getRadius()));
                break;

            case "center-x":
                sendMessage(sender, plugin.getLanguageManager().getMessage("config.center_x.current", configManager.getCenterX()));
                break;

            case "center-z":
                sendMessage(sender, plugin.getLanguageManager().getMessage("config.center_z.current", configManager.getCenterZ()));
                break;

            default:
                sendError(sender, plugin.getLanguageManager().getMessage("config.option.unknown", option));
                return true;
        }

        return true;
    }
}
