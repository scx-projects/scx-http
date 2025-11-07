package cool.scx.http.parameters;

import cool.scx.collections.multi_map.IMultiMapEntry;

import java.util.List;

/// ParameterEntryImpl
///
/// @author scx567888
/// @version 0.0.1
public class ParameterEntryImpl<K, V> implements ParameterEntry<K, V> {

    private final IMultiMapEntry<K, V> entry;

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
