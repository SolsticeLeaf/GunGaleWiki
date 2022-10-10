package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.darkwaterapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.darkwaterapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.gui.menus.BookMarksGUI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BookMarkButton implements GuiItem {

    private final GunGaleWiki gunGaleWiki;
    private final ItemStack itemStack;
    private final DarkGUI lastGui;
    private final YamlFile config;
    private final int slot;

    public BookMarkButton(@NotNull PlayerLocale playerLocale,
                          @NotNull GunGaleWiki gunGaleWiki,
                          @NotNull DarkGUI lastGui, int slot) {
        this.gunGaleWiki = gunGaleWiki;
        this.lastGui = lastGui;
        this.config = gunGaleWiki.getConfiguration();
        this.slot = slot;
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(
                                            Material.valueOf(config.getString(Config.BUTTON_BOOKMARKS_MATERIAL)),
                                            messages.getStringMessage(playerLocale, Message.BUTTON_BOOKMARKS_NAME),
                                            messages.getComponentList(playerLocale, Message.BUTTON_BOOKMARKS_LORE),
                                            1,
                                            itemMeta -> {
                                                var cmd = config.getInt(Config.BUTTON_BOOKMARKS_CMD);
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
            lastGui.delete();
            // TODO: вставить лист предметов
            new BookMarksGUI(gunGaleWiki, player)
                    .setSize(53)
                    .open(player);
        });
    }
}
