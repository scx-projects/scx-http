package dev.scx.http.media.form_params;

import dev.scx.http.parameters.ParametersWritable;

import java.util.Collection;

/// FormParamsWritable
///
/// @author scx567888
/// @version 0.0.1
public interface FormParamsWritable extends FormParams, ParametersWritable<String, String> {

    @Override
    FormParamsWritable set(String name, String... values);

    @Override
    FormParamsWritable set(String name, Collection<? extends String> values);

    @Override
    FormParamsWritable add(String name, String... values);

    @Override
    FormParamsWritable add(String name, Collection<? extends String> values);

    @Override
    FormParamsWritable remove(String name);

    @Override
    FormParamsWritable clear();

}
