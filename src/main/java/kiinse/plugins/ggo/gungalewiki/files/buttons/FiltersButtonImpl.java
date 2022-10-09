package kiinse.plugins.ggo.gungalewiki.files.buttons;

import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FiltersButtonImpl implements FiltersButton {

    private final String name;
    private final List<String> items;

    public FiltersButtonImpl(@NotNull YamlConfiguration yamlConfiguration) {
        this.name = yamlConfiguration.getString("button.name");
        this.items = yamlConfiguration.getStringList("button.items");
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull List<String> getItems() {
        return Collections.unmodifiableList(items);
    }
}
