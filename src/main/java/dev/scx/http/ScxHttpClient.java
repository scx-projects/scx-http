package dev.scx.http;

import dev.scx.http.version.HttpVersion;

/// ScxHttpClient
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClient {

    /// 创建一个 请求
    ///
    /// @param httpVersions 支持的 Http 版本 (空列表为自动协商)
    ScxHttpClientRequest request(HttpVersion... httpVersions);

}
