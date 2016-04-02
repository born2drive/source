package swip.ch16table.domain;

public class Person {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(int id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "new Person(\"" + id +
                "\",\"" + firstName + "\",\"" +
                lastName + "\"," + age + ")\n";
    }
}