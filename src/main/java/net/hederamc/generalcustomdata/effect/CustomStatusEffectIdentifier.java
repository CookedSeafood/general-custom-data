package net.hederamc.generalcustomdata.effect;

import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;
import net.hederamc.genericregistry.registry.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public final class CustomStatusEffectIdentifier {
    public static final StreamCodec<ByteBuf, CustomStatusEffectIdentifier> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            CustomStatusEffectIdentifier::getId,
            ByteBufCodecs.INT,
            CustomStatusEffectIdentifier::getColor,
            CustomStatusEffectIdentifier::new);
    private final Identifier id;
    private final int color;

    public CustomStatusEffectIdentifier(Identifier id, int color) {
        this.id = id;
        this.color = color;
    }

    public CustomStatusEffectIdentifier(Identifier id) {
        this(id, 0);
    }

    public CustomStatusEffectIdentifier(CustomStatusEffectIdentifier id) {
        this(id.id, id.color);
    }

    public static CustomStatusEffectIdentifier of(Identifier id) {
        return new CustomStatusEffectIdentifier(id);
    }

    public static CustomStatusEffectIdentifier fromRegistry(Identifier id) {
        return Registries.get(CustomStatusEffectIdentifier.class, id);
    }

    public boolean removeFrom(CustomStatusEffectManager manager) {
        return manager.remove(this);
    }

    public boolean isIdOf(CustomStatusEffect statusEffect) {
        return statusEffect.isOf(this);
    }

    public Identifier getId() {
        return this.id;
    }

    public int getColor() {
        return this.color;
    }

    public boolean equals(CustomStatusEffectIdentifier id) {
        return this.id.equals(id.id) && this.color == id.color;
    }

    public int hashCode() {
        return this.id.hashCode() * 31 + Integer.hashCode(this.color);
    }

    public String toString() {
        return this.id.toString() + " " + this.color;
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffectIdentifier
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectIdentifier copy() {
        return new CustomStatusEffectIdentifier(this);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffectIdentifier
     *
     * @see #copy()
     */
    public CustomStatusEffectIdentifier deepCopy() {
        return new CustomStatusEffectIdentifier(Identifier.fromNamespaceAndPath(this.id.getNamespace(), this.id.getPath()), this.color);
    }

    public static CustomStatusEffectIdentifier fromNbt(CompoundTag nbtCompound) {
        return new CustomStatusEffectIdentifier(
            Identifier.parse(nbtCompound.getStringOr("id", "")),
            nbtCompound.getIntOr("color", 0)
        );
    }

    public CompoundTag toNbt() {
        return new CompoundTag(
            new HashMap<>(
                Map.<String, Tag>of(
                    "id",
                    StringTag.valueOf(this.id.toString()),
                    "color",
                    IntTag.valueOf(this.color)
                )
            )
        );
    }
}
