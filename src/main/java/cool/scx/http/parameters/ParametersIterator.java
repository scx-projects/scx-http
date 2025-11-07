package cool.scx.http.parameters;

import cool.scx.collections.multi_map.IMultiMapEntry;

import java.util.Iterator;

/// ParametersIterator
///
/// @author scx567888
/// @version 0.0.1
public class ParametersIterator<K, V> implements Iterator<ParameterEntry<K, V>> {

    private final Iterator<IMultiMapEntry<K, V>> iterator;

    public ParametersIterator(Iterator<IMultiMapEntry<K, V>> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public ParameterEntry<K, V> next() {
        var next = iterator.next();
        return new ParameterEntryImpl<>(next);
    }

}
