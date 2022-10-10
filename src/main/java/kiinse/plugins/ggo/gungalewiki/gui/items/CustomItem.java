package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.darkwaterapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.gui.menus.BookMarksGUI;
import kiinse.plugins.ggo.gungalewiki.gui.menus.FromThisItemCraftGUI;
import kiinse.plugins.ggo.gungalewiki.gui.menus.ThisItemCraftGUI;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomItem implements GuiItem {

    private final String item;
    private final CustomStack customStack;
    private final PluginData pluginData;
    private final DarkGUI fromGui;
    private final int pos;

    public CustomItem(@NotNull String item, int pos, @NotNull PluginData pluginData, @NotNull DarkGUI fromGui) {
        this.customStack = CustomStack.getInstance(item);
        this.item = item;
        this.pos = pos;
        this.pluginData = pluginData;
        this.fromGui = fromGui;
    }

    @Override
    public int slot() {
        return pos;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        return customStack.getItemStack();
    }

    @Override
    public @NotNull String name() {
        return customStack.getDisplayName();
    }

    @Override
    public @NotNull GuiAction action() {
        return (clickType, player) -> {
            if (clickType == ClickType.SHIFT_LEFT) {
                if (pluginData.hasPlayerItemInBookmarks(player, item)) {
                    pluginData.removeFromPlayerBookmarks(player, item);
                } else {
                    pluginData.addToPlayerBookmarks(player, item);
                }
                // TODO: Добавить оповещение игрока
                if (fromGui instanceof BookMarksGUI prev) {
                    prev.delete();
                    prev.open(player);
                }
                return;
            }
            var gunGaleWiki = GunGaleWiki.getInstance();
            var config = gunGaleWiki.getConfiguration();
            if (clickType == ClickType.LEFT) {
                fromGui.delete();
                new ThisItemCraftGUI(gunGaleWiki, player, itemStack())
                        .setName(config.getString(Config.MENU_THISITEM_NAME))
                        .setSize(53)
                        .open(player);
                return;
            }
            if (clickType == ClickType.RIGHT) {
                fromGui.delete();
                new FromThisItemCraftGUI(gunGaleWiki, player, itemStack())
                        .setName(config.getString(Config.MENU_FROMITEM_NAME))
                        .setSize(53)
                        .open(player);
            }
        };
    }
}
