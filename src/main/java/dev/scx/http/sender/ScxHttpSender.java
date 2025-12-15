package dev.scx.http.sender;

import dev.scx.http.media.MediaWriter;
import dev.scx.http.media.byte_array.ByteArrayMediaWriter;
import dev.scx.http.media.byte_input.ByteInputMediaWriter;
import dev.scx.http.media.event_stream.ServerEventStream;
import dev.scx.http.media.event_stream.ServerEventStreamMediaWriter;
import dev.scx.http.media.file.FileMediaWriter;
import dev.scx.http.media.form_params.FormParams;
import dev.scx.http.media.form_params.FormParamsMediaWriter;
import dev.scx.http.media.input_stream.InputStreamMediaWriter;
import dev.scx.http.media.multi_part.MultiPart;
import dev.scx.http.media.multi_part.MultiPartMediaWriter;
import dev.scx.http.media.node.NodeMediaWriter;
import dev.scx.http.media.object.ObjectMediaWriter;
import dev.scx.http.media.string.StringMediaWriter;
import dev.scx.io.ByteInput;
import dev.scx.node.Node;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import static dev.scx.http.media.empty.EmptyMediaWriter.EMPTY_MEDIA_WRITER;

/// ScxHttpSender
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpSender<T> {

    T send(MediaWriter mediaWriter) throws IllegalSenderStateException, HttpSendException;

    ScxHttpSenderStatus senderStatus();

    //******************** send 操作 *******************

    default T send() throws IllegalSenderStateException, HttpSendException {
        return send(EMPTY_MEDIA_WRITER);
    }

    default T send(ByteInput byteInput) throws IllegalSenderStateException, HttpSendException {
        return send(new ByteInputMediaWriter(byteInput));
    }

    default T send(InputStream inputStream) throws IllegalSenderStateException, HttpSendException {
        return send(new InputStreamMediaWriter(inputStream));
    }

    default T send(byte[] bytes) throws IllegalSenderStateException, HttpSendException {
        return send(new ByteArrayMediaWriter(bytes));
    }

    default T send(String str) throws IllegalSenderStateException, HttpSendException {
        return send(new StringMediaWriter(str));
    }

    default T send(String str, Charset charset) throws IllegalSenderStateException, HttpSendException {
        return send(new StringMediaWriter(str, charset));
    }

    default T send(File file) throws IllegalSenderStateException, HttpSendException {
        return send(new FileMediaWriter(file));
    }

    default T send(File file, long offset, long length) throws IllegalSenderStateException, HttpSendException {
        return send(new FileMediaWriter(file, offset, length));
    }

    default T send(FormParams formParams) throws IllegalSenderStateException, HttpSendException {
        return send(new FormParamsMediaWriter(formParams));
    }

    default T send(MultiPart multiPart) throws IllegalSenderStateException, HttpSendException {
        return send(new MultiPartMediaWriter(multiPart));
    }

    default T send(Node node) throws IllegalSenderStateException, HttpSendException {
        return send(new NodeMediaWriter(node));
    }

    default T send(Object object) throws IllegalSenderStateException, HttpSendException {
        return send(new ObjectMediaWriter(object));
    }

    //理论上只有 服务器才支持发送这种格式
    default ServerEventStream sendEventStream() throws IllegalSenderStateException, HttpSendException {
        var writer = new ServerEventStreamMediaWriter();
        send(writer);
        return writer.eventStream();
    }

    default ScxHttpSender<T> sendGzip() {
        return new GzipHttpSender<>(this);
    }

}
