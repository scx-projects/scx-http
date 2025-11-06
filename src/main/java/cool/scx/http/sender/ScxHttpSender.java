package cool.scx.http.sender;

import cool.scx.http.media.MediaWriter;
import cool.scx.http.media.byte_array.ByteArrayMediaWriter;
import cool.scx.http.media.byte_input.ByteInputMediaWriter;
import cool.scx.http.media.event_stream.ServerEventStream;
import cool.scx.http.media.event_stream.ServerEventStreamMediaWriter;
import cool.scx.http.media.file.FileMediaWriter;
import cool.scx.http.media.form_params.FormParams;
import cool.scx.http.media.form_params.FormParamsMediaWriter;
import cool.scx.http.media.input_stream.InputStreamMediaWriter;
import cool.scx.http.media.multi_part.MultiPart;
import cool.scx.http.media.multi_part.MultiPartMediaWriter;
import cool.scx.http.media.node.NodeMediaWriter;
import cool.scx.http.media.object.ObjectMediaWriter;
import cool.scx.http.media.string.StringMediaWriter;
import cool.scx.io.ByteInput;
import cool.scx.object.node.Node;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static cool.scx.http.media.empty.EmptyMediaWriter.EMPTY_MEDIA_WRITER;

public interface ScxHttpSender<T> {

    T send(MediaWriter mediaWriter) throws BodyAlreadySentException, HttpSendException;

    //******************** send 操作 *******************

    default T send() throws HttpSendException, BodyAlreadySentException {
        return send(EMPTY_MEDIA_WRITER);
    }

    default T send(ByteInput byteInput) throws BodyAlreadySentException, HttpSendException {
        return send(new ByteInputMediaWriter(byteInput));
    }

    default T send(InputStream inputStream) throws BodyAlreadySentException, HttpSendException {
        return send(new InputStreamMediaWriter(inputStream));
    }

    default T send(byte[] bytes) throws BodyAlreadySentException, HttpSendException {
        return send(new ByteArrayMediaWriter(bytes));
    }

    default T send(String str) throws BodyAlreadySentException, HttpSendException {
        return send(new StringMediaWriter(str));
    }

    default T send(String str, Charset charset) throws BodyAlreadySentException, HttpSendException {
        return send(new StringMediaWriter(str, charset));
    }

    default T send(File file) throws BodyAlreadySentException, HttpSendException {
        return send(new FileMediaWriter(file));
    }

    default T send(File file, long offset, long length) throws BodyAlreadySentException, HttpSendException {
        return send(new FileMediaWriter(file, offset, length));
    }

    default T send(FormParams formParams) throws BodyAlreadySentException, HttpSendException {
        return send(new FormParamsMediaWriter(formParams));
    }

    default T send(MultiPart multiPart) throws BodyAlreadySentException, HttpSendException {
        return send(new MultiPartMediaWriter(multiPart));
    }

    default T send(Node node) throws BodyAlreadySentException, HttpSendException {
        return send(new NodeMediaWriter(node));
    }

    default T send(Object object) throws BodyAlreadySentException, HttpSendException {
        return send(new ObjectMediaWriter(object));
    }

    //理论上只有 服务器才支持发送这种格式
    default ServerEventStream sendEventStream() throws BodyAlreadySentException, HttpSendException {
        var writer = new ServerEventStreamMediaWriter();
        send(writer);
        return writer.eventStream();
    }

    default ScxHttpSender<T> sendGzip() {
        return new GzipHttpSender<>(this);
    }

}
