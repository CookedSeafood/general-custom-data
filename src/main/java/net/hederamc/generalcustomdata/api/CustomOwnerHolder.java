package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.StringTag;
import org.jspecify.annotations.Nullable;

public interface CustomOwnerHolder extends CustomDataHolder {
    @Nullable
    default String getCustomOwner() {
        return this.getCustomDataOrEmpty().getTag().getStringOr("owner", null);
    }

    default void setCustomOwner(String owner) {
        this.getOrCreateCustomData().getTag().putString("owner", owner);
    }

    @Nullable
    default StringTag removeCustomOwner() {
        return (StringTag)this.getCustomDataOrEmpty().getTag().remove("owner");
    }
}
