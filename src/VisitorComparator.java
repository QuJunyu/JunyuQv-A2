import java.util.Comparator;

/**
 * 比较器类
 * 设计理由：按"会员等级+年龄"排序，符合主题公园"会员优先、年长优先"的业务逻辑
 * 排序规则：1. 会员等级（Gold > Silver > Bronze）；2. 年龄（降序，年长优先）
 */
public class VisitorComparator implements Comparator<Visitor> {
    /**
     * 会员等级优先级映射：Gold(3) > Silver(2) > Bronze(1)
     * @param level 会员等级
     * @return 优先级数值（用于比较）
     */
    private int getMembershipPriority(String level) {
        return switch (level) {
            case "Gold" -> 3;
            case "Silver" -> 2;
            case "Bronze" -> 1;
            default -> 0;
        };
    }

    /**
     * 比较两个游客：先按会员等级，再按年龄
     * @param v1 第一个游客
     * @param v2 第二个游客
     * @return 正数=v1排在后，负数=v1排在前，0=顺序不变
     */
    @Override
    public int compare(Visitor v1, Visitor v2) {
        // 空值处理（避免NullPointerException）
        if (v1 == null) return 1;
        if (v2 == null) return -1;

        // 第一步：按会员等级排序（优先级高）
        int levelPriorityDiff = getMembershipPriority(v2.getMembershipLevel())
                - getMembershipPriority(v1.getMembershipLevel());
        if (levelPriorityDiff != 0) {
            return levelPriorityDiff; // 等级不同，直接返回结果
        }

        // 第二步：会员等级相同，按年龄降序排序（年长优先）
        return v2.getAge() - v1.getAge();
    }
}
