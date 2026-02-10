package dev.scx.http.media.form_params;

import dev.scx.http.parameters.Parameters;

/// FormParams
///
/// @author scx567888
/// @version 0.0.1
public interface FormParams extends Parameters<String, String> {

    static FormParamsWritable of() {
        return new FormParamsImpl();
    }

    static FormParamsWritable of(FormParams oldFormParams) {
        return new FormParamsImpl(oldFormParams);
    }

    /// 从编码后的文本内容中生成 FormParams
    static FormParamsWritable parse(String encodedStr) {
        return FormParamsHelper.parseFormParams(new FormParamsImpl(), encodedStr);
    }

    /// 编码成 from 表单格式
    default String encode() {
        return FormParamsHelper.encodeFormParams(this);
    }

}
