import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonMySQL implements PersonDAO {

    public List<Person> getAllPersonsByHousehold(int householdId) throws SQLException {
        String query = "SELECT * FROM person WHERE id_household = ?";
        List<Person> persons = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, householdId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person(resultSet.getString("first_name"), resultSet.getString("last_name"));
                    person.setId(resultSet.getInt("id"));
                    persons.add(person);
                }
            }
        }
        return persons;
    }
    public int createPerson(Person person) throws SQLException {
        String query = "INSERT INTO person (first_name, last_name, id_household) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getHousehold().getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Household " + person.getFirstName() + " " + person.getLastName() + " created successfully.");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    person.setId(generatedId);
                    System.out.println("Generated ID: " + generatedId);
                } else {
                    System.out.println("Failed to create person.");
                }
            }
        }
        return generatedId;
    }

    public Person readPerson(int personId) throws SQLException {
        String query = "SELECT * FROM person WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, personId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Person person = new Person(firstName, lastName);
                    person.setId(personId);
                    return person;
                } else {
                    return null;
                }
            }
        }
    }

    public void updatePerson(Person person) throws SQLException {
        String query = "UPDATE person SET first_name = ?, last_name = ? WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getId());
            statement.executeUpdate();
        }
    }

    public void deletePerson(int personId) throws SQLException {
        String query = "DELETE FROM person WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, personId);
            statement.executeUpdate();
        }
    }

}
