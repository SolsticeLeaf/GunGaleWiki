package kiinse.plugins.ggo.gungalewiki.pagemanager.interfaces;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface WikiPageManager {

    @NotNull WikiPageManager setStackItems(@NotNull List<ItemStack> items);

    @NotNull WikiPageManager setOreItems(@NotNull List<String> items);

    @NotNull WikiPageManager setItems(@NotNull List<String> items);

    @NotNull WikiPageManager setRecipes(@NotNull List<Recipe> recipes);

    boolean hasPage(int page);

    @Nullable Recipe getPageRecipe(int page);

    @NotNull String getItem(int page);

    @NotNull List<ItemStack> getPageItemStackList(int page);

    @NotNull List<String> getPageList(int page);
}
