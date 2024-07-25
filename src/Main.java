import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws SQLException {
        //DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        //Connection connection = databaseConnection.getConnection();

        HouseholdDAO householdDAO = new HouseholdMySQL();
        PersonDAO personDAO = new PersonMySQL();
        PetDAO petDAO = new PetMySQL();

        Household newHousehold = new Household("New Household");
        int idHousehold = householdDAO.createHousehold(newHousehold);
        System.out.println("New household created with ID: " + idHousehold);


        Household retrievedHousehold = householdDAO.readHousehold(idHousehold);
        System.out.println("Retrieved household: " + retrievedHousehold.getName());


        retrievedHousehold.setName("Updated Household");
        householdDAO.updateHousehold(retrievedHousehold);
        System.out.println("Household updated successfully.");

        String householdName = householdDAO.readHousehold(1).getName();
        System.out.println("Household with ID = 1 hat name : " + householdName );

        householdDAO.deleteHousehold(retrievedHousehold.getId());
        System.out.println("Household deleted successfully.");

        List<Household> allHouseholds = householdDAO.getAllHouseholds();
        System.out.println("All households: ");
        for (Household household : allHouseholds) {
            System.out.println(household.getName());
        }

        Person newPerson = new Person("Viktoria", "Smith");
        Random r = new Random();
        int randIndHousehold = r.nextInt(allHouseholds.size());
        Household existingHousehold = householdDAO.readHousehold(randIndHousehold);
        newPerson.setHousehold(existingHousehold);
        if(existingHousehold != null) {
            personDAO.createPerson(newPerson);
            System.out.println("New person created with ID: " + newPerson.getId());

            Person retrievedPerson = personDAO.readPerson(newPerson.getId());
            System.out.println("Retrieved person: " + retrievedPerson.getFirstName() + " " + retrievedPerson.getLastName());

            retrievedPerson.setFirstName("Victoria");
            personDAO.updatePerson(retrievedPerson);
            System.out.println("Person updated successfully.");

            String personFirstName = personDAO.readPerson(1).getFirstName();
            String personLastName = personDAO.readPerson(1).getLastName();
            System.out.println("Person's name with ID = 1 is " + personFirstName + " " + personLastName);

            personDAO.deletePerson(retrievedPerson.getId());
            System.out.println("Person deleted successfully.");
        }
        List<Person> allPersons = personDAO.getAllPersonsByHousehold(1);
        System.out.println("All persons by ID_Household = 1: ");
        for (Person person : allPersons) {
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }

        int randIndPerson = r.nextInt(allPersons.size());
        Person existingPerson = personDAO.readPerson(randIndPerson);
        Pet newPet = new Pet("New Pet", "dog");
        newPet.setPerson(existingPerson);
        if (existingPerson != null) {
            int idNewPet = petDAO.createPet(newPet);
            System.out.println("New pet created with ID: " + idNewPet);


            Pet retrievedPet = petDAO.readPet(idNewPet);
            System.out.println("Retrieved pet: " + retrievedPet.getName());

            retrievedPet.setName("Updated Pet");
            petDAO.updatePet(retrievedPet);
            System.out.println("Pet updated successfully.");

            String petName = petDAO.readPet(1).getName();
            String petType = petDAO.readPet(1).getType();
            System.out.println("Pet with ID = 1 is : " + petType + " " + petName);

            petDAO.deletePet(retrievedPet.getId());
            System.out.println("Pet deleted successfully.");

            List<Pet> allPets = petDAO.getAllPetByPerson(randIndPerson);
            System.out.println("All pets by ID_Person = " + randIndPerson);
            for (Pet pet : allPets) {
                System.out.println(pet.getName() + " " + pet.getType());
            }
        }

    }
}
