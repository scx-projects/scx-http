package dev.scx.http.media.event_stream;

/// SseEventImpl
///
/// @author scx567888
/// @version 0.0.1
public final class SseEventImpl implements SseEventWritable {

    private String event;
    private String data;
    private String id;
    private Long retry;
    private String comment;

    @Override
    public SseEventWritable event(String event) {
        // event 不允许存在换行
        if (event != null && event.contains("\n")) {
            throw new IllegalArgumentException("SSE event cannot contain \\n : \"" + event + "\"");
        }
        this.event = event;
        return this;
    }

    @Override
    public SseEventWritable data(String data) {
        this.data = data;
        return this;
    }

    @Override
    public SseEventWritable id(String id) {
        // id 不允许存在换行
        if (id != null && id.contains("\n")) {
            throw new IllegalArgumentException("SSE id cannot contain \\n : \"" + id + "\"");
        }
        this.id = id;
        return this;
    }

    @Override
    public SseEventWritable retry(Long retry) {
        this.retry = retry;
        return this;
    }

    @Override
    public SseEventWritable comment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String event() {
        return event;
    }

    @Override
    public String data() {
        return data;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Long retry() {
        return retry;
    }

    @Override
    public String comment() {
        return comment;
    }

}
