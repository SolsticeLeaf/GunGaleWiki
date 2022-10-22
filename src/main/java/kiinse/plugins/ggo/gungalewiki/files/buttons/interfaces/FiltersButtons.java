package kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces;

import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungalewiki.files.buttons.Button;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface FiltersButtons {

    FiltersButtons load() throws IOException, InvalidConfigurationException;

    @NotNull FiltersButton getButton(@NotNull Button button, @NotNull PlayerLocale playerLocale);
}
