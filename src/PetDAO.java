import java.sql.SQLException;
import java.util.List;

public interface PetDAO {
    public int createPet(Pet pet) throws SQLException;
    public Pet readPet(int petId) throws SQLException;
    public void updatePet(Pet pet) throws SQLException;
    public void deletePet(int petId) throws SQLException;
    public List<Pet> getAllPetByPerson(int personId) throws SQLException;
}
