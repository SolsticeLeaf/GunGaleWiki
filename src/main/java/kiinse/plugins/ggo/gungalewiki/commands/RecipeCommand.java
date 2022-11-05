package kiinse.plugins.ggo.gungalewiki.commands;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.commands.Command;
import kiinse.plugins.ggo.gungaleapi.api.commands.CommandContext;
import kiinse.plugins.ggo.gungaleapi.api.commands.DarkCommand;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import org.jetbrains.annotations.NotNull;

public class RecipeCommand extends DarkCommand {

    public RecipeCommand(@NotNull GunGaleJavaPlugin plugin) {
        super(plugin);
    }

    @Command(command = "recipe",
             disallowNonPlayer = true,
             permission = "gungalewiki.menu")
    public void recipes(@NotNull CommandContext context) {
        try {
            GunGaleWiki.getInstance().getPluginData().getUserData(DarkPlayerUtils.getPlayer(context.getSender())).getLastGui().open(context.getSender());
        } catch (Exception e) {
            var message = e.getMessage();
            if (message != null && !message.contains("Name is null")) {
                getPlugin().sendLog("Error on gui open:", e);
                GuiUtils.getMainGui(DarkPlayerUtils.getPlayer(context.getSender())).open(context.getSender());
            }
        }
    }
}
