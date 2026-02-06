package net.hederamc.generalcustomdata.effect;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;

/**
 * Tickable status effect episode.
 */
public class CustomStatusEffectEpisode {
    public static final int INFINITE = -1;
    public static final int MIN_AMPLIFIER = 0;
    public static final int MAX_AMPLIFIER = 255;
    private int duration;
    private int amplifier;

    public CustomStatusEffectEpisode(int duration, int amplifier) {
        this.duration = duration;
        this.amplifier = Math.clamp(amplifier, MIN_AMPLIFIER, MAX_AMPLIFIER);
    }

    public CustomStatusEffectEpisode(CustomStatusEffectEpisode episode) {
        this(episode.duration, episode.amplifier);
    }

    public CustomStatusEffectEpisode() {
        this(0, 0);
    }

    public static CustomStatusEffectEpisode of(int duration, int amplifier) {
        return new CustomStatusEffectEpisode(duration, amplifier);
    }

    public void tick() {
        if (this.duration == INFINITE) {
            return;
        }

        --this.duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int incrementDuration() {
        return this.incrementDuration(1);
    }

    public int incrementDuration(int value) {
        this.setDuration(this.duration + value);
        return this.duration;
    }

    public CustomStatusEffectEpisode withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = Math.clamp(amplifier, MIN_AMPLIFIER, MAX_AMPLIFIER);
    }

    public int incrementAmplifier() {
        return this.incrementAmplifier(1);
    }

    public int incrementAmplifier(int value) {
        this.setAmplifier(this.amplifier + value);
        return this.amplifier;
    }

    public CustomStatusEffectEpisode withAmplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    public boolean equals(CustomStatusEffectEpisode episode) {
        return this.duration == episode.duration && this.amplifier == episode.amplifier;
    }

    public int hashCode() {
        return Integer.hashCode(this.duration) * 31 + Integer.hashCode(this.amplifier);
    }

    public String toString() {
        return this.duration + " " + this.amplifier;
    }

    /**
     * The same as {@link #deepCopy()}.
     *
     * @return a new CustomStatusEffectEpisode
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectEpisode copy() {
        return new CustomStatusEffectEpisode(this);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffectEpisode
     *
     * @see #copy()
     */
    public CustomStatusEffectEpisode deepCopy() {
        return new CustomStatusEffectEpisode(this.duration, this.amplifier);
    }

    public static CustomStatusEffectEpisode fromNbt(CompoundTag nbtCompound) {
        return new CustomStatusEffectEpisode(
            nbtCompound.getIntOr("duration", 0),
            nbtCompound.getIntOr("amplifier", 0)
        );
    }

    public CompoundTag toNbt() {
        return new CompoundTag(
            new HashMap<>(
                Map.<String, Tag>of(
                    "duration",
                    IntTag.valueOf(this.duration),
                    "amplifier",
                    IntTag.valueOf(this.amplifier)
                )
            )
        );
    }
}
