package kiinse.plugins.ggo.gungalewiki.gui;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiUtils {

    private GuiUtils() {}

    public static @NotNull CreatedGui getMainGui(@NotNull Player player) {
        return new GuiBuilder(player)
                .setPage(1)
                .getGui(Gui.HOME)
                .setGuiName(GunGaleWiki.getInstance().getConfiguration().getString(Config.MENU_MAIN_NAME));
    }
}
