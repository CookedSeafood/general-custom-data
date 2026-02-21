package net.hederamc.generalcustomdata.api;

import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.StringTag;
import org.jspecify.annotations.Nullable;

public interface CustomOwnerHolder extends CustomDataHolder {
    @Nullable
    default String getCustomOwner() {
        return this.getCustomDataOrEmpty().tag().getStringOr("owner", null);
    }

    default void setCustomOwner(String owner) {
        this.getOrCreateCustomData().tag().putString("owner", owner);
    }

    default StringTag removeCustomOwner() {
        return (StringTag)this.getCustomDataOrEmpty().tag().remove("owner");
    }
}
