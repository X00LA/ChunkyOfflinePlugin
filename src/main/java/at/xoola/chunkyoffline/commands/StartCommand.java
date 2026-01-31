package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.CommandSender;

public class StartCommand extends CommandBase {

    public StartCommand(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        super(plugin, configManager, chunkyManager, "chunkyoffline", "chunkyoffline.admin.start");
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        if (chunkyManager.isGenerating()) {
            sendError(sender, plugin.getLanguageManager().getMessage("chunk.already_running"));
            return true;
        }

        chunkyManager.start();
        sendMessage(sender, plugin.getLanguageManager().getMessage("chunk.started"));
        sendInfoBroadcast(plugin.getLanguageManager().getMessage("chunk.started_broadcast"));
        return true;
    }
}
