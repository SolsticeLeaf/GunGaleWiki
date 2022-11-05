package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
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
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomItem implements GuiItem {

    private final GunGaleWiki gunGaleWiki = GunGaleWiki.getInstance();
    private final String item;
    private final ItemStack itemStack;
    private final UserData userData;
    private final CreatedGui fromGui;
    private final int pos;
    private List<Component> lore = new ArrayList<>();

    public CustomItem(@NotNull String item, int pos, @NotNull UserData userData, @NotNull CreatedGui fromGui) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.item = item;
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
    }

    public CustomItem(@NotNull String item, int pos, @NotNull UserData userData, @NotNull CreatedGui fromGui, @NotNull List<Component> beforeLore) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.item = item;
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
        this.lore = beforeLore;
    }

    public CustomItem(@NotNull String item, int pos, int amount, @NotNull UserData userData, @NotNull CreatedGui fromGui) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.itemStack.setAmount(amount);
        this.item = item;
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
    }

    public CustomItem(@NotNull CustomStack customStack, int pos, @NotNull UserData userData, @NotNull CreatedGui fromGui) {
        this.itemStack = customStack.getItemStack();
        this.item = customStack.getNamespacedID();
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
    }

    public void setAmount(int amount) {
        this.itemStack.setAmount(amount);
    }

    @Override
    public int slot() {
        return pos;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        var meta = itemStack.getItemMeta();
        if (meta != null) {
            var locale = gunGaleWiki.getGunGaleAPI().getPlayerLocales().getLocale(userData.getPlayer());
            lore.addAll(gunGaleWiki.getMessages().getComponentList(locale, item));
            var addLore = this.lore;
            addLore.addAll(gunGaleWiki.getMessages().getComponentList(locale, Message.DESCRIPTION));
            meta.lore(addLore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    @Override
    public @NotNull GuiAction action() {
        return (clickType, player) -> {
            var config = gunGaleWiki.getConfiguration();
            if (clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) {
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
                        .setGui(fromGui.getType())
                        .setPageManager(fromGui.getPageManager())
                        .setLastGui(fromGui.getLastGui())
                        .setStringItem(fromGui.getItem())
                        .setGuiName(fromGui.getName())
                        .open(player);
                return;
            }
            if (clickType == ClickType.LEFT) {
                var parsedItem = item;
                if (item.contains(":")) {
                    var split = item.split(":");
                    if (split[0].equalsIgnoreCase("boss_item")) {
                        return;
                    }
                    parsedItem = split[1];
                }

                var oresData = gunGaleWiki.getOresData();
                if (oresData.hasOre(parsedItem)) return;
                if (oresData.hasDrop(parsedItem)) {
                    var pages = new PageManager().setOreItems(oresData.getOresByDrop(parsedItem));
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
                    var totem = GunGaleMythicMobs.getInstance().getTotems().getSchematicByTotemName(totemsData.getTotem(parsedItem));
                    if (totem != null) TotemUtils.setTotemProjection(gunGaleWiki, totem, player, totemsData.getDelay());
                    fromGui.delete();
                    player.closeInventory();
                    return;
                }


                var pages = new PageManager().setRecipes(GuiUtils.getRecipes(item));
                if (pages.hasPage(0)) {
                    fromGui.delete();
                    var isFurnace = pages.getPageRecipe(0) instanceof FurnaceRecipe;
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
            if (clickType == ClickType.RIGHT) {
                fromGui.delete();
                new GuiBuilder(player)
                        .setPage(0)
                        .setItem(item)
                        .setGui(Gui.FROMITEM)
                        .setLastGui(fromGui)
                        .setPageManager(new PageManager().setStackItems(GuiUtils.getItemsFromThis(item)))
                        .setStringItem(item)
                        .setName(config.getString(Config.MENU_FROMITEM_NAME))
                        .open(player);
            }
        };
    }
}
