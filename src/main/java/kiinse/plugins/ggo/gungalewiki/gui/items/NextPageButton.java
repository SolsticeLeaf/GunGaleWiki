package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NextPageButton implements GuiItem {

    private final int slot;
    private final ItemStack itemStack;
    private final GuiAction guiAction;

    public NextPageButton(boolean hasPage, @NotNull PlayerLocale playerLocale,
                          @NotNull GunGaleWiki gunGaleWiki, int slot, @NotNull GuiAction action) {
        if (hasPage) {
            this.guiAction = action;
        } else {
            this.guiAction = ((clickType, player) -> {});
        }
        this.slot = slot;

        var config = gunGaleWiki.getConfiguration();
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(
                                            Material.valueOf(config.getString(hasPage ? Config.BUTTON_NEXTPAGE_ALLOW_MATERIAL
                                                                                      : Config.BUTTON_NEXTPAGE_DISALLOW_MATERIAL)),
                                            messages.getComponentMessage(playerLocale, Message.BUTTON_NEXT_PAGE_NAME),
                                            messages.getComponentList(playerLocale, Message.BUTTON_NEXT_PAGE_LORE),
                                            1,
                                            itemMeta -> {
                                                var cmd = config.getInt(hasPage ? Config.BUTTON_NEXTPAGE_ALLOW_CMD
                                                                                : Config.BUTTON_NEXTPAGE_DISALLOW_CMD);
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
    public @NotNull GuiAction action() {
        return guiAction;
    }
}
