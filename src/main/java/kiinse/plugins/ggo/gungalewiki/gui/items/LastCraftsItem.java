package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LastCraftsItem implements GuiItem {

    private final DarkWaterJavaPlugin plugin;

    public LastCraftsItem(@NotNull DarkWaterJavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public int slot() {
        return 0;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return null;
    }

    @Override
    public @NotNull String name() {
        return null;
    }

    @Override
    public @NotNull GuiAction action() {
        return null;
    }
}
