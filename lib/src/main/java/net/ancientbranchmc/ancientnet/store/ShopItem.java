package net.ancientbranchmc.ancientnet.store;

import net.ancientbranchmc.ancientnet.api.DatabaseColumn;
import net.ancientbranchmc.ancientnet.api.DatabaseItem;
import net.ancientbranchmc.ancientnet.api.FromTable;

@FromTable(database = "mtree", table = "store_items", primaryKey = "item_id")
public class ShopItem extends DatabaseItem {
    @DatabaseColumn(name = "item_id", type = int.class)
    private int itemId;

    @DatabaseColumn(name = "item_category_id", type = int.class)
    private int categoryId;

    @DatabaseColumn(name = "display_name", type = String.class)
    private String displayName;

    @DatabaseColumn(name = "short_description", type = String.class)
    private String shortDesc;

    @DatabaseColumn(name = "long_description", type = String.class)
    private String longDesc;

    @DatabaseColumn(name = "price_per_unit", type = double.class)
    private double pricePerUnit;

    @DatabaseColumn(name = "show_on_store", type = boolean.class)
    private double showOnStore;

    @DatabaseColumn(name = "allow_purchase", type = boolean.class)
    private double allowPurchase;

    @DatabaseColumn(name = "require_permission", type = String.class)
    private String permission;

    @DatabaseColumn(name = "show_in_game", type = boolean.class)
    private boolean showInGame;

    @DatabaseColumn(name = "rarity", type = String.class)
    private String rarity;

    @DatabaseColumn(name = "allow_igc", type = boolean.class)
    private boolean allowInGameCurrency;

    @DatabaseColumn(name = "igc_sale_effected", type = boolean.class)
    private boolean saleAffectsInGamePrice;

    @DatabaseColumn(name = "igc_sale_multiplier", type = double.class)
    private double saleMultiplierForInGameCurrency;

    @DatabaseColumn(name = "in_game_icon", type = String.class)
    private String inGameIcon;

    @DatabaseColumn(name = "in_game_icon_nbt", type = String.class)
    private String inGameIconNbt;

    @Override
    protected String getKeyValue() {
        return Integer.toString(itemId);
    }
}
