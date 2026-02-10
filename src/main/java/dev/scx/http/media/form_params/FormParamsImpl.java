package dev.scx.http.media.form_params;

import dev.scx.http.parameters.ParametersImpl;

import java.util.Collection;

/// FormParamsImpl
///
/// @author scx567888
/// @version 0.0.1
public final class FormParamsImpl extends ParametersImpl<String, String> implements FormParamsWritable {

    public FormParamsImpl() {

    }

    public FormParamsImpl(FormParams oldFormParams) {
        super(oldFormParams);
    }

    @Override
    public FormParamsImpl set(String name, String... values) {
        return (FormParamsImpl) super.set(name, values);
    }

    @Override
    public FormParamsImpl set(String name, Collection<? extends String> values) {
        return (FormParamsImpl) super.set(name, values);
    }

    @Override
    public FormParamsImpl add(String name, String... values) {
        return (FormParamsImpl) super.add(name, values);
    }

    @Override
    public FormParamsImpl add(String name, Collection<? extends String> values) {
        return (FormParamsImpl) super.add(name, values);
    }

    @Override
    public FormParamsImpl remove(String name) {
        return (FormParamsImpl) super.remove(name);
    }

    @Override
    public FormParamsImpl clear() {
        return (FormParamsImpl) super.clear();
    }

}
