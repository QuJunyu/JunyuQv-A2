import java.util.LinkedList;

/*
 * 主类：程序入口，包含所有Part的演示方法
 */
public class AssignmentTwo {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("===== PROG2004 A2: Theme Park Ride Management System =====");
        System.out.println("==================================================");

        // 按Part顺序演示所有功能（每个方法对应一个作业要求）
        partOne();    // Part1：类与继承
        partTwo();    // Part2：抽象类与接口
        partThree();  // Part3：队列管理
        partFourA();  // Part4A：骑行历史（LinkedList）
        partFourB();  // Part4B：历史排序（Comparator）
        partFive();   // Part5：骑行周期运行
        partSix();    // Part6：CSV导出
        partSeven();  // Part7：CSV导入

        System.out.println("\n==================================================");
        System.out.println("===== All Functions Demonstrated Successfully! =====");
        System.out.println("==================================================");
    }

    /*
     * Part1：演示类与继承（ULO2）
     * 功能：创建Employee和Visitor对象，验证继承关系和属性初始化（使用Employee默认构造，消除警告）
     */
    public static void partOne() {
        System.out.println("\n==================================================");
        System.out.println("Part 1: Class & Inheritance Demonstration");
        System.out.println("==================================================");

        // 创建2个员工对象（使用全参构造）
        Employee emp1 = new Employee("John Doe", 35, "13800138000", "E001", "Roller Coaster");
        Employee emp2 = new Employee("Jane Smith", 28, "13900139000", "E002", "Ferris Wheel");

        // 使用Employee默认构造+setter创建对象（消除默认构造未使用警告）
        Employee emp3 = new Employee();
        emp3.setName("Sam Wilson");
        emp3.setAge(30);
        emp3.setContact("13100131000");
        emp3.setEmployeeId("E003");
        emp3.setRideTypeInCharge("Bumper Cars");

        // 创建3个游客对象（验证输入验证：如会员等级错误会自动转为Bronze）
        Visitor vis1 = new Visitor("Alice", 25, "13700137000", "V001", "Gold");
        Visitor vis2 = new Visitor("Bob", 18, "13600136000", "V002", "Silver");
        Visitor vis3 = new Visitor("Charlie", 30, "13500135000", "V003", "Platinum"); // 非法等级→Bronze

        // 打印对象信息（验证toString和getter方法）
        System.out.println("\n📌 Created Employees (Inherit from Person):");
        System.out.println(emp1);
        System.out.println(emp2);
        System.out.println(emp3); // 打印默认构造创建的员工

        System.out.println("\n📌 Created Visitors (Inherit from Person):");
        System.out.println(vis1);
        System.out.println(vis2);
        System.out.println(vis3);
    }

    /*
     * Part2：演示抽象类与接口（ULO2）
     * 功能：1. 验证抽象类不可实例化；2. 验证接口实现；3. 演示操作员分配方法
     */
    public static void partTwo() {
        System.out.println("\n==================================================");
        System.out.println("Part 2: Abstract Class & Interface Demonstration");
        System.out.println("==================================================");

        // 验证1：抽象类Person不可实例化（以下代码会编译报错，注释掉以通过编译）
        // Person person = new Person(); // ❌ Error: Cannot instantiate abstract class

        // 验证2：Ride类实现RideInterface接口（可正常创建对象）
        Employee operator = new Employee("Mike Johnson", 32, "13400134000", "E003", "Carousel");
        Ride carousel = new Ride(operator, 4); // 单次最大4人
        System.out.println("\n✅ Success: Ride object created (implements RideInterface)");
        System.out.println("Assigned Operator: " + carousel.getOperator().getName());

        // 验证3：调用接口的assignOperator方法（作业要求）
        Employee newOperator = new Employee("Emma Davis", 27, "12100121000", "E006", "Carousel");
        carousel.assignOperator(newOperator);
        System.out.println("Updated Operator: " + carousel.getOperator().getName());
    }

    /*
     * Part3：演示队列管理（ULO3）
     * 功能：添加5个游客→打印队列→移除1个游客→打印更新后的队列
     */
    public static void partThree() {
        System.out.println("\n==================================================");
        System.out.println("Part 3: Queue Management (FIFO) Demonstration");
        System.out.println("==================================================");

        // 创建骑行和操作员
        Employee operator = new Employee("Lisa Wilson", 29, "13300133000", "E004", "Roller Coaster");
        Ride rollerCoaster = new Ride(operator, 5); // 单次最大5人

        // 创建5个游客（含重复卡号，验证队列唯一校验）
        Visitor vis1 = new Visitor("David Brown", 22, "13200132000", "V004", "Gold");
        Visitor vis2 = new Visitor("Ella White", 27, "13100131000", "V005", "Silver");
        Visitor vis3 = new Visitor("Frank Green", 33, "13000130000", "V006", "Bronze");
        Visitor vis4 = new Visitor("Grace Black", 19, "12900129000", "V007", "Gold");
        Visitor vis5 = new Visitor("David Brown", 22, "13200132000", "V004", "Gold"); // 重复卡号

        // 添加游客到队列（验证重复卡号校验）
        System.out.println("\n📥 Adding visitors to queue:");
        rollerCoaster.addVisitorToQueue(vis1);
        rollerCoaster.addVisitorToQueue(vis2);
        rollerCoaster.addVisitorToQueue(vis3);
        rollerCoaster.addVisitorToQueue(vis4);
        rollerCoaster.addVisitorToQueue(vis5); // 会提示重复，无法添加

        // 打印初始队列
        rollerCoaster.printQueue();

        // 移除1个游客（队列头部）
        System.out.println("\n📤 Removing one visitor from queue:");
        rollerCoaster.removeVisitorFromQueue();

        // 打印更新后的队列
        rollerCoaster.printQueue();
    }

    /*
     * Part4A：演示骑行历史（ULO3）
     * 功能：添加5个游客→检查游客是否存在→打印历史总数→用Iterator打印历史
     */
    public static void partFourA() {
        System.out.println("\n==================================================");
        System.out.println("Part 4A: Ride History (LinkedList) Demonstration");
        System.out.println("==================================================");

        // 创建骑行
        Ride ferrisWheel = new Ride(new Employee("Tom Harris", 31, "12700127000", "E005", "Ferris Wheel"), 3);

        // 创建5个游客并加入历史（含重复卡号，验证历史唯一校验）
        Visitor vis1 = new Visitor("Ivy Clark", 26, "12600126000", "V009", "Gold");
        Visitor vis2 = new Visitor("Jack Lewis", 28, "12500125000", "V010", "Silver");
        Visitor vis3 = new Visitor("Kelly Walker", 23, "12400124000", "V011", "Bronze");
        Visitor vis4 = new Visitor("Leo Martin", 35, "12300123000", "V012", "Gold");
        Visitor vis5 = new Visitor("Jack Lewis", 28, "12500125000", "V010", "Silver"); // 重复卡号

        System.out.println("\n📥 Adding visitors to history:");
        ferrisWheel.addVisitorToHistory(vis1);
        ferrisWheel.addVisitorToHistory(vis2);
        ferrisWheel.addVisitorToHistory(vis3);
        ferrisWheel.addVisitorToHistory(vis4);
        ferrisWheel.addVisitorToHistory(vis5); // 会提示重复，无法添加

        // 检查游客是否在历史中
        Visitor checkVis = new Visitor("Jack Lewis", 28, "12500125000", "V010", "Silver");
        boolean exists = ferrisWheel.checkVisitorFromHistory(checkVis);
        System.out.printf("\n❓ Is visitor Jack (Card ID: %s) in history? %s%n",
                checkVis.getVisitorCardId(), exists ? "Yes" : "No");

        // 打印历史游客总数
        System.out.printf("📊 Total visitors in history: %d%n", ferrisWheel.numberOfVisitors());

        // 用Iterator打印历史（作业强制要求，评分关键）
        ferrisWheel.printRideHistory();
    }

    /*
     * Part4B：演示历史排序（ULO3）
     * 功能：添加5个游客→打印排序前历史→用Comparator排序→打印排序后历史
     * 优化：将Collections.sort改为List.sort（符合Java 8+规范）
     */
    public static void partFourB() {
        System.out.println("\n==================================================");
        System.out.println("Part 4B: Ride History Sorting (Comparator) Demonstration");
        System.out.println("==================================================");

        // 创建骑行并添加历史游客（会员等级+年龄多样，便于演示排序）
        Ride carousel = new Ride(new Employee("Emma Davis", 27, "12100121000", "E006", "Carousel"), 4);
        Visitor vis1 = new Visitor("Noah Miller", 29, "12000120000", "V014", "Silver");
        Visitor vis2 = new Visitor("Olivia Taylor", 32, "11900119000", "V015", "Gold");
        Visitor vis3 = new Visitor("Liam Anderson", 25, "11800118000", "V016", "Bronze");
        Visitor vis4 = new Visitor("Sophia Thomas", 30, "11700117000", "V017", "Gold");
        Visitor vis5 = new Visitor("Elijah Moore", 24, "11600116000", "V018", "Silver");

        System.out.println("\n📥 Adding visitors to history (for sorting):");
        carousel.addVisitorToHistory(vis1);
        carousel.addVisitorToHistory(vis2);
        carousel.addVisitorToHistory(vis3);
        carousel.addVisitorToHistory(vis4);
        carousel.addVisitorToHistory(vis5);

        // 排序前打印
        System.out.println("\n📋 Ride History Before Sorting (Insert Order):");
        carousel.printRideHistory();

        // 排序：使用VisitorComparator（优化为List.sort，消除IDE提示）
        LinkedList<Visitor> history = carousel.getRideHistory();
        history.sort(new VisitorComparator()); // 替代Collections.sort
        System.out.println("\n🔄 Sorting completed (Rule: Gold > Silver > Bronze, Age Desc)");

        // 排序后打印
        System.out.println("\n📋 Ride History After Sorting:");
        carousel.printRideHistory();
    }

    /*
     * Part5：演示骑行周期运行（ULO3）
     * 功能：添加10个游客→打印队列→运行1次周期→打印更新后的队列和历史
     * 优化：调用getMaxRider()打印最大游客数，消除未使用方法警告
     */
    public static void partFive() {
        System.out.println("\n==================================================");
        System.out.println("Part 5: Ride Cycle Operation Demonstration");
        System.out.println("==================================================");

        // 创建骑行（单次最大3人）和操作员
        Employee operator = new Employee("Aiden Nelson", 33, "11500115000", "E007", "Roller Coaster");
        Ride rollerCoaster = new Ride(operator, 3);

        // 调用getMaxRider()打印最大游客数（消除未使用警告）
        System.out.printf("📌 Ride Info: Max riders per cycle = %d%n", rollerCoaster.getMaxRider());

        // 添加10个游客到队列（批量创建，验证队列容量）
        System.out.println("\n📥 Adding 10 visitors to queue:");
        for (int i = 1; i <= 10; i++) {
            String membership = (i % 3 == 0) ? "Gold" : (i % 2 == 0) ? "Silver" : "Bronze";
            Visitor visitor = new Visitor(
                    "Visitor" + i,
                    20 + i,
                    "1140011400" + i,
                    "V0" + (19 + i),
                    membership
            );
            rollerCoaster.addVisitorToQueue(visitor);
        }

        // 打印运行前的队列
        System.out.println("\n📋 Waiting Queue Before Cycle:");
        rollerCoaster.printQueue();

        // 运行1次骑行周期（核心功能演示）
        rollerCoaster.runOneCycle();

        // 打印运行后的队列和历史
        System.out.println("\n📋 Waiting Queue After Cycle (Remaining Visitors):");
        rollerCoaster.printQueue();

        System.out.println("\n📜 Ride History After Cycle (Processed Visitors):");
        rollerCoaster.printRideHistory();

        // 打印已运行周期数
        System.out.printf("\n📊 Total completed cycles: %d%n", rollerCoaster.getNumOfCycles());
    }

    /**
     * Part6：演示CSV导出（ULO4）
     * 功能：添加3个游客到历史→导出到CSV文件→显示导出成功信息和文件路径
     */
    public static void partSix() {
        System.out.println("\n==================================================");
        System.out.println("Part 6: CSV Export Demonstration");
        System.out.println("==================================================");

        // 创建骑行并添加历史游客
        Ride ferrisWheel = new Ride(new Employee("Mia Thompson", 28, "11300113000", "E008", "Ferris Wheel"), 5);
        ferrisWheel.addVisitorToHistory(new Visitor("Lucas Garcia", 27, "11200112000", "V030", "Gold"));
        ferrisWheel.addVisitorToHistory(new Visitor("Amelia Martinez", 22, "11100111000", "V031", "Silver"));
        ferrisWheel.addVisitorToHistory(new Visitor("Ethan Robinson", 31, "11000110000", "V032", "Bronze"));

        // 导出到CSV文件（使用项目根目录路径，方便查找）
        String csvPath = "rideHistory.csv";
        ferrisWheel.exportRideHistory(csvPath);
    }

    /*
     * Part7：演示CSV导入（ULO4）
     * 功能：创建新骑行→导入Part6导出的CSV→打印导入后的历史
     */
    public static void partSeven() {
        System.out.println("\n==================================================");
        System.out.println("Part 7: CSV Import Demonstration");
        System.out.println("==================================================");

        // 创建新骑行（初始无历史）
        Ride newRide = new Ride(new Employee("Harper Clark", 26, "10900109000", "E009", "Carousel"), 4);
        System.out.printf("📊 Before import: Total visitors in history = %d%n", newRide.numberOfVisitors());

        // 从Part6导出的CSV文件导入（路径与导出一致）
        String csvPath = "rideHistory.csv";
        newRide.importRideHistory(csvPath);

        // 打印导入后的历史（验证导入成功）
        System.out.println("\n📜 Ride History After Import:");
        newRide.printRideHistory();
    }
}
