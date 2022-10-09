package kiinse.plugins.ggo.gungalewiki.files.buttons;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.ggo.gungalewiki.enums.Button;
import kiinse.plugins.ggo.gungalewiki.enums.Directory;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class ButtonsData extends FilesManager implements FiltersButtons {

    private final HashMap<Button, FiltersButton> buttons = new HashMap<>();

    public ButtonsData(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public FiltersButtons load() throws IOException, InvalidConfigurationException {
        if (isFileNotExists(Directory.BUTTONS) || isDirectoryEmpty(Directory.BUTTONS))
            copyFile(Directory.BUTTONS);
        for (var file : listFilesInDirectory(Directory.BUTTONS)) {
            var yaml = new YamlConfiguration();
            yaml.load(file);
            buttons.put(Button.valueOf(file.getName().split("\\.")[0].toUpperCase()), new FiltersButtonImpl(yaml));
        }
        return this;
    }

    @Override
    public @NotNull FiltersButton getButton(@NotNull Button button) {
        return buttons.get(button);
    }
}
