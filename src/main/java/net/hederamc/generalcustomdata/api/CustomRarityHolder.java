package net.hederamc.generalcustomdata.api;

public interface CustomRarityHolder {
    default String getCustomRarity() {
        throw new UnsupportedOperationException();
    }

    default void setCustomRarity(String rarity) {
        throw new UnsupportedOperationException();
    }

    default String getCustomRarityOrRarity() {
        throw new UnsupportedOperationException();
    }
}
