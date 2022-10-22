package kiinse.plugins.ggo.gungalewiki.gui.builder.interfaces;

import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface WikiGuiBuilder {

    @Nullable String getItem();

    @NotNull GuiBuilder setItem(@Nullable String item);

    @NotNull CreatedGui getGui(@NotNull Gui gui);

    int getPage();

    @NotNull GuiBuilder setPage(int page);

    @NotNull Player getPlayer();

}
