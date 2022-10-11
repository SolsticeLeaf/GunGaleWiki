package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FromThisItemCraftGUI extends DarkGUI {

    private final GunGaleWiki gunGaleWiki;
    private final Player openedPlayer;
    public final ItemStack fromItem;

    public FromThisItemCraftGUI(@NotNull GunGaleWiki gunGaleWiki, @NotNull Player player, @NotNull ItemStack fromItem) {
        super(gunGaleWiki);
        this.gunGaleWiki = gunGaleWiki;
        this.openedPlayer = player;
        this.fromItem = fromItem;
    }

    @Override
    protected void inventory(@NotNull GunGaleJavaPlugin darkWaterJavaPlugin) {

    }
}
