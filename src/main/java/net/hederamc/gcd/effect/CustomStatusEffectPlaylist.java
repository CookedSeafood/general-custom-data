package net.hederamc.gcd.effect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

/**
 * Tickable single-id auto-ordering status effect container.
 *
 * @see #add(CustomStatusEffectEpisode)
 */
public class CustomStatusEffectPlaylist {
    private final List<CustomStatusEffectEpisode> playlist;

    public CustomStatusEffectPlaylist(List<CustomStatusEffectEpisode> playlist) {
        this.playlist = playlist;
    }

    public CustomStatusEffectPlaylist() {
        this.playlist = new ArrayList<>();
    }

    public int getActiveAmplifier() {
        CustomStatusEffectEpisode statusEffect = this.get(0);

        if (statusEffect == null) {
            return -1;
        }

        return statusEffect.getAmplifier();
    }

    public int getActiveDuration() {
        CustomStatusEffectEpisode statusEffect = this.get(0);

        if (statusEffect == null) {
            return -1;
        }

        return statusEffect.getDuration();
    }

    public void tick() {
        Iterator<CustomStatusEffectEpisode> iterator = this.iterator();

        while (iterator.hasNext()) {
            CustomStatusEffectEpisode statusEffect = iterator.next();

            if (statusEffect.getDuration() == 0) {
                iterator.remove();
                continue;
            }

            statusEffect.tick();
        }
    }

    public List<CustomStatusEffectEpisode> getPlaylist() {
        return this.playlist;
    }

    public int size() {
        return this.playlist.size();
    }

    public boolean isEmpty() {
        return this.playlist.isEmpty();
    }

    public boolean contains(CustomStatusEffectEpisode statusEffect) {
        return this.playlist.contains(statusEffect);
    }

    public boolean containsAll(Collection<CustomStatusEffectEpisode> statusEffects) {
        return this.playlist.containsAll(statusEffects);
    }

    public CustomStatusEffectEpisode get(int index) {
        return this.playlist.get(index);
    }

    /**
     * Add the status effect in descending order of amplifier.
     *
     * @param statusEffect
     * @return {@code true}
     */
    public boolean add(CustomStatusEffectEpisode statusEffect) {
        int amplifier = statusEffect.getAmplifier();
        int size = this.size();

        for (int i = 0; i < size; ++i) {
            if (this.get(i).getAmplifier() < amplifier) {
                this.add(i, statusEffect);
                return true;
            }
        }

        return this.playlist.add(statusEffect);
    }

    public void add(int i, CustomStatusEffectEpisode statusEffect) {
        this.playlist.add(i, statusEffect);
    }

    /**
     * Add every status effect in descending order of amplifier.
     *
     * @param statusEffect
     * @return {@code true}
     */
    public boolean addAll(Collection<CustomStatusEffectEpisode> statusEffects) {
        statusEffects.forEach(this::add);
        return true;
    }

    public boolean addAll(int i, Collection<CustomStatusEffectEpisode> statusEffects) {
        return this.playlist.addAll(i, statusEffects);
    }

    public boolean remove(CustomStatusEffectEpisode statusEffect) {
        return this.playlist.remove(statusEffect);
    }

    public boolean removeAll(Collection<CustomStatusEffectEpisode> statusEffects) {
        return this.playlist.removeAll(statusEffects);
    }

    public boolean removeIf(Predicate<? super CustomStatusEffectEpisode> filter) {
        return this.playlist.removeIf(filter);
    }

    public void clear() {
        this.playlist.clear();
    }

    public void forEach(Consumer<? super CustomStatusEffectEpisode> action) {
        this.playlist.forEach(action);
    }

    public Iterator<CustomStatusEffectEpisode> iterator() {
        return this.playlist.iterator();
    }

    public Stream<CustomStatusEffectEpisode> stream() {
        return this.playlist.stream();
    }

    public void sort(Comparator<? super CustomStatusEffectEpisode> c) {
        this.playlist.sort(c);
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffectPlaylist
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectPlaylist copy() {
        return new CustomStatusEffectPlaylist(this.playlist);
    }

    /**
     * A deep copy.
     *
     * @return a new CustomStatusEffectPlaylist
     *
     * @see #copy()
     */
    public CustomStatusEffectPlaylist deepCopy() {
        return new CustomStatusEffectPlaylist(
            this.stream()
                .map(CustomStatusEffectEpisode::deepCopy)
                .collect(Collectors.toList())
        );
    }

    public static CustomStatusEffectPlaylist fromNbt(NbtList nbtList) {
        return new CustomStatusEffectPlaylist(
            nbtList.stream()
                .map(NbtCompound.class::cast)
                .map(CustomStatusEffectEpisode::fromNbt)
                .collect(Collectors.toList())
        );
    }

    public NbtList toNbt() {
        return this.stream()
            .map(CustomStatusEffectEpisode::toNbt)
            .collect(NbtList::new, NbtList::add, NbtList::addAll);
    }
}
