package dev.scx.http.parameters;

import dev.scx.collection.multi_map.MultiMap;
import dev.scx.function.Function2Void;

import java.util.*;

/// ParametersImpl (允许继承以便拓展功能)
///
/// @author scx567888
/// @version 0.0.1
public class ParametersImpl<K, V> implements ParametersWritable<K, V> {

    protected final MultiMap<K, V> map;

    public ParametersImpl(Parameters<K, V> p) {
        this();
        for (var e : p) {
            this.map.set(e.name(), e.values());
        }
    }

    public ParametersImpl() {
        this.map = new MultiMap<>(LinkedHashMap::new, ArrayList::new);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ParametersImpl<K, V> set(K name, V... values) {
        map.set(name, values);
        return this;
    }

    @Override
    public ParametersWritable<K, V> set(K name, Collection<? extends V> values) {
        map.set(name, values);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ParametersImpl<K, V> add(K name, V... values) {
        map.add(name, values);
        return this;
    }

    @Override
    public ParametersWritable<K, V> add(K name, Collection<? extends V> values) {
        map.add(name, values);
        return this;
    }

    @Override
    public ParametersImpl<K, V> remove(K name) {
        map.removeAll(name);
        return this;
    }

    @Override
    public ParametersWritable<K, V> clear() {
        map.clear();
        return this;
    }

    @Override
    public long size() {
        return map.size();
    }

    @Override
    public Set<K> names() {
        return map.keys();
    }

    @Override
    public V get(K name) {
        return map.get(name);
    }

    @Override
    public List<V> getAll(K name) {
        return map.getAll(name);
    }

    @Override
    public boolean contains(K name) {
        return map.containsKey(name);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Map<K, List<V>> toMultiValueMap() {
        return map.toMultiValueMap();
    }

    @Override
    public Map<K, V> toMap() {
        return map.toSingleValueMap();
    }

    @Override
    public <X extends Throwable> void forEach(Function2Void<? super K, V, X> action) throws X {
        map.forEach(action);
    }

    @Override
    public <X extends Throwable> void forEachParameter(Function2Void<? super K, List<V>, X> action) throws X {
        map.forEachEntry(action);
    }

    @Override
    public Iterator<ParameterEntry<K, V>> iterator() {
        return new ParametersIterator<>(map.iterator());
    }

    @Override
    public String toString() {
        return map.toString();
    }

}
