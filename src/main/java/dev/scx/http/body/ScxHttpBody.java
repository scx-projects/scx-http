package dev.scx.http.body;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media.event_stream.ClientEventStream;
import dev.scx.http.media.file.FileMediaReader;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.multi_part.MultiPartStream;
import dev.scx.http.media.object.ObjectMediaReader;
import dev.scx.http.media.string.StringMediaReader;
import dev.scx.io.ByteInput;
import dev.scx.io.exception.AlreadyClosedException;
import dev.scx.io.exception.ScxIOException;
import dev.scx.node.Node;
import dev.scx.reflect.TypeReference;

import java.io.File;
import java.nio.charset.Charset;

import static dev.scx.http.media.byte_array.ByteArrayMediaReader.BYTE_ARRAY_MEDIA_READER;
import static dev.scx.http.media.event_stream.ClientEventStreamMediaReader.CLIENT_EVENT_STREAM_MEDIA_READER;
import static dev.scx.http.media.form_params.FormParamsMediaReader.FORM_PARAMS_MEDIA_READER;
import static dev.scx.http.media.multi_part.MultiPartStreamMediaReader.MULTI_PART_STREAM_MEDIA_READER;
import static dev.scx.http.media.node.NodeMediaReader.NODE_MEDIA_READER;
import static dev.scx.http.media.string.StringMediaReader.STRING_MEDIA_READER;

/// ScxHttpBody
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpBody {

    ByteInput byteInput();

    <T> T as(MediaReader<T> mediaReader) throws ScxIOException, AlreadyClosedException;

    //******************** asXXX 操作 *******************

    default byte[] asBytes() throws ScxIOException, AlreadyClosedException {
        return as(BYTE_ARRAY_MEDIA_READER);
    }

    default String asString() throws ScxIOException, AlreadyClosedException {
        return as(STRING_MEDIA_READER);
    }

    default String asString(Charset charset) throws ScxIOException, AlreadyClosedException {
        return as(new StringMediaReader(charset));
    }

    default File asFile(File file) throws ScxIOException, AlreadyClosedException {
        return as(new FileMediaReader(file));
    }

    default FormParams asFormParams() throws ScxIOException, AlreadyClosedException {
        return as(FORM_PARAMS_MEDIA_READER);
    }

    default MultiPartStream asMultiPart() throws ScxIOException, AlreadyClosedException {
        return as(MULTI_PART_STREAM_MEDIA_READER);
    }

    default Node asNode() throws ScxIOException, AlreadyClosedException {
        return as(NODE_MEDIA_READER);
    }

    default <T> T asObject(Class<T> c) throws ScxIOException, AlreadyClosedException {
        return as(new ObjectMediaReader<>(c));
    }

    default <T> T asObject(TypeReference<T> c) throws ScxIOException, AlreadyClosedException {
        return as(new ObjectMediaReader<>(c));
    }

    default ClientEventStream asEventStream() throws ScxIOException, AlreadyClosedException {
        return as(CLIENT_EVENT_STREAM_MEDIA_READER);
    }

    default GzipHttpBody asGzipBody() throws ScxIOException, AlreadyClosedException {
        return as(GzipHttpBody::new);
    }

    default CacheHttpBody asCacheBody() throws ScxIOException, AlreadyClosedException {
        return as(CacheHttpBody::new);
    }

}
