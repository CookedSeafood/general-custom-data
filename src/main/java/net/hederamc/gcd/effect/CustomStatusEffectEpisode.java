package net.hederamc.gcd.effect;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;

/**
 * Tickable status effect.
 */
public class CustomStatusEffectEpisode {
    private int duration;
    private int amplifier;

    public CustomStatusEffectEpisode(int duration, int amplifier) {
        this.duration = duration;
        this.amplifier = Math.clamp(amplifier, CustomStatusEffect.MIN_AMPLIFIER, CustomStatusEffect.MAX_AMPLIFIER);
    }

    public void tick() {
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
        this.amplifier = Math.clamp(amplifier, CustomStatusEffect.MIN_AMPLIFIER, CustomStatusEffect.MAX_AMPLIFIER);
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

    /**
     * The same as {@link #deepCopy()}.
     *
     * @return a new CustomStatusEffect
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectEpisode copy() {
        return this.deepCopy();
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffect
     *
     * @see #copy()
     */
    public CustomStatusEffectEpisode deepCopy() {
        return new CustomStatusEffectEpisode(this.duration, this.amplifier);
    }

    public static CustomStatusEffectEpisode fromNbt(NbtCompound nbtCompound) {
        return new CustomStatusEffectEpisode(
            nbtCompound.getInt("duration", 0),
            nbtCompound.getInt("amplifier", 0)
        );
    }

    public NbtCompound toNbt() {
        return new NbtCompound(
            new HashMap<>(
                Map.<String, NbtElement>of(
                    "duration",
                    NbtInt.of(this.duration),
                    "amplifier",
                    NbtInt.of(this.amplifier)
                )
            )
        );
    }
}
