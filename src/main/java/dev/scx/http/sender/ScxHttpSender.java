package dev.scx.http.sender;

import dev.scx.exception.ScxWrappedException;
import dev.scx.http.headers.ScxHttpHeadersWritable;
import dev.scx.http.received.ScxHttpReceived;
import dev.scx.io.ByteOutput;

/// 本接口用于表达:
/// HTTP 输出端(发送端) 在协议层面的最小, 语义完备, 且不可逆的抽象. 表示 一次 HTTP 发送行为本身.
///
/// ScxHttpSender 的抽象层级与 [ScxHttpReceived] 完全等价.
/// 此接口设计并非经验性选择, 也非风格偏好,
/// 而是在给定约束下, 对所有合理建模路径进行系统性枚举, 退化实验与反证分析后, 所能得到的 "不可再削减解".
///
/// # 1. 设计目标.
///
/// ScxHttpSender 表达的是一次 "协议提交动作(protocol commit)":
/// - headers 与 body 必须作为整体被提交.
/// - 提交会改变连接与协议状态.
/// - 提交具有不可逆性 (只能发送一次).
///
/// 因此:
///
/// - ScxHttpSender 不是纯值对象.
/// - 不是输出流, 也不是上下文或交换器
/// - 发送前可以配置 headers 或其他提交数据, 但发送动作本身是原子且不可逆的.
/// - send(...) 在语义上等价于事务系统中的 commit.
///
/// # 2. 为什么与 [ScxHttpReceived]  "形式" 上不对称.
///
/// ScxHttpReceived 是一个 "被动存在" 的抽象:
/// - headers 已经存在
/// - body 已经存在
/// - 读取不会改变协议状态
///
/// HTTP 输出端本质上不是值, 而是一次 "协议提交动作" :
/// 因此:
///   输入端 = 观测型 / pull / 无副作用
///   输出端 = 行为型 / push / 有副作用
/// 强行追求形式上对称, 只会隐藏或延迟副作用, 从而引入更大的设计不确定性.
/// 而事实上 这套接口与 ScxHttpReceived 其实已经形成了一种更本质的 "因果对称" .
///
/// # 3. 为什么命名为 ScxHttpSender?
///
/// Sender 表达的是 "发送权与发送责任的拥有者", 而非数据或工具:
/// - 拥有发送时机的决定权.
/// - 对 framing / flush / backpressure 的实现语义负责.
/// - send(...) 调用后, 其生命周期即告终结.
///
/// Message / Output / Writer 等命名,  都会弱化或隐藏这种动作所有权,  从而不可避免地引入隐式状态或副作用.
///
/// # 4. 为什么输出端必须是 push(ByteOutput)
///
/// 在 HTTP 中, 发送方天然拥有写入主动控制权.
/// 使用 pull(ByteInput)建模输出端:
/// - 1, 丢失发送方主动权, 写入时机被动化.
/// - 2, 某些功能 会迫使用户必须引入管道, 线程或缓冲结构来解决.
/// - 3, ByteInput 生命周期不明确.
///
/// 因此, 输出端只能合理的被建模为: "一个消费 ByteOutput 的写入策略".
///
/// # 5. 为什么 send(...) 是唯一合理的 IO 触发点.
///
/// 所有真实 IO 行为必须在一个明确, 原子的时间点发生.
///
/// 以下方案已被系统性否定:
///
/// - body() 调用即触发 IO -> 生命周期模糊.
/// - 第一次 write() 触发 IO -> 隐式副作用.
/// - close() / finish() 触发 IO -> 所有权不清.
///
/// send(...) 是唯一同时满足以下条件的操作:
///
/// - headers + body 原子提交.
/// - 明确的协议边界.
/// - 成功 / 失败可区分 (返回值 / 异常).
/// - 可自然表达 "只能调用一次".
///
/// # 6. 为什么 BodyWriter 只能作为 send(...) 的参数
///
/// BodyWriter 表达的是 "写入策略" , 而不是值.
/// 将 BodyWriter 作为 send(...) 的参数. 意味着:
/// - 不存在 "body 已设置但尚未发送" 的中间态.
/// - 不需要定义 body 覆盖 / 重置规则.
/// - BodyWriter 的生命周期被严格限制在 send 调用栈内.
///
/// 任何将 BodyWriter 存入对象的设计, 都不可避免地引入一个额外的可变状态阶段, 从而扩大状态空间并降低抽象稳定性.
///
/// # 7. 已被系统性否定的设计路径(失败示例)
///
/// 以下设计并非遗漏, 而是已被明确验证为必然退化:
///
/// - 纯值对象 + 外部 sender
///     - 协议提交被伪装为数据传递
///     - headers / body 一致性无法保证
///     - IO 触发点不明确
///
/// - 对称模型: body() 返回 ByteOutput
///     - IO 触发点不明确
///     - send 退化为隐式行为
///
/// - pull 输出(ByteInput 作为 body)
///     - 发送权丧失
///     - 必须引入缓冲或线程
///
/// - 首次 write() 触发 IO
///     - 隐式副作用
///     - headers / body 原子性破坏
///
/// - close() / finish() 作为提交点
///     - close 语义被污染
///     - 所有权与异常边界不清
///
/// - Exchange / Context 聚合
///     - 生命周期被强行耦合
///     - 状态机集中化并膨胀
///
/// - 尝试让输出端"看起来更对称"
///     - 混淆协议事实与协议动作
///     - 不可逆性无法在类型层面体现
///
/// 所有未被采用的方案, 都会至少触发以下失败模式之一:
/// - 推迟 IO 触发点 → 隐式副作用
/// - 对称建模输入 / 输出 → 因果关系丢失
/// - 使用 pull 输出 → 发送权错位
/// 当前设计的意义在于:
/// 在保持协议表达能力不变的前提下,
/// 以上失败模式均被系统性消除.
///
/// # 8. 关于不可再削减性(Design Closure)
///
/// 当前设计具有:
/// - 唯一动作: send(...).
/// - 唯一生命周期跃迁: 未发送 → 已终结.
/// - 无可变执行策略存储.
/// - 无显式状态机暴露.
/// - 无隐式 IO 行为.
///
/// 在保持表达能力不变的前提下:
/// - 任何改动都会增加状态
/// - 或引入歧义
/// - 或削弱协议表达能力
///
/// 因此, 在既定约束下:  本设计是语义完备, 状态最小, 不可安全改动的解.
///
/// 如果你再次产生以下念头:
///
/// - 是否可以设计成纯值 ?
/// - 是否可以让它看起来更对称 ?
/// - 是否可以提前设置 body ?
/// - 是否可以拆分 send 的职责 ?
/// - 是否可以用 ByteInput 表达输出 ?
/// - 是否可以将 bodyLength 方法移动到 ScxHttpSender 中 ?
///
/// 那么你大概率不是在发现新的改进, 而是在重新引入已被验证的退化方案.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpSender<T> {

    /// 一个 "可写" 的头.
    ScxHttpHeadersWritable headers();

    /// 提交一次 HTTP 发送动作, 并返回本次提交所产生的结果.
    ///
    /// send(...) 在语义上等价于一次协议级的 `commit` (事务提交):
    /// headers 与 body 作为一个不可分割的整体被提交,
    /// 调用一旦开始即不可逆, 其生命周期在本次调用结束时终结.
    ///
    /// ### 异常语义 (异常域严格区分)
    ///
    /// send(...) 可能抛出的异常被严格划分为以下四个互斥的异常域,
    /// 每一类异常都对应着一次发送流程中 "不同阶段, 不同责任方 的失败:
    ///
    /// - [IllegalSenderStateException] (状态异常):
    ///
    ///   表示 send(...) 在非法的 Sender 状态下被调用,
    ///   例如: 正在发送中, 已经发送完成( 无论成功或失败)等.
    ///   此类异常在发送流程开始之前即被检测, 与 IO, 用户逻辑或远端无关.
    ///
    /// - [ScxHttpSendException] (发送异常 / 协议提交异常):
    ///
    ///   表示在 HTTP 协议提交或写出过程中发生的异常,
    ///   包括但不限于:
    ///    - 建立或使用底层连接失败
    ///    - 发送 headers 失败
    ///    - 协议 framing / chunk 编码失败
    ///    - flush / socket 写入失败
    ///
    ///  该异常表示: 发送方试图将请求提交给对端, 但未能成功完成协议级写出.
    ///
    ///  注意: BodyWriter 自身抛出的异常不属于此异常域.
    ///
    /// - [ScxWrappedException] (用户异常 / 回调异常):
    ///
    ///   表示在执行 [BodyWriter#write(ByteOutput)] 时, 由用户提供的写入策略抛出的异常.
    ///
    ///   所有来自 BodyWriter 的异常, 无论原始类型为何, 都会被统一包装为 `ScxWrappedException`,
    ///   以明确区分 用户异常域 与 宿主异常域.
    ///
    /// - [ScxHttpReceiveException] (接收异常):
    ///
    ///   表示 send(...) 已成功完成协议提交, 但在 send 内部选择等待, 读取或解析对端响应时发生的异常.
    ///   该异常仅在具体实现选择在 send(...) 调用内完成接收流程时才可能出现.
    ///
    /// 以上四类异常在语义上互斥, 且能够稳定反映失败发生的阶段与责任边界,
    /// 以支持可靠的错误处理, 日志归因, 重试策略与协议层决策.
    ///
    /// @param bodyWriter 用于将 body 写入输出端的写入策略
    /// @return 本次发送行为所产生的结果
    /// @throws IllegalSenderStateException Sender 状态错误
    /// @throws ScxHttpSendException        协议提交/写出阶段的发送异常
    /// @throws ScxWrappedException         BodyWriter 执行过程中抛出的用户异常
    /// @throws ScxHttpReceiveException     send 内部接收/解析结果时发生的异常 (如果存在这一阶段的话)
    T send(BodyWriter bodyWriter) throws IllegalSenderStateException, ScxHttpSendException, ScxWrappedException, ScxHttpReceiveException;

    /// body 写入器
    interface BodyWriter {

        /// body 的实际字节长度 (如果已知).
        ///
        /// - null : 未知的 body 总字节长度.
        /// - 大于等于 0 : 已知的 body 总字节长度.
        default Long bodyLength() {
            return null;
        }

        /// 用于将 body 写入到提供的 ByteOutput.
        ///
        /// 约定:
        /// - write(...) 表达的是一次写入策略的执行.
        /// - 写入完成后, 实现必须关闭 byteOutput.
        void write(ByteOutput byteOutput) throws Throwable;

    }

}
