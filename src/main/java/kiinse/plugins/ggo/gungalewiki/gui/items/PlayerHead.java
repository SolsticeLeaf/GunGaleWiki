package kiinse.plugins.ggo.gungalewiki.gui.items;

import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiAction;
import kiinse.plugins.ggo.darkwaterapi.api.gui.GuiItem;
import kiinse.plugins.ggo.darkwaterapi.core.utilities.DarkPlayerUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerHead implements GuiItem {

    private final Player usedPlayer;

    public PlayerHead(@NotNull Player player) {
        this.usedPlayer = player;
    }

    @Override
    public int slot() {
        return 4;
    }

    @Override
    public @NotNull ItemStack itemStack() {
        var ggw = GunGaleWiki.getInstance();
        var head = ggw.getItemStackUtils().getPlayerHead(usedPlayer);
        head.setCustomModelData(ggw.getConfiguration().getInt(Config.PLAYER_HEAD_CMD));
        return head;
    }

    @Override
    public @NotNull String name() {
        return DarkPlayerUtils.getPlayerName(usedPlayer);
    }

    @Override
    public @NotNull GuiAction action() {
        return player -> {};
    }
}
