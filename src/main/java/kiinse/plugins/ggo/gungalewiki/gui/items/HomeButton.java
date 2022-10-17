package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HomeButton implements GuiItem {

    private final CreatedGui gui;
    private final ItemStack itemStack;
    private final int slot;

    public HomeButton(@NotNull PlayerLocale playerLocale,
                      @NotNull GunGaleWiki gunGaleWiki, int slot, @NotNull CreatedGui gui) {
        this.gui = gui;
        this.slot = slot;
        var config = gunGaleWiki.getConfiguration();
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(Material.valueOf(config.getString(Config.BUTTON_HOME_MATERIAL)),
                                                  messages.getComponentMessage(playerLocale, Message.BUTTON_HOME_NAME),
                                                  messages.getComponentList(playerLocale, Message.BUTTON_HOME_LORE),
                                                  1,
                                                  itemMeta -> {
                                                      var cmd = config.getInt(Config.BUTTON_HOME_CMD);
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
        return ((clickType, player) -> {
            gui.delete();
            GuiUtils.getMainGui(player).open(player);
        });
    }
}
