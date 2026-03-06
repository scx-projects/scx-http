package dev.scx.http.uri;

import dev.scx.http.parameters.Parameters;
import dev.scx.http.parameters.ParametersWritable;

/// ScxURIWritable
///
/// @author scx567888
/// @version 0.0.1
public interface ScxURIWritable extends ScxURI {

    @Override
    ParametersWritable<String, String> query();

    ScxURIWritable scheme(String scheme);

    ScxURIWritable host(String host);

    ScxURIWritable port(Integer port);

    ScxURIWritable path(String path);

    ScxURIWritable fragment(String fragment);

    default ScxURIWritable query(Parameters<String, String> otherQuery) {
        query().clear();
        for (var e : otherQuery) {
            query().set(e.name(), e.values());
        }
        return this;
    }

    default ScxURIWritable setQuery(String name, String... values) {
        query().set(name, values);
        return this;
    }

    default ScxURIWritable addQuery(String name, String... values) {
        query().add(name, values);
        return this;
    }

    default ScxURIWritable addQuery(String name, Object... values) {
        var strArray = new String[values.length];
        for (int i = 0; i < values.length; i = i + 1) {
            strArray[i] = values[i].toString();
        }
        query().add(name, strArray);
        return this;
    }

    default ScxURIWritable removeQuery(String name) {
        query().remove(name);
        return this;
    }

    default ScxURIWritable clearQuery() {
        query().clear();
        return this;
    }

}
