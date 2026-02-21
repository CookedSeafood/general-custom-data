package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.StringTag;
import org.jspecify.annotations.Nullable;

public interface CustomRarityHolder extends CustomDataHolder {
    @Nullable
    default String getCustomRarity() {
        return this.getCustomDataOrEmpty().tag().getStringOr("rarity", null);
    }

    default void setCustomRarity(String rarity) {
        this.getOrCreateCustomData().tag().putString("rarity", rarity);
    }

    default StringTag removeCustomRarity() {
        return (StringTag)this.getCustomDataOrEmpty().tag().remove("rarity");
    }

    default String getCustomRarityOrRarity() {
        throw new UnsupportedOperationException();
    }
}
