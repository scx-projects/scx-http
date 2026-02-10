package dev.scx.http.parameters;

import java.util.Collection;

/// ParametersWritable
///
/// @author scx567888
/// @version 0.0.1
@SuppressWarnings("unchecked")
public interface ParametersWritable<K, V> extends Parameters<K, V> {

    ParametersWritable<K, V> set(K name, V... values);

    ParametersWritable<K, V> set(K name, Collection<? extends V> values);

    ParametersWritable<K, V> add(K name, V... values);

    ParametersWritable<K, V> add(K name, Collection<? extends V> values);

    ParametersWritable<K, V> remove(K name);

    ParametersWritable<K, V> clear();

}
