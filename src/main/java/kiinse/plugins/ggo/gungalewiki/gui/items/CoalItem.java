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

public class CoalItem implements GuiItem {

    private final ItemStack itemStack;
    private final int pos;

    public CoalItem(int pos, @NotNull GunGaleWiki gunGaleWiki, @NotNull PlayerLocale playerLocale) {
        this.pos = pos;
        var config = gunGaleWiki.getConfiguration();
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(Material.valueOf(config.getString(Config.FURNACE_COAL_MATERIAL)),
                                                  messages.getStringMessage(playerLocale, Message.FURNACE_COAL_NAME),
                                                  messages.getComponentList(playerLocale, Message.FURNACE_COAL_LORE),
                                                  1,
                                                  itemMeta -> {
                                                      var cmd = config.getInt(Config.FURNACE_COAL_CMD);
                                                      if (cmd != 0) itemMeta.setCustomModelData(cmd);
                                                  });
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
    public @NotNull String name() {
        return itemStack.getDisplayName();
    }

    @Override
    public @NotNull GuiAction action() {
        return (clickType, player) -> {};
    }
}

