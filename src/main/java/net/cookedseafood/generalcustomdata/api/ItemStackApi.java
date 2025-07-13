package net.cookedseafood.generalcustomdata.api;

import net.minecraft.nbt.NbtList;

public interface ItemStackApi {
    default String getCustomId() {
        return null;
    }

    default void setCustomId(String id) {
    }

    default String getCustomIdOrId() {
        return null;
    }

    default String getCustomRarity() {
        return null;
    }

    default void setCustomRarity(String rarity) {
    }

    default String getCustomRarityOrRarity() {
        return null;
    }

    default NbtList getCustomModifiers() {
        return null;
    }

    default NbtList getCustomStatusEffects() {
        return null;
    }
}
