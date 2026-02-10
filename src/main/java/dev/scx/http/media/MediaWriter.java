package dev.scx.http.media;

import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.sender.ScxHttpSender;

/// 媒体写入器 一般用于 [dev.scx.http.sender.ScxHttpMediaSender]
///
/// @author scx567888
/// @version 0.0.1
public interface MediaWriter extends ScxHttpSender.BodyWriter {

    /// 媒体类型, 允许为空, 表示不提供 媒体类型
    ScxMediaType mediaType();

}
