package kiinse.plugins.ggo.gungalewiki.listeners;

import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class OnQuitListener implements Listener {

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        GunGaleWiki.getInstance().getPluginData().save(event.getPlayer());
    }
}
