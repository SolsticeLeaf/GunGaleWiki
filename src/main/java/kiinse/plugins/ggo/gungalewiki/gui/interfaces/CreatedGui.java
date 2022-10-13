package kiinse.plugins.ggo.gungalewiki.gui.interfaces;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.gui.GuiItem;
import kiinse.plugins.ggo.gungaleapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.managers.pagemanager.PageManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CreatedGui extends DarkGUI {

    private final GunGaleWiki gunGaleWiki;
    private Gui gui;
    private CreatedGui lastGui;
    private PageManager pageManager;
    private Player player;
    private int page = 1;
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
    public PageManager getPageManager() {
        return pageManager;
    }

    public @NotNull CreatedGui setPageManager(@NotNull PageManager pageManager) {
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

    public @NotNull CreatedGui setCreateGuiName(@NotNull String name) {
        super.setName(name);
        return this;
    }

    protected @NotNull Player getPlayer() {
        return player;
    }

    public @NotNull CreatedGui setPlayer(@NotNull Player player) {
        this.player = player;
        return this;
    }

    public @NotNull CreatedGui setType(@NotNull Gui gui) {
        this.gui = gui;
        return this;
    }

    public @NotNull Gui getType() {
        return gui;
    }

    protected @NotNull GunGaleWiki getGunGaleWiki() {
        return gunGaleWiki;
    }

    public @NotNull CreatedGui setLastGui(@NotNull CreatedGui lastGui) {
        this.lastGui = lastGui;
        return this;
    }

    @NotNull
    public CreatedGui getLastGui() {
        return lastGui;
    }

    public void setCreatedItem(@NotNull GuiItem guiItem) {
        super.setItem(guiItem);
    }

    public @NotNull CreatedGui setGuiName(@NotNull String name) {
        super.setName(name);
        return this;
    }

    @Override
    protected void inventory(@NotNull GunGaleJavaPlugin darkWaterJavaPlugin) {
        onOpenInventory(gunGaleWiki);
    }

    public @NotNull String getName() {
        return super.getName();
    }

    public abstract void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki);
}
