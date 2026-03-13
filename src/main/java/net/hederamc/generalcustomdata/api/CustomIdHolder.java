package net.hederamc.generalcustomdata.api;

import java.util.Optional;
import net.hederamc.fishbonetrehalose.api.CustomDataHolder;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

public interface CustomIdHolder extends CustomDataHolder {
    @Nullable
    default Identifier getCustomId() {
        Optional<String> optional = this.getCustomDataOrEmpty().getTag().getString("id");

        if (optional.isEmpty()) {
            return null;
        }

        return Identifier.parse(optional.get());
    }

    default void setCustomId(Identifier id) {
        this.getOrCreateCustomData().getTag().putString("id", id.toString());
    }

    @Nullable
    default StringTag removeCustomId() {
        return (StringTag) this.getCustomDataOrEmpty().getTag().remove("id");
    }

    default Identifier getCustomIdOrId() {
        throw new UnsupportedOperationException();
    }
}
