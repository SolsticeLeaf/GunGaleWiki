package kiinse.plugins.ggo.gungalewiki.commands;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.commands.Command;
import kiinse.plugins.ggo.gungaleapi.api.commands.CommandContext;
import kiinse.plugins.ggo.gungaleapi.api.commands.DarkCommand;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class RecipesCommand extends DarkCommand {

    public RecipesCommand(@NotNull GunGaleJavaPlugin plugin) {
        super(plugin);
    }

    @Command(command = "recipes",
             disallowNonPlayer = true,
             permission = "gungalewiki.menu")
    public void recipes(@NotNull CommandContext context) {
        try {
            GunGaleWiki.getInstance().getPluginData().getUserData(DarkPlayerUtils.getPlayer(context.getSender())).getLastGui().open(context.getSender());
        } catch (Exception e) {
            getPlugin().sendLog(Level.WARNING, "Error on gui open: " + e.getMessage());
        }
    }
}
