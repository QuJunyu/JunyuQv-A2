public abstract class Person {
    // 3个共同实例变量（私有封装，通过getter/setter访问）
    private String name;    // 姓名（非空）
    private int age;        // 年龄（1-120岁）
    private String contact; // 联系方式（非空）

    // 1. 默认构造函数（满足JavaBean规范）
    public Person() {}

    // 2. 全参构造函数（初始化所有父类属性，供子类调用）
    public Person(String name, int age, String contact) {
        this.setName(name);     // 调用setter，复用输入验证逻辑
        this.setAge(age);
        this.setContact(contact);
    }

    // 3. 抽象方法：强制子类实现"获取角色"（员工/游客），体现抽象类的规范作用
    public abstract String getRole();

    // 4. 所有变量的Getter/Setter（含严格输入验证，避免非法数据）
    public String getName() { return name; }
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        } else {
            System.out.println("❌ Error [Person]: Name cannot be empty!");
        }
    }

    public int getAge() { return age; }
    public void setAge(int age) {
        if (age > 0 && age <= 120) {
            this.age = age;
        } else {
            System.out.println("❌ Error [Person]: Age must be 1-120!");
        }
    }

    public String getContact() { return contact; }
    public void setContact(String contact) {
        if (contact != null && !contact.trim().isEmpty()) {
            this.contact = contact.trim();
        } else {
            System.out.println("❌ Error [Person]: Contact cannot be empty!");
        }
    }
}
