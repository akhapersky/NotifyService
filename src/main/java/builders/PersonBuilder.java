package builders;

import objects.Event;
import objects.Person;

import java.util.Date;

public class PersonBuilder {
    private int age;
    private String firstName;
    private String secondName;
    private Date birthDay;
    private String info;
    private String lastName;
    private Event event;

    public PersonBuilder setFirstName(String firstName){
        this.firstName=firstName;
        return this;
    }

    public PersonBuilder setEvent(Event event){
        this.event=event;
        return this;
    }

    public PersonBuilder setSecondName(String secondName){
        this.secondName=secondName;
        return this;
    }

    public PersonBuilder setLastName(String lastName){
        this.lastName=lastName;
        return this;
    }

    public PersonBuilder setBirthDay(Date date){
        this.birthDay=date;
        return this;
    }

    public Person build(){
        return new Person(firstName,secondName,lastName,birthDay, info, event);
    }
}
