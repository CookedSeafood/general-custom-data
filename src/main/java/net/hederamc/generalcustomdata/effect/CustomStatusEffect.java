package net.hederamc.generalcustomdata.effect;

import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class CustomStatusEffect {
    public static final StreamCodec<ByteBuf, CustomStatusEffect> STREAM_CODEC = StreamCodec.composite(
            CustomStatusEffectIdentifier.STREAM_CODEC,
            CustomStatusEffect::getId,
            CustomStatusEffectPlaylist.STREAM_CODEC,
            CustomStatusEffect::getPlaylist,
            CustomStatusEffect::new);
    private final CustomStatusEffectIdentifier id;
    private final CustomStatusEffectPlaylist playlist;

    public CustomStatusEffect(CustomStatusEffectIdentifier id, CustomStatusEffectPlaylist playlist) {
        this.id = id;
        this.playlist = playlist;
    }

    public CustomStatusEffect(CustomStatusEffectIdentifier id) {
        this(id, new CustomStatusEffectPlaylist());
    }

    public CustomStatusEffect(CustomStatusEffect statusEffect) {
        this(statusEffect.id, statusEffect.playlist);
    }

    public static CustomStatusEffect of(CustomStatusEffectIdentifier id) {
        return new CustomStatusEffect(id);
    }

    public boolean addEpisode(CustomStatusEffectEpisode episode) {
        return this.playlist.add(episode);
    }

    public CustomStatusEffect withEpisode(CustomStatusEffectEpisode episode) {
        this.addEpisode(episode);
        return this;
    }

    /**
     * Get the duration of the presented status effects with the id and the highest amplifier.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveDuration() {
        return this.playlist.getActiveDuration();
    }

    /**
     * Get the highest amplifier of the presented status effects with the id.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveAmplifier() {
        return this.playlist.getActiveAmplifier();
    }

    public boolean merge(CustomStatusEffect statusEffect) {
        return this.playlist.addAll(statusEffect.playlist);
    }

    public boolean mergeTo(CustomStatusEffect statusEffect) {
        return statusEffect.merge(this);
    }

    public boolean addTo(CustomStatusEffectManager manager) {
        return manager.add(this);
    }

    public boolean setTo(CustomStatusEffectManager manager) {
        return manager.set(this);
    }

    public boolean isOf(CustomStatusEffectIdentifier id) {
        return this.id.equals(id);
    }

    public int getColor() {
        return this.id.getColor();
    }

    public void tick() {
        this.playlist.tick();
    }

    public CustomStatusEffectIdentifier getId() {
        return this.id;
    }

    public CustomStatusEffectPlaylist getPlaylist() {
        return this.playlist;
    }

    public boolean equals(CustomStatusEffect statusEffect) {
        return this.id.equals(statusEffect.id) && this.playlist.equals(statusEffect.playlist);
    }

    public int hashCode() {
        return this.id.hashCode() * 31 + this.playlist.hashCode();
    }

    public String toString() {
        return this.id.toString() + " " + this.playlist.toString();
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffect
     *
     * @see #deepCopy()
     */
    public CustomStatusEffect copy() {
        return new CustomStatusEffect(this);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffect
     *
     * @see #copy()
     */
    public CustomStatusEffect deepCopy() {
        return new CustomStatusEffect(this.id.deepCopy(), this.playlist.deepCopy());
    }

    public static CustomStatusEffect fromNbt(CompoundTag nbtCompound) {
        return new CustomStatusEffect(
            CustomStatusEffectIdentifier.fromRegistry(Identifier.parse(nbtCompound.getStringOr("id", ""))),
            CustomStatusEffectPlaylist.fromNbt(nbtCompound.getListOrEmpty("episodes"))
        );
    }

    public CompoundTag toNbt() {
        return new CompoundTag(
            new HashMap<>(
                Map.<String, Tag>of(
                    "id",
                    StringTag.valueOf(this.id.getId().toString()),
                    "episodes",
                    this.playlist.toNbt()
                )
            )
        );
    }
}
