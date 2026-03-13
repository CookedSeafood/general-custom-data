package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.ShortTag;
import org.jspecify.annotations.Nullable;

public interface CustomFuseHolder extends CustomDataHolder {
    default short getCustomFuse() {
        return this.getCustomDataOrEmpty().getTag().getShortOr("fuse", (short) 0);
    }

    default void setCustomFuse(short fuse) {
        this.getOrCreateCustomData().getTag().putShort("fuse", fuse);
    }

    @Nullable
    default ShortTag removeCustomFuse() {
        return (ShortTag) this.getCustomDataOrEmpty().getTag().remove("fuse");
    }
}
