package kiinse.plugins.ggo.gungalewiki.data;

import kiinse.plugins.ggo.gungaleapi.api.GunGaleJavaPlugin;
import kiinse.plugins.ggo.gungaleapi.api.files.filemanager.YamlFile;
import kiinse.plugins.ggo.gungalewiki.data.interfaces.TotemsData;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.File;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Totems extends YamlFile implements TotemsData {

    private final HashMap<String, String> data = new HashMap<>();
    private final int delay;

    public Totems(@NotNull GunGaleJavaPlugin plugin) {
        super(plugin, File.TOTEMS_YML);
        for (var obj : getStringList(Config.TOTEMS_LIST)) {
            var arr = obj.split(":");
            data.put(arr[0], arr[1]);
        }
        delay = getInt(Config.TOTEMS_DELAY);
    }

    @Override
    public boolean hasItem(@NotNull String item) {
        return data.containsKey(item);
    }

    @Override
    public @NotNull String getTotem(@NotNull String item) {
        return data.get(item);
    }

    @Override
    public int getDelay() {
        return delay;
    }
}
