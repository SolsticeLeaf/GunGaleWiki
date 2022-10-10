package kiinse.plugins.ggo.gungalewiki.files.buttons;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.api.files.filemanager.FilesManager;
import kiinse.plugins.ggo.darkwaterapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungalewiki.enums.Button;
import kiinse.plugins.ggo.gungalewiki.enums.Directory;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ButtonsData extends FilesManager implements FiltersButtons {

    private final HashMap<Button, YamlConfiguration> buttons = new HashMap<>();
    private final DarkWaterJavaPlugin plugin;

    public ButtonsData(@NotNull DarkWaterJavaPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public FiltersButtons load() throws IOException, InvalidConfigurationException {
        if (isFileNotExists(Directory.BUTTONS) || isDirectoryEmpty(Directory.BUTTONS))
            copyFile(Directory.BUTTONS);
        for (var file : listFilesInDirectory(Directory.BUTTONS)) {
            var yaml = new YamlConfiguration();
            yaml.load(file);
            var fileName = file.getName().split(File.separator);
            buttons.put(Button.valueOf(fileName[fileName.length-1].split("\\.")[0].toUpperCase()), yaml);
        }
        return this;
    }

    @Override
    public @NotNull FiltersButton getButton(@NotNull Button button, @NotNull PlayerLocale playerLocale) {
        return new FiltersButtonImpl(buttons.get(button), plugin, playerLocale, button);
    }
}
