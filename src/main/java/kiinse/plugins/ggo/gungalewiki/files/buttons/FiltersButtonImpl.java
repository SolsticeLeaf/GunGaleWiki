package kiinse.plugins.ggo.gungalewiki.files.buttons;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.files.locale.PlayerLocale;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkItemUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Button;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces.FiltersButton;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FiltersButtonImpl implements FiltersButton {

    private final String menuName;
    private final int cmd;
    private final Material material;
    private final List<String> items;
    private String name;
    private List<Component> lore;

    public FiltersButtonImpl(@NotNull YamlConfiguration yamlConfiguration) {
        this.menuName = yamlConfiguration.getString("menu.name");
        this.items = yamlConfiguration.getStringList("button.items");
        this.cmd = yamlConfiguration.getInt("button.cmd");
        this.material = Material.valueOf(yamlConfiguration.getString("button.material"));
    }

    public @NotNull FiltersButton get(@NotNull GunGaleJavaPlugin plugin, @NotNull Button button, @NotNull PlayerLocale playerLocale) {
        var messages = plugin.getMessages();
        this.name = messages.getStringMessage(playerLocale, Message.valueOf("BUTTON_FILTER_" + button + "_NAME"));
        this.lore = messages.getComponentList(playerLocale, Message.valueOf("BUTTON_FILTER_" + button + "_LORE"));
        return this;
    }

    @Override
    public @NotNull ItemStack getItemStack() {
        return new DarkItemUtils(GunGaleWiki.getInstance())
                .getItemStack(getMaterial(),
                              getName(),
                              getLore(),
                              1,
                              itemMeta -> {
                                  if (getCMD() != 0) itemMeta.setCustomModelData(getCMD());
                              });
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull String getMenuName() {
        return menuName;
    }

    @Override
    public @NotNull Material getMaterial() {
        return material;
    }

    @Override
    public int getCMD() {
        return cmd;
    }

    @Override
    public @NotNull List<String> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public @NotNull List<Component> getLore() {
        return Collections.unmodifiableList(lore);
    }
}
