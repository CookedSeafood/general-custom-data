package net.hederamc.generalcustomdata.api;

import net.minecraft.resources.Identifier;

public interface CustomIdHolder {
    default Identifier getCustomId() {
        throw new UnsupportedOperationException();
    }

    default void setCustomId(Identifier id) {
        throw new UnsupportedOperationException();
    }

    default Identifier getCustomIdOrId() {
        throw new UnsupportedOperationException();
    }
}
