package dev.scx.http.parameters;

import dev.scx.collection.multi_map.IMultiMapEntry;

import java.util.List;

/// ParameterEntryImpl (允许继承以便拓展功能)
///
/// @author scx567888
/// @version 0.0.1
public class ParameterEntryImpl<K, V> implements ParameterEntry<K, V> {

    protected final IMultiMapEntry<K, V> entry;

    public ParameterEntryImpl(IMultiMapEntry<K, V> entry) {
        this.entry = entry;
    }

    @Override
    public K name() {
        return entry.key();
    }

    @Override
    public V value() {
        return entry.value();
    }

    @Override
    public List<V> values() {
        return entry.values();
    }

}
