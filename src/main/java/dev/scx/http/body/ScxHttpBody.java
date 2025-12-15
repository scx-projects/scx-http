package dev.scx.http.body;

import dev.scx.http.media.MediaReader;
import dev.scx.http.media.event_stream.ClientEventStream;
import dev.scx.http.media.file.FileMediaReader;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.multi_part.MultiPartStream;
import dev.scx.http.media.object.ObjectMediaReader;
import dev.scx.http.media.string.StringMediaReader;
import dev.scx.io.ByteInput;
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

    <T> T as(MediaReader<T> mediaReader) throws BodyAlreadyConsumedException, BodyReadException;

    //******************** asXXX 操作 *******************

    default byte[] asBytes() throws BodyReadException, BodyAlreadyConsumedException {
        return as(BYTE_ARRAY_MEDIA_READER);
    }

    default String asString() throws BodyReadException, BodyAlreadyConsumedException {
        return as(STRING_MEDIA_READER);
    }

    default String asString(Charset charset) throws BodyReadException, BodyAlreadyConsumedException {
        return as(new StringMediaReader(charset));
    }

    default File asFile(File file) throws BodyReadException, BodyAlreadyConsumedException {
        return as(new FileMediaReader(file));
    }

    default FormParams asFormParams() throws BodyReadException, BodyAlreadyConsumedException {
        return as(FORM_PARAMS_MEDIA_READER);
    }

    default MultiPartStream asMultiPart() throws BodyReadException, BodyAlreadyConsumedException {
        return as(MULTI_PART_STREAM_MEDIA_READER);
    }

    default Node asNode() throws BodyReadException, BodyAlreadyConsumedException {
        return as(NODE_MEDIA_READER);
    }

    default <T> T asObject(Class<T> c) throws BodyReadException, BodyAlreadyConsumedException {
        return as(new ObjectMediaReader<>(c));
    }

    default <T> T asObject(TypeReference<T> c) throws BodyReadException, BodyAlreadyConsumedException {
        return as(new ObjectMediaReader<>(c));
    }

    default ClientEventStream asEventStream() throws BodyReadException, BodyAlreadyConsumedException {
        return as(CLIENT_EVENT_STREAM_MEDIA_READER);
    }

    default GzipHttpBody asGzipBody() throws BodyReadException, BodyAlreadyConsumedException {
        return as(GzipHttpBody::new);
    }

    default CacheHttpBody asCacheBody() throws BodyReadException, BodyAlreadyConsumedException {
        return as(CacheHttpBody::new);
    }

}
