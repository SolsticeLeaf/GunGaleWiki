package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackButton implements GuiItem {

    private final CreatedGui gui;
    private final ItemStack itemStack;
    private final int slot;

    public BackButton(@NotNull PlayerLocale playerLocale,
                      @NotNull GunGaleWiki gunGaleWiki, int slot, @NotNull CreatedGui gui) {
        this.gui = gui;
        this.slot = slot;

        var config = gunGaleWiki.getConfiguration();
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(
                                            Material.valueOf(config.getString(Config.BUTTON_BACK_MATERIAL)),
                                            messages.getStringMessage(playerLocale, Message.BUTTON_BACK_NAME),
                                            messages.getComponentList(playerLocale, Message.BUTTON_BACK_LORE),
                                            1,
                                            itemMeta -> {
                                                var cmd = config.getInt(Config.BUTTON_BACK_CMD);
                                                if (cmd != 0) itemMeta.setCustomModelData(cmd);
                                            });
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
            gui.delete();
            if (clickType == ClickType.SHIFT_RIGHT) {
                GuiUtils.getMainGui(player).open(player);
            } else {
                var lastGui = gui.getLastGui();
                assert lastGui.getPageManager() != null;
                new GuiBuilder(player)
                        .setItem(lastGui.getItem())
                        .setPage(lastGui.getPage())
                        .getGui(lastGui.getType())
                        .setLastGui(lastGui.getLastGui())
                        .setPageManager(lastGui.getPageManager())
                        .setStringItem(lastGui.getItem())
                        .setName(lastGui.getName())
                        .setSize(54)
                        .open(player);
            }
        });
    }
}
