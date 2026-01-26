package dev.scx.http.method;

/// HTTP Method (注意: 大小写 敏感)
///
/// 设计说明: 为何该接口被设计为 sealed.
///
/// ScxHttpMethod 本质上是一个 "简单值" (simple value)：
/// 其核心语义仅由一个方法名字符串表达, 接口中不承载额外结构或派生状态.
///
/// 对于这类 "语义可由单一基础值完整表达" 的抽象 (如方法名、状态码、协议版本等),
/// 扩展通常只体现在值本身, 而不体现在新的实现策略或结构形态上.
/// 因此, 将实现类型收敛为 "标准枚举 [HttpMethod] + 通用值实现 [ScxHttpMethodImpl]" 即可覆盖全部合理场景.
///
/// sealed 用于约束实现类型空间, 而非约束取值空间:
/// 新的或非标准方法名仍可通过通用实现表达,
/// 但不会引入新的实现类型, 从而保持类型分支的稳定性与可推理性.
///
/// @author scx567888
/// @version 0.0.1
public sealed interface ScxHttpMethod permits HttpMethod, ScxHttpMethodImpl {

    static ScxHttpMethod of(String method) {
        // 优先使用 HttpMethod
        var m = HttpMethod.find(method);
        return m != null ? m : new ScxHttpMethodImpl(method);
    }

    String value();

}
