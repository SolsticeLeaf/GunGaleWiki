package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackButton implements GuiItem {

    private final GuiAction action;
    private final int slot;

    public BackButton(int slot, @NotNull GuiAction action) {
        this.slot = slot;
        this.action = action;
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return null;
        //TODO: Ну тут да
    }

    @Override
    public @NotNull String name() {
        return null;
    }

    @Override
    public @NotNull GuiAction action() {
        return action;
    }
}
