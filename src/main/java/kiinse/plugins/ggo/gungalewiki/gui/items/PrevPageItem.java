package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.gui.GUIData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PrevPageItem implements GuiItem {

    private final GUIData guiData;

    public PrevPageItem(@NotNull GUIData guiData) {
        this.guiData = guiData;
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
