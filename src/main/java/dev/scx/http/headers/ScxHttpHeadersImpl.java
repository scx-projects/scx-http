package dev.scx.http.headers;

import dev.scx.http.parameters.ParametersImpl;

import java.util.Collection;

/// ScxHttpHeadersImpl (允许继承以便拓展功能)
///
/// @author scx567888
/// @version 0.0.1
public class ScxHttpHeadersImpl extends ParametersImpl<ScxHttpHeaderName, String> implements ScxHttpHeadersWritable {

    public ScxHttpHeadersImpl(ScxHttpHeaders h) {
        super(h);
    }

    public ScxHttpHeadersImpl() {

    }

    @Override
    public ScxHttpHeadersImpl set(ScxHttpHeaderName name, String... values) {
        return (ScxHttpHeadersImpl) super.set(name, values);
    }

    @Override
    public ScxHttpHeadersImpl set(ScxHttpHeaderName name, Collection<? extends String> values) {
        return (ScxHttpHeadersImpl) super.set(name, values);
    }

    @Override
    public ScxHttpHeadersImpl add(ScxHttpHeaderName name, String... values) {
        return (ScxHttpHeadersImpl) super.add(name, values);
    }

    @Override
    public ScxHttpHeadersImpl add(ScxHttpHeaderName name, Collection<? extends String> values) {
        return (ScxHttpHeadersImpl) super.add(name, values);
    }

    @Override
    public ScxHttpHeadersImpl remove(ScxHttpHeaderName name) {
        return (ScxHttpHeadersImpl) super.remove(name);
    }

    @Override
    public ScxHttpHeadersImpl clear() {
        return (ScxHttpHeadersImpl) super.clear();
    }

    @Override
    public String toString() {
        return encode();
    }

}
