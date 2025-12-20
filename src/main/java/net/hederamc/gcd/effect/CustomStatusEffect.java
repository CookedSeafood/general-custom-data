package net.hederamc.gcd.effect;

import java.util.HashMap;
import java.util.Map;
import net.hederamc.gr.registry.Registries;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

/**
 * Non-tickable status effect.
 */
public class CustomStatusEffect {
    public static final int INFINITE = -1;
	public static final int MIN_AMPLIFIER = 0;
	public static final int MAX_AMPLIFIER = 255;
    private CustomStatusEffectIdentifier id;
    private int duration;
    private int amplifier;

    public CustomStatusEffect(CustomStatusEffectIdentifier id, int duration, int amplifier) {
        this.id = id;
        this.duration = duration;
        this.amplifier = Math.clamp(amplifier, MIN_AMPLIFIER, MAX_AMPLIFIER);
    }

    public boolean addTo(CustomStatusEffectManager manager) {
        return manager.add(this);
    }

    public boolean setTo(CustomStatusEffectManager manager) {
        return manager.set(this);
    }

    public CustomStatusEffectIdentifier getId() {
        return this.id;
    }

    public void setId(CustomStatusEffectIdentifier id) {
        this.id = id;
    }

    public CustomStatusEffect withId(CustomStatusEffectIdentifier id) {
        this.id = id;
        return this;
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

    public CustomStatusEffect withDuration(int duration) {
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

    public CustomStatusEffect withAmplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffect
     *
     * @see #deepCopy()
     */
    public CustomStatusEffect copy() {
        return new CustomStatusEffect(this.id, this.duration, this.amplifier);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffect
     *
     * @see #copy()
     */
    public CustomStatusEffect deepCopy() {
        return new CustomStatusEffect(this.id.deepCopy(), this.duration, this.amplifier);
    }

    public static CustomStatusEffect fromNbt(NbtCompound nbtCompound) {
        return new CustomStatusEffect(
            Registries.get(
                CustomStatusEffectIdentifier.class,
                Identifier.of(nbtCompound.getString("id", ""))
            ),
            nbtCompound.getInt("duration", 0),
            nbtCompound.getInt("amplifier", 0)
        );
    }

    public NbtCompound toNbt() {
        return new NbtCompound(
            new HashMap<>(
                Map.<String, NbtElement>of(
                    "id",
                    NbtString.of(this.id.getId().toString()),
                    "duration",
                    NbtInt.of(this.duration),
                    "amplifier",
                    NbtInt.of(this.amplifier)
                )
            )
        );
    }
}
