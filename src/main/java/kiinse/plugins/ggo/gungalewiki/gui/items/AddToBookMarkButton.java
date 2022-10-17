package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungaleapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.enums.Replace;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AddToBookMarkButton implements GuiItem {

    private final GunGaleWiki gunGaleWiki;
    private final UserData userData;
    private final boolean hasItemInBookmarks;
    private final String item;
    private final ItemStack itemStack;
    private final CreatedGui fromGui;
    private final int slot;

    public AddToBookMarkButton(int slot, @NotNull PlayerLocale playerLocale,
                               @NotNull GunGaleWiki gunGaleWiki,
                               @NotNull CreatedGui fromGui, @NotNull Player player, @NotNull String item) {
        this.gunGaleWiki = gunGaleWiki;
        this.userData = gunGaleWiki.getPluginData().getUserData(player);
        this.hasItemInBookmarks = userData.hasItemInBookmarks(item);
        this.fromGui = fromGui;
        this.item = item;
        this.slot = slot;

        var config = gunGaleWiki.getConfiguration();
        var messages = gunGaleWiki.getMessages();
        this.itemStack = gunGaleWiki.getItemStackUtils()
                                    .getItemStack(Material.valueOf(config.getString(hasItemInBookmarks ? Config.BUTTON_REMOVE_BOOKMARKS_MATERIAL
                                                                                            : Config.BUTTON_ADD_BOOKMARKS_MATERIAL)),
                                                  messages.getComponentMessage(playerLocale, hasItemInBookmarks ? Message.BUTTON_REMOVE_BOOKMARKS_NAME
                                                          : Message.BUTTON_ADD_BOOKMARKS_NAME),
                                                  messages.getComponentList(playerLocale, hasItemInBookmarks ? Message.BUTTON_REMOVE_BOOKMARKS_LORE
                                                          : Message.BUTTON_ADD_BOOKMARKS_LORE),
                                                  1,
                                                  itemMeta -> {
                                                      var cmd = config.getInt(hasItemInBookmarks ? Config.BUTTON_REMOVE_BOOKMARKS_CMD
                                                                                      : Config.BUTTON_ADD_BOOKMARKS_CMD);
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
            var config = gunGaleWiki.getConfiguration();
            var messages = new DarkMessagesUtils(gunGaleWiki);
            if (userData.hasItemInBookmarks(item)) {
                userData.removeFromBookmarks(item);
                DarkPlayerUtils.playSound(player, Sound.valueOf(config.getString(Config.BOOKMARK_REMOVED_SOUND)));
                messages.sendMessage(player,
                                     Message.BOOKMARK_REMOVED,
                                     Replace.ITEM,
                                     GuiUtils.getNameByItemStack(CustomStack.getInstance(item).getItemStack()));
            } else {
                userData.addToBookmarks(item);
                DarkPlayerUtils.playSound(player, Sound.valueOf(config.getString(Config.BOOKMARK_ADDED_SOUND)));
                messages.sendMessage(player,
                                     Message.BOOKMARK_ADDED,
                                     Replace.ITEM,
                                     GuiUtils.getNameByItemStack(CustomStack.getInstance(item).getItemStack()));
            }
            gunGaleWiki.getPluginData().saveData(userData);
            fromGui.delete();
            assert fromGui.getPageManager() != null;
            new GuiBuilder(player)
                    .setPage(fromGui.getPage())
                    .setItem(fromGui.getItem())
                    .getGui(fromGui.getType())
                    .setPageManager(fromGui.getPageManager())
                    .setLastGui(fromGui.getLastGui())
                    .setStringItem(fromGui.getItem())
                    .setGuiName(fromGui.getName())
                    .open(player);
        });
    }
}