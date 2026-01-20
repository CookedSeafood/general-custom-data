package net.hederamc.generalcustomdata.effect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.hederamc.generalcustomdata.api.CustomStatusEffectsHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

/**
 * Tickable status effect container.
 */
public class CustomStatusEffectManager {
    protected final Set<CustomStatusEffect> statusEffects;

    public CustomStatusEffectManager(Set<CustomStatusEffect> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public CustomStatusEffectManager(CustomStatusEffectManager manager) {
        this(manager.statusEffects);
    }

    public CustomStatusEffectManager() {
        this(new HashSet<>());
    }

    public static CustomStatusEffectManager fromHolder(CustomStatusEffectsHolder holder) {
        return CustomStatusEffectManager.fromNbt(holder.getCustomStatusEffects());
    }

    /**
     * Get the duration of the presented status effects with the id and the highest amplifier.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveDuration(CustomStatusEffectIdentifier id) {
        CustomStatusEffect statusEffect = this.get(id);

        if (statusEffect == null) {
            return -1;
        }

        return statusEffect.getActiveDuration();
    }

    /**
     * Get the highest amplifier of the presented status effects with the id.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveAmplifier(CustomStatusEffectIdentifier id) {
        CustomStatusEffect statusEffect = this.get(id);

        if (statusEffect == null) {
            return -1;
        }

        return statusEffect.getActiveAmplifier();
    }

    public boolean contains(CustomStatusEffectIdentifier id) {
        return this.parallelStream()
            .anyMatch(id::isIdOf);
    }

    @Nullable
    public CustomStatusEffect get(CustomStatusEffectIdentifier id) {
        return this.parallelStream()
            .filter(id::isIdOf)
            .findAny()
            .orElse(null);
    }

    public boolean set(CustomStatusEffect statusEffect) {
        this.remove(statusEffect.getId());
        return this.add(statusEffect);
    }

    public boolean remove(CustomStatusEffectIdentifier id) {
        Iterator<CustomStatusEffect> iterator = this.iterator();

        while (iterator.hasNext()) {
            CustomStatusEffect statusEffect = iterator.next();

            if (statusEffect.isOf(id)) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public boolean setAll(Collection<CustomStatusEffect> c) {
        c.forEach(this::set);
        return true;
    }

    public boolean addFrom(CustomStatusEffectManager manager) {
        return this.addAll(manager.statusEffects);
    }

    public boolean addTo(CustomStatusEffectManager manager) {
        return manager.addFrom(this);
    }

    public Set<CustomStatusEffectIdentifier> idSet() {
        return this.parallelStream()
            .map(CustomStatusEffect::getId)
            .collect(Collectors.toSet());
    }

    public void tick() {
        Iterator<CustomStatusEffect> iterator = this.iterator();

        while (iterator.hasNext()) {
            CustomStatusEffect statusEffect = iterator.next();
            statusEffect.tick();

            if (statusEffect.getPlaylist().isEmpty()) {
                iterator.remove();
                continue;
            }
        }
    }

    public Set<CustomStatusEffect> getStatusEffects() {
        return this.statusEffects;
    }

    public int size() {
        return this.statusEffects.size();
    }

    public boolean isEmpty() {
        return this.statusEffects.isEmpty();
    }

    public boolean contains(CustomStatusEffect statusEffect) {
        return this.statusEffects.contains(statusEffect);
    }

    public Iterator<CustomStatusEffect> iterator() {
        return this.statusEffects.iterator();
    }

    public void forEach(Consumer<? super CustomStatusEffect> action) {
        this.statusEffects.forEach(action);
    }

    public boolean add(CustomStatusEffect statusEffect) {
        CustomStatusEffect presented = this.get(statusEffect.getId());

        if (presented == null) {
            return this.statusEffects.add(statusEffect);
        }

        return presented.addFrom(statusEffect);
    }

    public boolean remove(CustomStatusEffect statusEffect) {
        return this.statusEffects.remove(statusEffect);
    }

    public boolean containsAll(Collection<CustomStatusEffect> c) {
        return this.statusEffects.containsAll(c);
    }

    public boolean addAll(Collection<CustomStatusEffect> c) {
        c.forEach(this::add);
        return true;
    }

    public boolean removeAll(Collection<CustomStatusEffect> c) {
        return this.statusEffects.removeAll(c);
    }

    public boolean removeIf(Predicate<? super CustomStatusEffect> filter) {
        return this.statusEffects.removeIf(filter);
    }

    public boolean retainAll(Collection<CustomStatusEffect> c) {
        return this.statusEffects.retainAll(c);
    }

    public void clear() {
        this.statusEffects.clear();
    }

    public boolean equals(CustomStatusEffectManager manager) {
        return this.statusEffects.equals(manager.statusEffects);
    }

    public int hashCode() {
        return this.statusEffects.hashCode();
    }

    public String toString() {
        return this.stream()
            .map(CustomStatusEffect::toString)
            .collect(Collectors.joining(",", "[", "]"));
    }

    public Spliterator<CustomStatusEffect> spliterator() {
        return this.statusEffects.spliterator();
    }

    public Stream<CustomStatusEffect> stream() {
        return this.statusEffects.stream();
    }

    public Stream<CustomStatusEffect> parallelStream() {
        return this.statusEffects.parallelStream();
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffectManager
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectManager copy() {
        return new CustomStatusEffectManager(this);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffectManager
     *
     * @see #copy()
     */
    public CustomStatusEffectManager deepCopy() {
        return new CustomStatusEffectManager(
            this.stream()
                .map(CustomStatusEffect::deepCopy)
                .collect(Collectors.toSet())
        );
    }

    public static CustomStatusEffectManager fromNbt(ListTag nbtList) {
        return new CustomStatusEffectManager(
            nbtList.stream()
                .map(CompoundTag.class::cast)
                .map(CustomStatusEffect::fromNbt)
                .collect(Collectors.toSet())
        );
    }

    public ListTag toNbt() {
        return this.stream()
            .map(CustomStatusEffect::toNbt)
            .collect(ListTag::new, ListTag::add, ListTag::addAll);
    }
}
