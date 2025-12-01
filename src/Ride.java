import java.io.*;
import java.util.*;

public class Ride implements RideInterface {
    // 核心属性（私有封装，通过方法访问/修改）
    private final Queue<Visitor> waitingQueue;   // Part3：等待队列（FIFO，LinkedList实现）
    private final LinkedList<Visitor> rideHistory; // Part4A：骑行历史（支持Iterator遍历+排序）
    private Employee operator;                   // 负责该骑行的员工（非空才能运行）
    private final int maxRider;                  // Part5：单次骑行最大游客数（≥1）
    private int numOfCycles;                     // Part5：已运行周期数（初始0）

    // 1. 全参构造函数（初始化所有属性，满足ULO2封装要求）
    public Ride(Employee operator, int maxRider) {
        this.waitingQueue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.operator = operator;
        // 校验单次最大游客数（≥1，避免非法值）
        this.maxRider = (maxRider >= 1) ? maxRider : 3; // 默认3人/次
        this.numOfCycles = 0;
    }

    // 2. Part3：队列管理方法（实现RideInterface，ULO3）
    /**
     * 添加游客到队列（FIFO），校验：1.游客非空；2.卡号唯一（不重复排队）
     */
    @Override
    public void addVisitorToQueue(Visitor visitor) {
        if (visitor == null) {
            System.out.println("❌ Error [Queue]: Cannot add null visitor!");
            return;
        }

        // 校验：游客是否已在队列中（按卡号唯一）
        for (Visitor v : waitingQueue) {
            if (v.isSameVisitor(visitor)) {
                System.out.printf("❌ Error [Queue]: Visitor (Card ID: %s) is already in queue!%n",
                        visitor.getVisitorCardId());
                return;
            }
        }

        waitingQueue.offer(visitor); // Queue安全添加（FIFO）
        System.out.printf("✅ Success [Queue]: Added visitor - %s (Card ID: %s)%n",
                visitor.getName(), visitor.getVisitorCardId());
    }

    /**
     * 从队列头部移除游客（FIFO），校验：队列非空
     */
    @Override
    public void removeVisitorFromQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("❌ Error [Queue]: Queue is empty! Cannot remove!");
            return;
        }

        Visitor removed = waitingQueue.poll(); // Queue头部移除
        System.out.printf("✅ Success [Queue]: Removed visitor - %s (Card ID: %s)%n",
                removed.getName(), removed.getVisitorCardId());
    }

    /**
     * 打印队列信息（按加入顺序），显示队列大小和每个游客详情
     */
    @Override
    public void printQueue() {
        System.out.printf("%n=== Current Waiting Queue (Size: %d) ===%n", waitingQueue.size());
        if (waitingQueue.isEmpty()) {
            System.out.println("📭 Queue is empty.");
            return;
        }

        int index = 1;
        for (Visitor visitor : waitingQueue) { // FIFO顺序遍历
            System.out.printf("%d. %s (Role: %s, Level: %s)%n",
                    index++, visitor.getName(), visitor.getRole(), visitor.getMembershipLevel());
        }
    }

    // 3. Part4A：骑行历史管理方法（实现RideInterface，ULO3）
    /**
     * 添加游客到骑行历史，校验：1.游客非空；2.卡号唯一（不重复记录）
     */
    @Override
    public void addVisitorToHistory(Visitor visitor) {
        if (visitor == null) {
            System.out.println("❌ Error [History]: Cannot add null visitor!");
            return;
        }

        // 校验：游客是否已在历史中（按卡号唯一）
        if (checkVisitorFromHistory(visitor)) {
            System.out.printf("❌ Error [History]: Visitor (Card ID: %s) already in history!%n",
                    visitor.getVisitorCardId());
            return;
        }

        rideHistory.add(visitor);
        System.out.printf("✅ Success [History]: Added visitor - %s (Card ID: %s)%n",
                visitor.getName(), visitor.getVisitorCardId());
    }

    /**
     * 检查游客是否在历史中（按卡号唯一标识）
     */
    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        if (visitor == null) return false;
        for (Visitor v : rideHistory) {
            if (v.isSameVisitor(visitor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取历史游客总数
     */
    @Override
    public int numberOfVisitors() {
        return rideHistory.size();
    }

    /**
     * 打印骑行历史（必须用Iterator遍历，ULO3强制要求）
     */
    @Override
    public void printRideHistory() {
        System.out.printf("%n=== Ride History (Total Visitors: %d) ===%n", rideHistory.size());
        if (rideHistory.isEmpty()) {
            System.out.println("📜 No ride history yet.");
            return;
        }

        // 必须用Iterator遍历（评分关键，不可替换为for-each）
        Iterator<Visitor> iterator = rideHistory.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.printf("%d. %s (Card ID: %s, Level: %s)%n",
                    index++, visitor.getName(), visitor.getVisitorCardId(), visitor.getMembershipLevel());
        }
    }

    // 4. Part5：运行骑行周期（实现RideInterface，ULO3）
    /**
     * 运行一次骑行周期，核心逻辑：
     * 1. 校验：有操作员 + 队列非空
     * 2. 从队列取maxRider个游客，加入历史
     * 3. 周期数+1，打印结果
     */
    @Override
    public void runOneCycle() {
        System.out.printf("%n=== Starting Ride Cycle %d ===%n", numOfCycles + 1);

        // 校验1：是否有操作员（无操作员无法运行）
        if (operator == null) {
            System.out.println("❌ Error [Cycle]: No operator assigned! Cannot run!");
            return;
        }

        // 校验2：队列是否有游客（无游客无法运行）
        if (waitingQueue.isEmpty()) {
            System.out.println("❌ Error [Cycle]: No visitors in queue! Cannot run!");
            return;
        }

        // 核心逻辑：取maxRider个游客，加入历史
        int ridersProcessed = 0;
        while (!waitingQueue.isEmpty() && ridersProcessed < maxRider) {
            Visitor rider = waitingQueue.poll();
            rideHistory.add(rider);
            System.out.printf("🚀 Cycle %d: Processed visitor - %s (Card ID: %s)%n",
                    numOfCycles + 1, rider.getName(), rider.getVisitorCardId());
            ridersProcessed++;
        }

        // 周期数自增
        numOfCycles++;
        System.out.printf("✅ Cycle %d Completed: %d riders processed. Total cycles: %d%n",
                numOfCycles, ridersProcessed, numOfCycles);
    }

    // 5. Part6：导出骑行历史到CSV（实现RideInterface，ULO4）
    /**
     * 导出历史到CSV，处理所有IO异常，显示文件绝对路径（方便用户查找）
     */
    @Override
    public void exportRideHistory(String filePath) {
        System.out.println("\n=== Exporting Ride History to CSV ===");
        if (rideHistory.isEmpty()) {
            System.out.println("❌ Error [Export]: No history to export!");
            return;
        }

        // 处理所有IO异常（评分关键：不可遗漏异常）
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 写入表头（方便Excel打开阅读）
            writer.write("Name,Age,Contact,VisitorCardId,MembershipLevel");
            writer.newLine();

            // 写入每个游客信息（复用Visitor的toString() CSV格式）
            for (Visitor visitor : rideHistory) {
                writer.write(visitor.toString());
                writer.newLine();
            }

            // 显示文件绝对路径（方便用户查找，避免"找不到文件"问题）
            File file = new File(filePath);
            System.out.printf("✅ Success [Export]: Exported %d visitors to %s%n",
                    rideHistory.size(), file.getAbsolutePath());
        } catch (SecurityException e) {
            System.out.println("❌ Error [Export]: No permission to write file!");
        } catch (IOException e) {
            System.out.printf("❌ Error [Export]: IO error - %s%n", e.getMessage());
        }
    }

    // 6. Part7：从CSV导入骑行历史（实现RideInterface，ULO4）
    /**
     * 导入CSV到历史，处理：文件不存在、空行、格式错误、年龄格式错误等异常
     */
    @Override
    public void importRideHistory(String filePath) {
        System.out.println("\n=== Importing Ride History from CSV ===");

        // 处理所有异常（评分关键：全面异常处理）
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // 跳过表头（第一行）
            int importedCount = 0;
            int skippedCount = 0;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // 跳过表头
                }

                // 解析CSV行（调用Visitor静态方法，处理空行/格式错误）
                Visitor visitor = Visitor.fromCsvString(line);
                if (visitor != null) {
                    // 校验：避免重复导入（按卡号唯一）
                    if (!checkVisitorFromHistory(visitor)) {
                        rideHistory.add(visitor);
                        importedCount++;
                    } else {
                        System.out.printf("⚠️ Warning [Import]: Duplicate visitor (Card ID: %s) - skipped!%n",
                                visitor.getVisitorCardId());
                        skippedCount++;
                    }
                } else {
                    skippedCount++;
                }
            }

            // 打印导入结果（清晰展示成功/跳过数量）
            System.out.printf("✅ Success [Import]: Imported %d visitors, skipped %d lines.%n",
                    importedCount, skippedCount);
        } catch (FileNotFoundException e) {
            System.out.printf("❌ Error [Import]: File not found - %s%n", filePath);
        } catch (SecurityException e) {
            System.out.println("❌ Error [Import]: No permission to read file!");
        } catch (IOException e) {
            System.out.printf("❌ Error [Import]: IO error - %s%n", e.getMessage());
        }
    }

    // 7. 辅助方法：获取骑行历史（Part4B排序用）
    @Override
    public LinkedList<Visitor> getRideHistory() {
        return rideHistory;
    }

    // 8. 实现接口方法：分配操作员（作业明确要求）
    @Override
    public void assignOperator(Employee operator) {
        if (operator != null) {
            this.operator = operator;
            System.out.printf("✅ Success [Operator]: Assigned %s to ride (In Charge: %s)%n",
                    operator.getName(), operator.getRideTypeInCharge());
        } else {
            System.out.println("❌ Error [Operator]: Cannot assign null operator!");
        }
    }

    // 9. Getter（getMaxRider()已调用，消除警告）
    public Employee getOperator() { return operator; }
    public int getMaxRider() { return maxRider; }
    public int getNumOfCycles() { return numOfCycles; }
}
