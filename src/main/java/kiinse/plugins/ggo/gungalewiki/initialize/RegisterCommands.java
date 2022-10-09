package kiinse.plugins.ggo.gungalewiki.initialize;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.exceptions.CommandException;
import kiinse.plugins.ggo.darkwaterapi.core.commands.CommandManager;
import kiinse.plugins.ggo.gungalewiki.commands.CraftCommand;
import kiinse.plugins.ggo.gungalewiki.commands.CraftsCommand;
import kiinse.plugins.ggo.gungalewiki.commands.RecipesCommand;
import org.jetbrains.annotations.NotNull;

public class RegisterCommands {

    public RegisterCommands(@NotNull DarkWaterJavaPlugin plugin) throws NullPointerException, CommandException {
        plugin.sendLog("Registering commands...");
        new CommandManager(plugin)
                .registerCommand(new CraftCommand(plugin))
                .registerCommand(new CraftsCommand(plugin))
                .registerCommand(new RecipesCommand(plugin));
        plugin.sendLog("Commands registered");
    }
}
