package dev.scx.http.media.form_params;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.InputAlreadyClosedException;
import dev.scx.io.exception.ScxInputException;

import static dev.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;

/// FormParamsMediaReader
///
/// @author scx567888
/// @version 0.0.1
public final class FormParamsMediaReader implements MediaReader<FormParams, RuntimeException> {

    public static final FormParamsMediaReader FORM_PARAMS_MEDIA_READER = new FormParamsMediaReader();

    private FormParamsMediaReader() {

    }

    @Override
    public FormParams read(ByteInput byteInput, ScxMediaType mediaType) throws ScxInputException, InputAlreadyClosedException {
        // FormParams 本质上就是字符串 所以这里使用 STRING_READER 先进行内容读取
        var str = STRING_MEDIA_READER.read(byteInput, mediaType);
        return FormParams.parse(str);
    }

}
