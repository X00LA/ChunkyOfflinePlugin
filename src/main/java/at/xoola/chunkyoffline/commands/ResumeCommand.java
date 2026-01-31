package at.xoola.chunkyoffline.commands;

import at.xoola.chunkyoffline.ChunkyOfflinePlugin;
import at.xoola.chunkyoffline.config.ConfigManager;
import at.xoola.chunkyoffline.manager.ChunkyManager;
import org.bukkit.command.CommandSender;

public class ResumeCommand extends CommandBase {

    public ResumeCommand(ChunkyOfflinePlugin plugin, ConfigManager configManager, ChunkyManager chunkyManager) {
        super(plugin, configManager, chunkyManager, "chunkyoffline", "chunkyoffline.admin.resume");
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        chunkyManager.resume();
        sendMessage(sender, plugin.getLanguageManager().getMessage("chunk.resumed"));
        sendInfoBroadcast(plugin.getLanguageManager().getMessage("chunk.resumed_broadcast"));
        return true;
    }
}
