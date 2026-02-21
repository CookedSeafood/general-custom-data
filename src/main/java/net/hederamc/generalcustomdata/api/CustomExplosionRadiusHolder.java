package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.ByteTag;

public interface CustomExplosionRadiusHolder extends CustomDataHolder {
    default byte getCustomExplosionRadius() {
        return this.getCustomDataOrEmpty().tag().getByteOr("explosion_radius", (byte)0);
    }

    default void setCustomExplosionRadius(byte radius) {
        this.getOrCreateCustomData().tag().putByte("explosion_radius", radius);
    }

    default ByteTag removeCustomExplosionRadius() {
        return (ByteTag)this.getCustomDataOrEmpty().tag().remove("explosion_radius");
    }
}
