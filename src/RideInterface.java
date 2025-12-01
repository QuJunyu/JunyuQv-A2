import java.util.LinkedList;

/**
 * 接口（ULO2）：统一骑行设施的核心行为规范
 * 设计理由：接口强制所有骑行设施实现相同的核心功能，体现"统一接口、不同实现"的OOP思想
 * 未来新增骑行类型（如"Water Ride"）可直接实现此接口，无需修改现有代码（满足扩展性要求）
 */
public interface RideInterface {
    /**
     * Part3：添加游客到等待队列（FIFO）
     * @param visitor 待加入队列的游客
     */
    void addVisitorToQueue(Visitor visitor);

    /**
     * Part3：从队列头部移除游客（FIFO）
     */
    void removeVisitorFromQueue();

    /**
     * Part3：打印当前等待队列的所有游客信息
     */
    void printQueue();

    /**
     * Part4A：添加游客到骑行历史（已完成骑行）
     * @param visitor 完成骑行的游客
     */
    void addVisitorToHistory(Visitor visitor);

    /**
     * Part4A：检查游客是否在骑行历史中
     * @param visitor 待检查的游客
     * @return true=存在，false=不存在
     */
    boolean checkVisitorFromHistory(Visitor visitor);

    /**
     * Part4A：获取骑行历史中的游客总数
     * @return 游客总数
     */
    int numberOfVisitors();

    /**
     * Part4A：打印骑行历史（必须用Iterator遍历，ULO3要求）
     */
    void printRideHistory();

    /**
     * Part5：运行一次骑行周期
     * 逻辑：从队列取maxRider个游客，加入历史，周期数+1
     */
    void runOneCycle();

    /**
     * Part6：导出骑行历史到CSV文件
     * @param filePath 文件保存路径（相对/绝对路径均可）
     */
    void exportRideHistory(String filePath);

    /**
     * Part7：从CSV文件导入骑行历史
     * @param filePath CSV文件路径
     */
    void importRideHistory(String filePath);

    /**
     * 辅助方法：获取骑行历史列表（Part4B排序用）
     * @return 骑行历史LinkedList
     */
    LinkedList<Visitor> getRideHistory();

    /**
     * 分配操作员（作业要求：含分配操作员方法）
     * @param operator 负责该骑行的员工
     */
    void assignOperator(Employee operator);
}
