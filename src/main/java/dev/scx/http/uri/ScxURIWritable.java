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

    ScxURIWritable query(Parameters<String, String> query);

    ScxURIWritable fragment(String fragment);

    default ScxURIWritable setQuery(String name, String... value) {
        query().set(name, value);
        return this;
    }

    default ScxURIWritable addQuery(String name, String... value) {
        query().add(name, value);
        return this;
    }

    default ScxURIWritable addQuery(String name, Object... value) {
        var strArray = new String[value.length];
        for (int i = 0; i < value.length; i = i + 1) {
            strArray[i] = value[i].toString();
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
