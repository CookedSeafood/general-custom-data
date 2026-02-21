package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.ShortTag;

public interface CustomFuseHolder extends CustomDataHolder {
    default short getCustomFuse() {
        return this.getCustomDataOrEmpty().tag().getShortOr("fuse", (short)0);
    }

    default void setCustomFuse(short fuse) {
        this.getOrCreateCustomData().tag().putShort("fuse", fuse);
    }

    default ShortTag removeCustomFuse() {
        return (ShortTag)this.getCustomDataOrEmpty().tag().remove("fuse");
    }
}
