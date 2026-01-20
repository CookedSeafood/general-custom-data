package net.hederamc.generalcustomdata.effect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Nullable;

/**
 * Tickable auto-ordering status effect episode container.
 *
 * @see #add(CustomStatusEffectEpisode)
 */
public class CustomStatusEffectPlaylist {
    private final List<CustomStatusEffectEpisode> episodes;

    public CustomStatusEffectPlaylist(List<CustomStatusEffectEpisode> episodes) {
        this.episodes = episodes;
    }

    public CustomStatusEffectPlaylist(CustomStatusEffectPlaylist playlist) {
        this(playlist.episodes);
    }

    public CustomStatusEffectPlaylist() {
        this(new ArrayList<>());
    }

    public CustomStatusEffectPlaylist with(CustomStatusEffectEpisode episode) {
        this.add(episode);
        return this;
    }

    public CustomStatusEffectPlaylist withAll(Collection<CustomStatusEffectEpisode> c) {
        c.forEach(this::with);
        return this;
    }

    /**
     * Get the duration of the presented status effects with the id and the highest amplifier.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveDuration() {
        CustomStatusEffectEpisode episode = this.getActiveEpisode();

        if (episode == null) {
            return -1;
        }

        return episode.getDuration();
    }

    /**
     * Get the highest amplifier of the presented status effects with the id.
     *
     * @param id
     * @return {@code -1} if there is no status effect with the id
     */
    public int getActiveAmplifier() {
        CustomStatusEffectEpisode episode = this.getActiveEpisode();

        if (episode == null) {
            return -1;
        }

        return episode.getAmplifier();
    }

    @Nullable
    public CustomStatusEffectEpisode getActiveEpisode() {
        return this.get(0);
    }

    public boolean addFrom(CustomStatusEffectPlaylist playlist) {
        return this.addAll(playlist.episodes);
    }

    public boolean addTo(CustomStatusEffectPlaylist playlist) {
        return playlist.addFrom(this);
    }

    public Set<Integer> amplifierSet() {
        return this.parallelStream()
            .map(CustomStatusEffectEpisode::getAmplifier)
            .collect(Collectors.toSet());
    }

    public void tick() {
        Iterator<CustomStatusEffectEpisode> iterator = this.iterator();

        while (iterator.hasNext()) {
            CustomStatusEffectEpisode episode = iterator.next();

            if (episode.getDuration() == 0) {
                iterator.remove();
                continue;
            }

            episode.tick();
        }
    }

    public List<CustomStatusEffectEpisode> getPlaylist() {
        return this.episodes;
    }

    public int size() {
        return this.episodes.size();
    }

    public boolean isEmpty() {
        return this.episodes.isEmpty();
    }

    public boolean contains(CustomStatusEffectEpisode episode) {
        return this.episodes.contains(episode);
    }

    public Iterator<CustomStatusEffectEpisode> iterator() {
        return this.episodes.iterator();
    }

    public void forEach(Consumer<? super CustomStatusEffectEpisode> action) {
        this.episodes.forEach(action);
    }

    /**
     * Add the status effect in descending order of amplifier.
     *
     * @param episode
     * @return {@code true}
     */
    public boolean add(CustomStatusEffectEpisode episode) {
        int amplifier = episode.getAmplifier();
        int duration = episode.getDuration();
        int size = this.size();

        for (int i = 0; i < size; ++i) {
            CustomStatusEffectEpisode presented = this.get(i);
            int presentedAmplifier = presented.getAmplifier();
            int presentedDuration = presented.getDuration();

            if (presentedAmplifier == amplifier && presentedDuration < duration) {
                presented.setDuration(duration);
                return true;
            }

            if (presentedAmplifier < amplifier) {
                this.add(i, episode);
                return true;
            }
        }

        return this.episodes.add(episode);
    }

    public boolean remove(CustomStatusEffectEpisode episode) {
        return this.episodes.remove(episode);
    }

    public boolean containsAll(Collection<CustomStatusEffectEpisode> c) {
        return this.episodes.containsAll(c);
    }

    /**
     * Add every status effect in descending order of amplifier.
     *
     * @param episode
     * @return {@code true}
     */
    public boolean addAll(Collection<CustomStatusEffectEpisode> c) {
        c.forEach(this::add);
        return true;
    }

    public boolean addAll(int index, Collection<CustomStatusEffectEpisode> c) {
        return this.episodes.addAll(index, c);
    }

    public boolean removeAll(Collection<CustomStatusEffectEpisode> c) {
        return this.episodes.removeAll(c);
    }

    public boolean removeIf(Predicate<? super CustomStatusEffectEpisode> filter) {
        return this.episodes.removeIf(filter);
    }

    public boolean retainAll(Collection<CustomStatusEffectEpisode> c) {
        return this.episodes.retainAll(c);
    }

    public void replaceAll(UnaryOperator<CustomStatusEffectEpisode> operator) {
        this.episodes.replaceAll(operator);
    }

    public void sort(Comparator<? super CustomStatusEffectEpisode> c) {
        this.episodes.sort(c);
    }

    public void clear() {
        this.episodes.clear();
    }

    public boolean equals(CustomStatusEffectPlaylist playlist) {
        return this.episodes.equals(playlist.episodes);
    }

    public int hashCode() {
        return this.episodes.hashCode();
    }

    public String toString() {
        return this.stream()
            .map(CustomStatusEffectEpisode::toString)
            .collect(Collectors.joining(",", "[", "]"));
    }

    public CustomStatusEffectEpisode get(int index) {
        return this.episodes.get(index);
    }

    public CustomStatusEffectEpisode set(int index, CustomStatusEffectEpisode episode) {
        return this.episodes.set(index, episode);
    }

    public void add(int index, CustomStatusEffectEpisode episode) {
        this.episodes.add(index, episode);
    }

    public CustomStatusEffectEpisode remove(int index) {
        return this.episodes.remove(index);
    }

    public int indexOf(CustomStatusEffectEpisode episode) {
        return this.episodes.indexOf(episode);
    }

    public int lastIndexOf(CustomStatusEffectEpisode episode) {
        return this.episodes.lastIndexOf(episode);
    }

    public ListIterator<CustomStatusEffectEpisode> listIterator() {
        return this.episodes.listIterator();
    }

    public ListIterator<CustomStatusEffectEpisode> listIterator(int index) {
        return this.episodes.listIterator(index);
    }

    public List<CustomStatusEffectEpisode> subList(int fromIndex, int toIndex) {
        return this.episodes.subList(fromIndex, toIndex);
    }

    public Spliterator<CustomStatusEffectEpisode> spliterator() {
        return this.episodes.spliterator();
    }

    public Stream<CustomStatusEffectEpisode> stream() {
        return this.episodes.stream();
    }

    public Stream<CustomStatusEffectEpisode> parallelStream() {
        return this.episodes.parallelStream();
    }

    public void addFirst(CustomStatusEffectEpisode episode) {
        this.episodes.addFirst(episode);
    }

    public void addLast(CustomStatusEffectEpisode episode) {
        this.episodes.addLast(episode);
    }

    public CustomStatusEffectEpisode getFirst() {
        return this.episodes.getFirst();
    }

    public CustomStatusEffectEpisode getLast() {
        return this.episodes.getLast();
    }

    public CustomStatusEffectEpisode removeFirst() {
        return this.episodes.removeFirst();
    }

    public CustomStatusEffectEpisode removeLast() {
        return this.episodes.removeLast();
    }

    public List<CustomStatusEffectEpisode> reversed() {
        return this.episodes.reversed();
    }

    /**
     * A shadow copy.
     *
     * @return a new CustomStatusEffectPlaylist
     *
     * @see #deepCopy()
     */
    public CustomStatusEffectPlaylist copy() {
        return new CustomStatusEffectPlaylist(this);
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

    public static CustomStatusEffectPlaylist fromNbt(ListTag nbtList) {
        return new CustomStatusEffectPlaylist(
            nbtList.stream()
                .map(CompoundTag.class::cast)
                .map(CustomStatusEffectEpisode::fromNbt)
                .collect(Collectors.toList())
        );
    }

    public ListTag toNbt() {
        return this.stream()
            .map(CustomStatusEffectEpisode::toNbt)
            .collect(ListTag::new, ListTag::add, ListTag::addAll);
    }

    public static CustomStatusEffectPlaylist of() {
        return new CustomStatusEffectPlaylist();
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1) {
        return new CustomStatusEffectPlaylist().with(episode1);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4,
                                                CustomStatusEffectEpisode episode5) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4).with(episode5);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4,
                                                CustomStatusEffectEpisode episode5,
                                                CustomStatusEffectEpisode episode6) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4).with(episode5).with(episode6);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4,
                                                CustomStatusEffectEpisode episode5,
                                                CustomStatusEffectEpisode episode6,
                                                CustomStatusEffectEpisode episode7) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4).with(episode5).with(episode6)
                                               .with(episode7);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4,
                                                CustomStatusEffectEpisode episode5,
                                                CustomStatusEffectEpisode episode6,
                                                CustomStatusEffectEpisode episode7,
                                                CustomStatusEffectEpisode episode8) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4).with(episode5).with(episode6)
                                               .with(episode7).with(episode8);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode episode1,
                                                CustomStatusEffectEpisode episode2,
                                                CustomStatusEffectEpisode episode3,
                                                CustomStatusEffectEpisode episode4,
                                                CustomStatusEffectEpisode episode5,
                                                CustomStatusEffectEpisode episode6,
                                                CustomStatusEffectEpisode episode7,
                                                CustomStatusEffectEpisode episode8,
                                                CustomStatusEffectEpisode episode9) {
        return new CustomStatusEffectPlaylist().with(episode1).with(episode2).with(episode3)
                                               .with(episode4).with(episode5).with(episode6)
                                               .with(episode7).with(episode8).with(episode9);
    }

    public static CustomStatusEffectPlaylist of(CustomStatusEffectEpisode... episodes) {
        return new CustomStatusEffectPlaylist().withAll(Arrays.asList(episodes));
    }
}
