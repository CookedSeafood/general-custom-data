package net.hederamc.generalcustomdata.api;

import net.minecraft.world.entity.Entity;

public interface CustomOwnerHolder {
    default Entity getCustomOwner() {
        throw new UnsupportedOperationException();
    }

    default void setCustomOwner(Entity owner) {
        throw new UnsupportedOperationException();
    }
}
