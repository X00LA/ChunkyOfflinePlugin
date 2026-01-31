package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.CommandSender;

public class CancelCommand extends CommandBase {

    public CancelCommand(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        super(plugin, configManager, chunkyManager, "chunkyoffline", "chunkyoffline.admin.cancel");
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        chunkyManager.cancel();
        sendMessage(sender, plugin.getLanguageManager().getMessage("chunk.cancelled"));
        sendInfoBroadcast(plugin.getLanguageManager().getMessage("chunk.cancelled_broadcast"));
        return true;
    }
}
