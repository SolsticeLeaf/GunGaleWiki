package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import kiinse.plugins.ggo.gungalewiki.gui.menus.ItemsGUI;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FilterButton implements GuiItem {

    private final GunGaleWiki gunGaleWiki;
    private final ItemStack itemStack;
    private final String menuName;
    private final DarkGUI lastGui;
    private final List<String> items;
    private final int slot;

    public FilterButton(@NotNull FiltersButton filtersButton,
                        @NotNull GunGaleWiki gunGaleWiki,
                        @NotNull DarkGUI lastGui, int slot) {
        this.gunGaleWiki = gunGaleWiki;
        this.menuName = filtersButton.getMenuName();
        this.lastGui = lastGui;
        this.slot = slot;
        this.itemStack = filtersButton.getItemStack();
        this.items = filtersButton.getItems();
    }

    @Override
    public int slot() {
        return slot;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public @NotNull String name() {
        return itemStack.getDisplayName();
    }

    @Override
    public @NotNull GuiAction action() {
        return ((clickType, player) -> {
            lastGui.delete();
            new ItemsGUI(gunGaleWiki, player, items)
                    .setName(menuName)
                    .setSize(53)
                    .open(player);
        });
    }
}
