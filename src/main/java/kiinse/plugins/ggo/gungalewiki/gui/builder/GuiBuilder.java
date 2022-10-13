package kiinse.plugins.ggo.gungalewiki.gui.builder;

import kiinse.plugins.ggo.gungalewiki.enums.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.menus.MainGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GuiBuilder {

    private final Player player;
    private int page = 1;

    private String item;

    public GuiBuilder(@NotNull Player player) {
        this.player = player;
    }

    public @Nullable String getItem() {
        return item;
    }

    public @NotNull GuiBuilder setItem(@Nullable String item) {
        this.item = item;
        return this;
    }

    public @NotNull CreatedGui getGui(@NotNull Gui gui) {
        try {
            var result = gui.get().getDeclaredConstructor().newInstance();
            if (getItem() != null) result.setStringItem(result.getItem());
            result.setPlayer(player).setPage(getPage()).setType(gui);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new MainGUI()
                    .setPlayer(getPlayer())
                    .setPage(getPage());
        }
    }

    public int getPage() {
        return page;
    }

    public @NotNull GuiBuilder setPage(int page) {
        this.page = page;
        return this;
    }

    public @NotNull Player getPlayer() {
        return player;
    }

}
