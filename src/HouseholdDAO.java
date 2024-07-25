import java.sql.SQLException;
import java.util.List;

public interface HouseholdDAO {
    public  int createHousehold(Household household) throws SQLException;
    public Household readHousehold(int householdId) throws SQLException;
    public void updateHousehold(Household household) throws SQLException;
    public void deleteHousehold(int householdId) throws SQLException;
    public List<Household> getAllHouseholds() throws SQLException;

}
