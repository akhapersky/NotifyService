package objects;

import java.util.Date;

public class Person {
    private int age;
    private String firstName;
    private String secondName;
    private Date birthDay;
    private String info;
    private String lastName;
    private Event event;


    public Person(String firstName, String secondName, String lastName, Date birthDay, String info, Event event) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthDay = birthDay;
        this.lastName = lastName;
        this.info = info;
        this.event = event;
        System.out.println("NEW PERSON BUILT!!!------------------------------>>>>");
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Event getEvent() {
        return event;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getInfo() {
        return info;
    }
}
