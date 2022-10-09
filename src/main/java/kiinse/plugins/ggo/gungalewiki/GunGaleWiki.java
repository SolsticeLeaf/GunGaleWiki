package kiinse.plugins.ggo.gungalewiki;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.files.messages.MessagesUtils;
import kiinse.plugins.ggo.darkwaterapi.api.utilities.ItemStackUtils;
import kiinse.plugins.ggo.darkwaterapi.core.files.messages.DarkMessagesUtils;
import kiinse.plugins.ggo.darkwaterapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.files.buttons.ButtonsData;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import org.jetbrains.annotations.NotNull;

public final class GunGaleWiki extends DarkWaterJavaPlugin {

    private static GunGaleWiki instance;
    private FiltersButtons buttons;
    private ItemStackUtils itemStackUtils;
    private MessagesUtils messagesUtils;

    @Override
    public void onStart() throws Exception {
        instance = this;
        buttons = new ButtonsData(this).load();
        itemStackUtils = new DarkItemUtils(this);
        messagesUtils = new DarkMessagesUtils(this);
    }

    @Override
    public void onStop() throws Exception {

    }

    public @NotNull FiltersButtons getFilterButtons() {
        return buttons;
    }

    public @NotNull ItemStackUtils getItemStackUtils() {
        return itemStackUtils;
    }

    public @NotNull MessagesUtils messagesUtils() {
        return messagesUtils;
    }

    public static @NotNull GunGaleWiki getInstance() {
        return instance;
    }
}
