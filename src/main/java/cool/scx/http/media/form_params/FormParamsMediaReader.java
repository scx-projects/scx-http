package cool.scx.http.media.form_params;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

import static cool.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;

/// FormParamsMediaReader
///
/// @author scx567888
/// @version 0.0.1
public final class FormParamsMediaReader implements MediaReader<FormParams> {

    public static final FormParamsMediaReader FORM_PARAMS_MEDIA_READER = new FormParamsMediaReader();

    private FormParamsMediaReader() {

    }

    @Override
    public FormParams read(ByteInput byteInput, ScxHttpHeaders headers) throws ScxIOException, AlreadyClosedException {
        // FormParams 本质上就是字符串 所以这里使用 STRING_READER 先进行内容读取
        var str = STRING_MEDIA_READER.read(byteInput, headers);
        return FormParams.of(str);
    }

}
