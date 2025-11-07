package cool.scx.http.body;

import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.media.MediaReader;
import cool.scx.io.ByteInput;
import cool.scx.io.exception.AlreadyClosedException;
import cool.scx.io.exception.ScxIOException;

/// DefaultHttpBody
///
/// @author scx567888
/// @version 0.0.1
public record DefaultHttpBody(ByteInput byteInput, ScxHttpHeaders headers) implements ScxHttpBody {

    @Override
    public <T> T as(MediaReader<T> mediaReader) throws BodyAlreadyConsumedException, BodyReadException {
        try {
            return mediaReader.read(byteInput, headers);
        } catch (ScxIOException e) {
            throw new BodyReadException(e);
        } catch (AlreadyClosedException e) {
            throw new BodyAlreadyConsumedException();
        }
    }

}
