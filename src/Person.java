import java.util.List;

public class Person {
    private  int id;
    private String firstName;
    private String lastName;
    private  Household household;
    private List<Pet> pets;


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName(){
        return  firstName;
    }

    public String getLastName(){

        return  lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Household getHousehold() {
        return household;
    }

    public int getId() {
        return id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

