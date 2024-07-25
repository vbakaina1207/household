import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {
    public List<Person> getAllPersonsByHousehold(int householdId) throws SQLException;
    public int createPerson(Person person) throws SQLException;

    public Person readPerson(int personId) throws SQLException;
    public void updatePerson(Person person) throws SQLException;
    public void deletePerson(int personId) throws SQLException;

}
