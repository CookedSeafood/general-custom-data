package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.hederamc.fishbonetrehalose.api.RarityHolder;
import net.hederamc.fishbonetrehalose.util.Rarities;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.item.Rarity;
import org.jspecify.annotations.Nullable;

public interface CustomRarityHolder extends RarityHolder, CustomDataHolder {
    @Nullable
    default String getCustomRarity() {
        return this.getCustomDataOrEmpty().getTag().getStringOr("rarity", null);
    }

    default void setCustomRarity(String rarity) {
        this.getOrCreateCustomData().getTag().putString("rarity", rarity);
    }

    @Nullable
    default StringTag removeCustomRarity() {
        return (StringTag)this.getCustomDataOrEmpty().getTag().remove("rarity");
    }

    default String getCustomRarityOrRarity() {
        String customRarity = this.getCustomRarity();

        if (customRarity == null) {
            return this.getRarity().getSerializedName();
        }

        return customRarity;
    }

    default void setRarityOrCustomRarity(String rarity) {
        Rarity vanillaRarity = Rarities.byName(rarity);

        if (vanillaRarity == null) {
            this.setCustomRarity(rarity);
            return;
        }

        this.setRarity(vanillaRarity);
        this.removeCustomRarity();
    }
}
