package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LastCraftsButton implements GuiItem {

    private final GunGaleWiki gunGaleWiki;
    private final ItemStack itemStack;
    private final CreatedGui lastGui;
    private final YamlFile config;
    private final int slot;

    public LastCraftsButton(@NotNull PlayerLocale playerLocale,
                            @NotNull GunGaleWiki gunGaleWiki,
                            @NotNull CreatedGui lastGui, int slot) {
        this.gunGaleWiki = gunGaleWiki;
        this.lastGui = lastGui;
        this.config = gunGaleWiki.getConfiguration();
        this.slot = slot;
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(
                                            Material.valueOf(config.getString(Config.BUTTON_LASTCRAFTS_MATERIAL)),
                                            messages.getComponentMessage(playerLocale, Message.BUTTON_LAST_CRAFTS_NAME),
                                            messages.getComponentList(playerLocale, Message.BUTTON_LAST_CRAFTS_LORE),
                                            1,
                                            itemMeta -> {
                                                var cmd = config.getInt(Config.BUTTON_LASTCRAFTS_CMD);
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
            lastGui.delete();
            new GuiBuilder(player)
                    .setPage(0)
                    .setGui(Gui.LASTSEEN)
                    .setPageManager(new PageManager().setItems(gunGaleWiki.getPluginData().getUserData(player).getLastSeen(), 36))
                    .setLastGui(lastGui)
                    .setName(gunGaleWiki.getConfiguration().getString(Config.MENU_LASTSEEN_NAME))
                    .open(player);
        });
    }
}
