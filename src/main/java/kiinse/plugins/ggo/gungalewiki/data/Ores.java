package kiinse.plugins.ggo.gungalewiki.data;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.OresData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.File;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ores extends YamlFile implements OresData {

    private final Map<String, String> oresAndDrops = new HashMap<>();
    private final Map<String, String> oresAndAmount = new HashMap<>();

    public Ores(@NotNull GunGaleJavaPlugin plugin) {
        super(plugin, File.ORES_YML);

        for (var obj : getStringList(Config.ORES)) {
            var arr = obj.split(":");
            oresAndDrops.put(arr[0], arr[1]);
            oresAndAmount.put(arr[0], arr[2]);
        }
    }

    @Override
    public boolean hasOre(@NotNull String ore) {
        return oresAndDrops.containsKey(ore);
    }

    @Override
    public boolean hasDrop(@NotNull String drop) {
        return oresAndDrops.containsValue(drop);
    }

    @Override
    public @NotNull List<String> getOresByDrop(@NotNull String drop) {
        var result = new ArrayList<String>();
        for (var set : oresAndDrops.entrySet()) {
            if (set.getValue().equals(drop)) result.add(set.getKey());
        }
        return result;
    }

    @Override
    public @NotNull String getDropByOre(@NotNull String ore) {
        return oresAndDrops.get(ore);
    }

    @Override
    public @NotNull String getAmountByOre(@NotNull String ore) {
        return oresAndAmount.get(ore);
    }
}
