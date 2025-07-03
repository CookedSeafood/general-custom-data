package net.cookedseafood.generalcustomdata.api;

public interface EntityApi {
    default String getCustomId() {
        return null;
    }

    default void setCustomId(String id) {
    }

    default String getCustomIdOrId() {
        return null;
    }
}
