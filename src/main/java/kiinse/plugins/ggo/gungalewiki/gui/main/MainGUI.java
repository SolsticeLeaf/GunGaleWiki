package kiinse.plugins.ggo.gungalewiki.gui.main;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.gui.items.PlayerHead;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainGUI extends DarkGUI {

    // 53 slots
    private final Player player;

    protected MainGUI(@NotNull DarkWaterJavaPlugin plugin, @NotNull Player player) {
        super(plugin);
        this.player = player;
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin darkWaterJavaPlugin) {
        setItem(new PlayerHead(player));

    }
}
