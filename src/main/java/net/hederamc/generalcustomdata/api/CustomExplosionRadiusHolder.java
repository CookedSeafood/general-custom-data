package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.ByteTag;
import org.jspecify.annotations.Nullable;

public interface CustomExplosionRadiusHolder extends CustomDataHolder {
    default byte getCustomExplosionRadius() {
        return this.getCustomDataOrEmpty().getTag().getByteOr("explosion_radius", (byte)0);
    }

    default void setCustomExplosionRadius(byte radius) {
        this.getOrCreateCustomData().getTag().putByte("explosion_radius", radius);
    }

    @Nullable
    default ByteTag removeCustomExplosionRadius() {
        return (ByteTag)this.getCustomDataOrEmpty().getTag().remove("explosion_radius");
    }
}
