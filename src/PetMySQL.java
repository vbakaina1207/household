import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PetMySQL implements PetDAO {
    public int createPet(Pet pet) throws SQLException {
        String query = "INSERT INTO pet (name, kind, id_person) VALUES (?, ?, ?)";
        int generatedId = -1;
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query,  Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pet.getName());
            statement.setString(2, pet.getType());
            statement.setInt(3, pet.getPerson().getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Household " + pet.getName() + " created successfully.");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    pet.setId(generatedId);
                    System.out.println("Generated ID: " + generatedId);
                } else {
                    System.out.println("Failed to create pet.");
                }
            }
        }
        return generatedId;
    }

    public Pet readPet(int petId) throws SQLException {
        String query = "SELECT * FROM pet WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, petId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String  type = resultSet.getString("kind");
                    Pet pet = new Pet(name, type);
                    pet.setId(petId);
                    return pet;
                } else {
                    return null;
                }
            }
        }
    }

    public void updatePet(Pet pet) throws SQLException {
        String query = "UPDATE pet SET name = ? WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getId());
            statement.executeUpdate();
        }
    }

    public void deletePet(int petId) throws SQLException {
        String query = "DELETE FROM pet WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, petId);
            statement.executeUpdate();
        }
    }

    public List<Pet> getAllPetByPerson(int personId) throws SQLException {
        String query = "SELECT * FROM pet WHERE id_person = ?";
        List<Pet> pets = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {
            statement.setInt(1, personId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Pet pet = new Pet(resultSet.getString("name"), resultSet.getString("kind"));
                    pet.setId(resultSet.getInt("id"));
                    pets.add(pet);
                }
            }
        }
        return pets;
    }

}
