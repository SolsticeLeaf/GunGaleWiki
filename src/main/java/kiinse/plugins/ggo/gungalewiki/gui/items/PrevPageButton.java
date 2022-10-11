package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PrevPageButton implements GuiItem {

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
