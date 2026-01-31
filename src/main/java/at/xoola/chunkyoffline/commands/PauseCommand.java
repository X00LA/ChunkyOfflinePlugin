package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.CommandSender;

public class PauseCommand extends CommandBase {

    public PauseCommand(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        super(plugin, configManager, chunkyManager, "chunkyoffline", "chunkyoffline.admin.pause");
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        chunkyManager.pause();
        sendMessage(sender, plugin.getLanguageManager().getMessage("chunk.paused"));
        sendInfoBroadcast(plugin.getLanguageManager().getMessage("chunk.paused_broadcast"));
        return true;
    }
}
