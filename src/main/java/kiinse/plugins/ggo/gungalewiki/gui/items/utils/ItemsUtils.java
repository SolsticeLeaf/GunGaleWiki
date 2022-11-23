package kiinse.plugins.ggo.gungalewiki.gui.items.utils;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.gungaleapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalemythicmobs.GunGaleMythicMobs;
import kiinse.plugins.ggo.gungalemythicmobs.utils.TotemUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.enums.Replace;
import kiinse.plugins.ggo.gungalewiki.gui.GuiUtils;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ItemsUtils {

    private ItemsUtils() {}

    public static void addToBookmarks(@NotNull Player player, @NotNull YamlFile config, @NotNull GunGaleWiki gunGaleWiki, @NotNull CreatedGui fromGui,
                                      @NotNull UserData userData, @NotNull String item) {
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
        if (fromGui.getPageManager() != null) {
            fromGui.delete();
            new GuiBuilder(player)
                    .setPage(fromGui.getPage())
                    .setItem(fromGui.getItem())
                    .setGui(fromGui.getType())
                    .setPageManager(fromGui.getPageManager())
                    .setLastGui(fromGui.getLastGui())
                    .setStringItem(fromGui.getItem())
                    .setGuiName(fromGui.getName())
                    .open(player);
        }
    }

    public static void rightMouse(@NotNull Player player, @NotNull YamlFile config, @NotNull GunGaleWiki gunGaleWiki, @NotNull CreatedGui fromGui, @NotNull String item) {
        var list = GuiUtils.getItemsFromThis(item);
        if (!list.isEmpty()) {
            if (list.size() == 1) {
                leftMouse(player, config, gunGaleWiki, fromGui, CustomStack.byItemStack(list.get(0)).getId());
            } else {
                fromGui.delete();
                new GuiBuilder(player)
                        .setPage(0)
                        .setItem(item)
                        .setGui(Gui.FROMITEM)
                        .setLastGui(fromGui)
                        .setPageManager(new PageManager().setItems(list, 36))
                        .setStringItem(item)
                        .setName(config.getString(Config.MENU_FROMITEM_NAME))
                        .open(player);
            }
        }
    }

    public static void leftMouse(@NotNull Player player, @NotNull YamlFile config, @NotNull GunGaleWiki gunGaleWiki, @NotNull CreatedGui fromGui, @NotNull String item) {
        var parsedItem = item;
        if (item.contains(":")) {
            var split = item.split(":");
            if (split[0].equalsIgnoreCase("boss_item")) return;
            parsedItem = split[1];
        }

        var oresData = gunGaleWiki.getOresData();
        if (oresData.hasOre(parsedItem)) return;
        if (oresData.hasDrop(parsedItem)) {
            var pages = new PageManager().setItems(oresData.getOresByDrop(parsedItem), 1);
            if (pages.hasPage(0)) {
                fromGui.delete();
                new GuiBuilder(player)
                        .setItem(parsedItem)
                        .setPage(0)
                        .setGui(Gui.ORES)
                        .setLastGui(fromGui)
                        .setPageManager(pages)
                        .setStringItem(parsedItem)
                        .setName(config.getString(Config.MENU_ORES_NAME))
                        .open(player);
            }
            return;
        }

        var totemsData = gunGaleWiki.getTotemsData();
        if (totemsData.hasItem(parsedItem)) {
            var totem = GunGaleMythicMobs.Companion.getInstance().getTotems().getSchematic(totemsData.getTotem(parsedItem));
            if (totem != null) TotemUtils.INSTANCE.setTotemProjection(gunGaleWiki, totem, player, totemsData.getDelay());
            fromGui.delete();
            player.closeInventory();
            return;
        }

        var pages = new PageManager().setItems(GuiUtils.getRecipes(item), 1);
        if (pages.hasPage(0)) {
            fromGui.delete();
            var isFurnace = pages.get(0, new ArrayList<Recipe>()).get(0)  instanceof FurnaceRecipe;
            new GuiBuilder(player)
                    .setPage(0)
                    .setGui(isFurnace ? Gui.FURNACE : Gui.WORKBENCH)
                    .setLastGui(fromGui)
                    .setPageManager(pages)
                    .setStringItem(item)
                    .setName(config.getString(isFurnace ? Config.MENU_FURNACE_NAME : Config.MENU_WORKBENCH_NAME))
                    .open(player);
        }
    }
}
