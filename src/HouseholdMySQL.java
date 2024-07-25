import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HouseholdMySQL implements HouseholdDAO {


    public int createHousehold(Household household) throws SQLException {
        String query = "INSERT INTO household (name) VALUES (?)";
        int generatedId = -1;
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, household.getName());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Household " + household.getName() + " created successfully.");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    household.setId(generatedId);
                    System.out.println("Generated ID: " + generatedId);
                } else {
                    System.out.println("Failed to create household.");
                }
            }

        }
        return  generatedId;
    }
    public Household readHousehold(int householdId) throws SQLException {
        String query = "SELECT * FROM household WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, householdId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    //List<Person> persons = getAllPersonsByHousehold(householdId);
                    Household household = new Household(name);
                    household.setId(householdId);
                    return household;
                } else {
                    return null;
                }
            }
        }
    }

    public void updateHousehold(Household household) throws SQLException {
        String query = "UPDATE household SET name = ? WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, household.getName());
            statement.setInt(2, household.getId());
            statement.executeUpdate();
        }
    }

    public void deleteHousehold(int householdId) throws SQLException {
        String query = "DELETE FROM household WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, householdId);
            statement.executeUpdate();
        }
    }

    public List<Household> getAllHouseholds() throws SQLException {
        List<Household> households = new ArrayList<>();
        String query = "SELECT * FROM household";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    //List<Person> persons = getAllPersonsByHousehold(id);
                    Household household = new Household(name);
                    household.setId(id);
                    households.add(household);
                }
            }
        }
        return households;
    }



}
