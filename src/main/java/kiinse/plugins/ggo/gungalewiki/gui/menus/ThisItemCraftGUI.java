package kiinse.plugins.ggo.gungalewiki.gui.menus;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ThisItemCraftGUI extends DarkGUI {

    private final GunGaleWiki gunGaleWiki;
    private final Player openedPlayer;
    private final ItemStack item;

    public ThisItemCraftGUI(@NotNull GunGaleWiki gunGaleWiki, @NotNull Player player, @NotNull ItemStack item) {
        super(gunGaleWiki);
        this.gunGaleWiki = gunGaleWiki;
        this.openedPlayer = player;
        this.item = item;
    }

    @Override
    protected void inventory(@NotNull GunGaleJavaPlugin darkWaterJavaPlugin) {
        //TODO: Нахимичить проверку на вид рецепта, ибо может быть рецепт в печке
    }
}
