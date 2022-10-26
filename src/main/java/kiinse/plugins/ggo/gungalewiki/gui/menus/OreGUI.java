package kiinse.plugins.ggo.gungalewiki.gui.menus;

import dev.lone.itemsadder.api.CustomStack;
import kiinse.plugins.ggo.gungaleapi.core.utilities.DarkUtils;
import kiinse.plugins.ggo.gungalewiki.GunGaleWiki;
import kiinse.plugins.ggo.gungalewiki.enums.Config;
import kiinse.plugins.ggo.gungalewiki.enums.Message;
import kiinse.plugins.ggo.gungalewiki.enums.Replace;
import kiinse.plugins.ggo.gungalewiki.gui.builder.Gui;
import kiinse.plugins.ggo.gungalewiki.gui.builder.GuiBuilder;
import kiinse.plugins.ggo.gungalewiki.gui.interfaces.CreatedGui;
import kiinse.plugins.ggo.gungalewiki.gui.items.*;
import kiinse.plugins.ggo.gungalewiki.pagemanager.PageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OreGUI extends CreatedGui {

    public OreGUI() {
        super(GunGaleWiki.getInstance());
    }

    @Override
    public void onOpenInventory(@NotNull GunGaleWiki gunGaleWiki) {
        assert getItem() != null;
        var oresData = gunGaleWiki.getOresData();
        var config = gunGaleWiki.getConfiguration();
        if (getPageManager() == null) {
            setPageManager(new PageManager().setOreItems(oresData.getOresByDrop(getItem())));
        }

        var pluginData = gunGaleWiki.getPluginData();
        var userData = pluginData.getUserData(getPlayer());
        userData.addToLastSeen(getItem());
        pluginData.saveData(userData);

        var ore = CustomStack.getInstance(getPageManager().getItem(getPage()));
        var text = DarkUtils.replaceWord(gunGaleWiki.getMessages().getStringMessage(getPlayerLocale(), Message.ORE_DROP),
                                         Replace.AMOUNT,
                                         oresData.getAmountByOre(getPageManager().getItem(getPage())));

        var beforeLore = new ArrayList<Component>();
        beforeLore.add(MiniMessage.miniMessage().deserialize(text));
        setCreatedItem(new CustomItem(getItem(), 24, userData, this, beforeLore));


        if (ore != null) {
            setCreatedItem(new CustomItem(ore, 20, userData, this));
        }

        setCreatedItem(new NextPageButton(getPageManager().hasPage(getPage() + 1), getPlayerLocale(), gunGaleWiki, 52, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() + 1)
                    .setGui(Gui.ORES)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setStringItem(getItem())
                    .setName(config.getString(Config.MENU_ORES_NAME))
                    .open(player);
        })));

        setCreatedItem(new AddToBookMarkButton(51, getPlayerLocale(), gunGaleWiki, this, getPlayer(), getItem()));

        setCreatedItem(new PrevPageButton(getPageManager().hasPage(getPage() - 1), getPlayerLocale(), gunGaleWiki, 50, ((clickType, player) -> {
            delete();
            new GuiBuilder(player)
                    .setPage(getPage() - 1)
                    .setGui(Gui.ORES)
                    .setLastGui(getLastGui())
                    .setPageManager(getPageManager())
                    .setStringItem(getItem())
                    .setName(config.getString(Config.MENU_ORES_NAME))
                    .open(player);
        })));

        setCreatedItem(new HomeButton(getPlayerLocale(), gunGaleWiki, 47, this));
        for (var i : new int[]{45, 46}) {
            setCreatedItem(new BackButton(getPlayerLocale(), gunGaleWiki, i, this));
        }
    }
}
