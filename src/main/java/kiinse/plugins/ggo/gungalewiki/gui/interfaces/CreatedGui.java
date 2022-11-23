package kiinse.plugins.ggo.gungalewiki.gui.interfaces;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces.WikiPageManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CreatedGui extends DarkGUI {

    private final GunGaleWiki gunGaleWiki;
    private Gui gui;
    private CreatedGui lastGui;
    private WikiPageManager pageManager;
    private Player player;
    private int page = 0;
    private String item;

    protected CreatedGui(@NotNull GunGaleWiki gunGaleWiki) {
        super(gunGaleWiki);
        super.setSize(54);
        this.gunGaleWiki = gunGaleWiki;
    }

    public @NotNull CreatedGui setStringItem(@Nullable String item) {
        this.item = item;
        return this;
    }

    public @Nullable String getItem() {
        return item;
    }

    @Nullable
    public WikiPageManager getPageManager() {
        return pageManager;
    }

    public @NotNull CreatedGui setPageManager(@NotNull WikiPageManager pageManager) {
        this.pageManager = pageManager;
        return this;
    }

    public int getPage() {
        return page;
    }

    public @NotNull CreatedGui setPage(int page) {
        this.page = page;
        return this;
    }

    protected @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull CreatedGui setPlayer(@NotNull Player player) {
        this.player = player;
        return this;
    }

    public @NotNull Gui getType() {
        return gui;
    }

    public @NotNull CreatedGui setType(@NotNull Gui gui) {
        this.gui = gui;
        return this;
    }

    protected @NotNull GunGaleWiki getGunGaleWiki() {
        return gunGaleWiki;
    }

    @NotNull
    public CreatedGui getLastGui() {
        return lastGui;
    }

    public @NotNull CreatedGui setLastGui(@NotNull CreatedGui lastGui) {
        this.lastGui = lastGui;
        return this;
    }

    public void setCreatedItem(@NotNull GuiItem guiItem) {
        super.setItem(guiItem);
    }

    public @NotNull CreatedGui setGuiName(@NotNull String name) {
        super.setName(name);
        return this;
    }

    @Override
    protected void inventory(@NotNull GunGaleJavaPlugin gunGaleJavaPlugin) {
        var pluginData = gunGaleWiki.getPluginData();
        pluginData.saveData(pluginData.getUserData(player).saveLastGui(this));
        onOpenInventory(gunGaleWiki);
    }

    public @NotNull String getName() {
        return super.getName();
    }

    public abstract void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki);
}
