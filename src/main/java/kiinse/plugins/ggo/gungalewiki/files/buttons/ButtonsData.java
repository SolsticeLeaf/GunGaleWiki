package kiinse.plugins.ggo.gungalewiki.files.buttons;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.FilesManager;
import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungalewiki.enums.Directory;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButtons;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.logging.Level;

public class ButtonsData extends FilesManager implements FiltersButtons {

    private final HashMap<Button, FiltersButtonImpl> buttons = new HashMap<>();
    private final GunGaleJavaPlugin plugin;

    public ButtonsData(@NotNull GunGaleJavaPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public FiltersButtons load() {
        if (isFileNotExists(Directory.BUTTONS) || isDirectoryEmpty(Directory.BUTTONS)) copyFile(Directory.BUTTONS);
        listFilesInDirectory(Directory.BUTTONS).forEach(file -> {
            var fileName = file.getName().split("\\.")[0].toUpperCase();
            try {
                var yaml = new YamlConfiguration();
                yaml.load(file);
                buttons.put(Button.valueOf(fileName), new FiltersButtonImpl(yaml));
            } catch (Exception e) {
                plugin.sendLog(Level.WARNING, "Error on loading filter button '" + fileName + "'! Message: " + e.getMessage());
            }
        });
        return this;
    }

    @Override
    public @NotNull FiltersButton getButton(@NotNull Button button, @NotNull PlayerLocale playerLocale) {
        return buttons.get(button).get(plugin, button, playerLocale);
    }
}
