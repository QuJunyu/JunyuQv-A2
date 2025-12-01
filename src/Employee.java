public class Employee extends Person {
    // 2个员工专属变量（私有封装）
    private String employeeId;        // 员工唯一ID（字母+数字，非空）
    private String rideTypeInCharge;  // 负责的骑行类型（如"Roller Coaster"，非空）

    // 1. 默认构造函数（已使用，消除警告）
    public Employee() {}

    // 2. 全参构造函数（初始化父类+子类属性，满足ULO2继承要求）
    public Employee(String name, int age, String contact, String employeeId, String rideTypeInCharge) {
        super(name, age, contact); // 调用父类全参构造，复用父类属性初始化
        this.setEmployeeId(employeeId);
        this.setRideTypeInCharge(rideTypeInCharge);
    }

    // 3. 实现父类抽象方法：明确角色为"Employee"
    @Override
    public String getRole() {
        return "Employee";
    }

    // 4. 所有变量的Getter/Setter（核心修改：放宽员工ID校验规则）
    public String getEmployeeId() { return employeeId; } // 已调用，消除警告
    public void setEmployeeId(String employeeId) {
        // 修正正则：允许以字母开头，后接数字（支持E001、EMP002等格式）
        if (employeeId != null && employeeId.matches("^[A-Za-z]+\\d+$")) {
            this.employeeId = employeeId;
        } else {
            System.out.println("❌ Error [Employee]: ID must start with letters and end with numbers (e.g., E001)!");
        }
    }

    public String getRideTypeInCharge() { return rideTypeInCharge; }
    public void setRideTypeInCharge(String rideTypeInCharge) {
        if (rideTypeInCharge != null && !rideTypeInCharge.trim().isEmpty()) { // 非空校验
            this.rideTypeInCharge = rideTypeInCharge.trim();
        } else {
            System.out.println("❌ Error [Employee]: Ride type in charge cannot be empty!");
        }
    }

    // 重写toString：调用getEmployeeId()，消除未使用方法警告
    @Override
    public String toString() {
        return String.format("Employee [ID: %s, Name: %s, Age: %d, Contact: %s, In Charge: %s, Role: %s]",
                getEmployeeId(), getName(), getAge(), getContact(), rideTypeInCharge, getRole());
    }
}
