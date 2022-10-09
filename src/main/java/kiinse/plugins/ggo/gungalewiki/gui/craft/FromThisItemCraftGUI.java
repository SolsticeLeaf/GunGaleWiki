package kiinse.plugins.ggo.gungalewiki.gui.craft;

import kiinse.plugins.ggo.darkwaterapi.api.DarkWaterJavaPlugin;
import kiinse.plugins.ggo.darkwaterapi.core.gui.DarkGUI;
import kiinse.plugins.ggo.gungalewiki.gui.GUIData;
import org.jetbrains.annotations.NotNull;

public class FromThisItemCraftGUI extends DarkGUI {

    private final GUIData guiData;

    public FromThisItemCraftGUI(@NotNull GUIData guiData) {
        super(guiData.getGunGaleWiki());
        this.guiData = guiData;
    }

    @Override
    protected void inventory(@NotNull DarkWaterJavaPlugin darkWaterJavaPlugin) {

    }
}
