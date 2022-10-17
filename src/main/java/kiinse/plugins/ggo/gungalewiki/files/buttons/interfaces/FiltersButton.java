package kiinse.plugins.ggo.gungalewiki.files.buttons.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface FiltersButton {

    @NotNull ItemStack getItemStack();

    @NotNull Component getName();

    @NotNull String getMenuName();

    @NotNull Material getMaterial();

    int getCMD();

    @NotNull List<String> getItems();

    @NotNull List<Component> getLore();
}
