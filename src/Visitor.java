public class Visitor extends Person {
    // 2个游客专属变量（私有封装）
    private String visitorCardId;   // 游客卡号（唯一标识，非空）
    private String membershipLevel; // 会员等级（仅支持Gold/Silver/Bronze）

    // 1. 默认构造函数
    public Visitor() {}

    // 2. 全参构造函数（初始化父类+子类属性，满足ULO2继承要求）
    public Visitor(String name, int age, String contact, String visitorCardId, String membershipLevel) {
        super(name, age, contact); // 调用父类全参构造
        this.setVisitorCardId(visitorCardId);
        this.setMembershipLevel(membershipLevel);
    }

    // 3. 实现父类抽象方法：明确角色为"Visitor"
    @Override
    public String getRole() {
        return "Visitor";
    }

    // 4. 所有变量的Getter/Setter（含严格输入验证，避免非法数据）
    public String getVisitorCardId() { return visitorCardId; }
    public void setVisitorCardId(String visitorCardId) {
        if (visitorCardId != null && !visitorCardId.trim().isEmpty()) { // 非空校验
            this.visitorCardId = visitorCardId.trim();
        } else {
            System.out.println("❌ Error [Visitor]: Card ID cannot be empty!");
        }
    }

    public String getMembershipLevel() { return membershipLevel; }
    public void setMembershipLevel(String membershipLevel) {
        // 仅允许3种会员等级（严格校验，避免非法值）
        if (membershipLevel != null && (membershipLevel.equalsIgnoreCase("Gold")
                || membershipLevel.equalsIgnoreCase("Silver")
                || membershipLevel.equalsIgnoreCase("Bronze"))) {
            this.membershipLevel = membershipLevel.substring(0,1).toUpperCase() + membershipLevel.substring(1).toLowerCase();
        } else {
            System.out.println("❌ Error [Visitor]: Level must be Gold/Silver/Bronze!");
            this.membershipLevel = "Bronze"; // 默认青铜会员
        }
    }

    // 5. 重写toString：CSV格式（Part6导出依赖，格式：姓名,年龄,联系方式,卡号,会员等级）
    @Override
    public String toString() {
        return String.format("%s,%d,%s,%s,%s",
                getName(), getAge(), getContact(), visitorCardId, membershipLevel);
    }

    // 6. 静态方法：从CSV字符串解析为Visitor对象（Part7导入依赖）
    public static Visitor fromCsvString(String csvLine) {
        // 处理空行（避免解析报错）
        if (csvLine == null || csvLine.trim().isEmpty()) {
            System.out.println("⚠️ Warning [CSV Import]: Skipping empty line!");
            return null;
        }

        String[] parts = csvLine.split(",");
        // 校验CSV格式（必须5列）
        if (parts.length != 5) {
            System.out.printf("⚠️ Warning [CSV Import]: Invalid format - '%s' (must have 5 columns)!%n", csvLine);
            return null;
        }

        try {
            Visitor visitor = new Visitor();
            visitor.setName(parts[0].trim());
            visitor.setAge(Integer.parseInt(parts[1].trim())); // 年龄转整数（处理异常）
            visitor.setContact(parts[2].trim());
            visitor.setVisitorCardId(parts[3].trim());
            visitor.setMembershipLevel(parts[4].trim());
            return visitor;
        } catch (NumberFormatException e) {
            System.out.printf("⚠️ Warning [CSV Import]: Invalid age in line - '%s'!%n", csvLine);
            return null;
        }
    }

    // 7. 辅助方法：判断两个游客是否为同一人（按卡号唯一标识）
    public boolean isSameVisitor(Visitor other) {
        return other != null && this.visitorCardId.equals(other.getVisitorCardId());
    }
}
