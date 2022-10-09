package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.darkwaterapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.database.interfaces.PluginData;
import kiinse.plugins.ggo.gungalewiki.gui.GUIData;
import kiinse.plugins.ggo.gungalewiki.gui.craft.FromThisItemCraftGUI;
import kiinse.plugins.ggo.gungalewiki.gui.craft.ThisItemCraftGUI;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomItem implements GuiItem {

    private final String item;
    private final CustomStack customStack;
    private final PluginData pluginData;
    private final GUIData prevGui;
    private final int pos;

    public CustomItem(@NotNull String item, int pos, @NotNull PluginData pluginData, @NotNull GUIData prevGui) {
        this.customStack = CustomStack.getInstance(item);
        this.item = item;
        this.pos = pos;
        this.pluginData = pluginData;
        this.prevGui = prevGui;
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
                pluginData.addToPlayerBookmarks(player, item);
                DarkPlayerUtils.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_CHIME);
                return;
            }
            if (clickType == ClickType.LEFT) {
                prevGui.getPrevGui().delete();
                new ThisItemCraftGUI(prevGui).open(player);
                return;
            }
            if (clickType == ClickType.RIGHT) {
                prevGui.getPrevGui().delete();
                new FromThisItemCraftGUI(prevGui).open(player);
            }
        };
    }
}
