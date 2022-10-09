package kiinse.plugins.ggo.gungalewiki.commands;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.commands.Command;
import kiinse.plugins.ggo.darkwaterapi.api.commands.CommandContext;
import org.jetbrains.annotations.NotNull;

public class CraftCommand {

    private final DarkWaterJavaPlugin plugin;

    public CraftCommand(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Command(command = "recipes",
             disallowNonPlayer = true,
             permission = "gungalewiki.menu")
    public void recipes(@NotNull CommandContext context) {

    }

}
