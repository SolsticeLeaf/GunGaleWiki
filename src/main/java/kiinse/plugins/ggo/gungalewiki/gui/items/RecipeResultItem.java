package kiinse.plugins.ggo.gungalewiki.gui.items;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiAction;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.UserData;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.utils.ItemsUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecipeResultItem implements GuiItem {

    private final GunGaleWiki gunGaleWiki = GunGaleWiki.getInstance();
    private final String item;
    private final ItemStack itemStack;
    private final UserData userData;
    private final CreatedGui fromGui;
    private final int pos;
    private List<Component> lore = new ArrayList<>();

    public RecipeResultItem(@NotNull String item, int pos, @NotNull UserData userData, @NotNull CreatedGui fromGui) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.item = item;
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
    }

    public RecipeResultItem(@NotNull String item, int pos, @NotNull UserData userData, @NotNull CreatedGui fromGui, @NotNull List<Component> beforeLore) {
        this.itemStack = CustomStack.getInstance(item).getItemStack();
        this.item = item;
        this.pos = pos;
        this.userData = userData;
        this.fromGui = fromGui;
        this.lore = beforeLore;
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
            addLore.addAll(gunGaleWiki.getMessages().getComponentList(locale, "description_" + fromGui.getType().toString().toLowerCase()));
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
                ItemsUtils.addToBookmarks(player, config, gunGaleWiki, fromGui, userData, item);
                return;
            }
            if (clickType == ClickType.RIGHT || clickType == ClickType.LEFT) {
                ItemsUtils.rightMouse(player, config, gunGaleWiki, fromGui, item);
            }
        };
    }
}
