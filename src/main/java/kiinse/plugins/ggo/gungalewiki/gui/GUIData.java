package kiinse.plugins.ggo.gungalewiki.gui;

import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GUIData {

    private final Player player;
    private final GunGaleWiki gunGaleWiki;
    private final DarkGUI prevGui;

    public GUIData(@NotNull Player player, @NotNull GunGaleWiki gunGaleWiki, @NotNull DarkGUI prevGui) {
        this.player = player;
        this.gunGaleWiki = gunGaleWiki;
        this.prevGui = prevGui;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull GunGaleWiki getGunGaleWiki() {
        return gunGaleWiki;
    }

    public @NotNull DarkGUI getPrevGui() {
        return prevGui;
    }
}
