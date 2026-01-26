package work1.task6;
public class Person {
    private final String name;
    private final int age;
    private final String profession;
    private final boolean active;

    public Person(String name, int age, String profession, boolean active) {
        this.name = name;
        this.age = age;
        this.profession = profession;
        this.active = active;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getProfession() { return profession; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Person{name='%s', age=%d, profession='%s', active=%s}".formatted(name, age, profession, active);
    }
}