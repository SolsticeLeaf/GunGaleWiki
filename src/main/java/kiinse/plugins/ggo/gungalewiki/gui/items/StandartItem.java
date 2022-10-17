package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StandartItem implements GuiItem {

    private final ItemStack itemStack;
    private final int pos;

    public StandartItem(int pos, @NotNull ItemStack itemStack) {
        this.pos = pos;
        this.itemStack = itemStack;
    }

    @Override
    public int slot() {
        return pos;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public @NotNull GuiAction action() {
        return (clickType, player) -> {};
    }
}
