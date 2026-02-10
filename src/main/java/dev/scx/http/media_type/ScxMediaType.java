package dev.scx.http.media_type;

import dev.scx.http.parameters.Parameters;

import java.nio.charset.Charset;

/// ScxMediaType
///
/// ScxMediaType 表达的是 media type 的结构事实 (type/subtype + params),
/// 其取值空间在协议与生态层面均为开放集合(IANA/vendor/私有扩展).
///
/// 与 [dev.scx.http.method.ScxHttpMethod] 等 "简单值" 抽象不同,
/// MediaType 不仅包含字符串值, 还承载结构与参数语义,
/// 且常见实现策略存在合理差异(enum/record/惰性解析等).
///
/// 因此该接口保持开放, 不设计为 sealed;
/// 库仅提供 常用枚举: [MediaType] 与 默认实现: [ScxMediaTypeImpl], 用户可自行实现以承载自定义类型.
///
/// > 常见问题: 为什么没有 ContentType 抽象 ?
/// > 首先要明确一个概念 Content-Type 在 HTTP 中只是一个 header 名称, 其值语义是借用了 media type.
/// > 因此 我们只对 MediaType 建模, 因为从本质上来看 并不存在一个叫做 ContentType 的抽象.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxMediaType {

    static ScxMediaTypeWritable of(String type, String subtype) {
        return new ScxMediaTypeImpl(type, subtype);
    }

    /// 从一个现有的 ScxMediaType 创建
    static ScxMediaTypeWritable of(ScxMediaType oldMediaType) {
        return new ScxMediaTypeImpl(oldMediaType.type(), oldMediaType.subtype()).params(oldMediaType.params());
    }

    /// 从一个 mediaType 格式的字符串 创建
    static ScxMediaTypeWritable parse(String mediaTypeStr) throws IllegalArgumentException {
        return ScxMediaTypeHelper.parseMediaType(mediaTypeStr);
    }

    String type();

    String subtype();

    Parameters<String, String> params();

    default boolean equalsIgnoreParams(ScxMediaType other) {
        if (other == null) {
            return false;
        }
        return type().equalsIgnoreCase(other.type()) && subtype().equalsIgnoreCase(other.subtype());
    }

    default String encode() {
        return ScxMediaTypeHelper.encodeMediaType(this);
    }

    default Charset charset() {
        var charset = params().get("charset");
        return charset == null ? null : Charset.forName(charset);
    }

    default String boundary() {
        return params().get("boundary");
    }

}
