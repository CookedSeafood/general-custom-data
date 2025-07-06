package net.cookedseafood.generalcustomdata.api;

import net.minecraft.entity.Entity;

public interface EntityApi {
    default String getCustomId() {
        return null;
    }

    default void setCustomId(String id) {
    }

    default String getCustomIdOrId() {
        return null;
    }

    default Entity getCustomOwner() {
        return null;
    }

    default void setCustomOwner(Entity owner) {
    }

    default byte getCustomExplosionRadius() {
        return 0;
    }

    default void setCustomExplosionRadius(byte radius) {
    }
}
