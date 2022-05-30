import com.epam.koval.restaurant.database.DBManager;
import com.epam.koval.restaurant.database.DishManager;
import com.epam.koval.restaurant.database.entity.Category;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.exeptions.DBException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DishTest {
    @Mock
    private DataSource ds;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @Mock
    private DBManager dbm;

    private Dish dish;

    @Before
    public void setUp() throws SQLException {
        when(DBManager.getInstance().getConnection()).thenReturn(c);
        when(c.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);

        dish = new Dish(1, "sampleDish", 1, 1, Category.PIZZA, "");

    }

    @Test
    public void getDishByExistingId() throws DBException, SQLException {
        Dish expected = createTestDish();
        mockResultSet(rs, expected);
        when(DBManager.getInstance().getConnection()).thenReturn(c);
        when(c.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        Dish result = DishManager.getDishByID(1);
        assert(Objects.equals(result, expected));
    }

    private static Dish createTestDish(){
        return new Dish(1, "Dish1", 1, 1, Category.PIZZA, "Description1");
    }

    private static void mockResultSet(ResultSet rs, Dish d) throws SQLException {
        when(rs.getInt(1)).thenReturn(d.getId());
        when(rs.getString(2)).thenReturn(d.getName());
        when(rs.getInt(3)).thenReturn(d.getCategory().getId());
        when(rs.getInt(4)).thenReturn(d.getPrice());
        when(rs.getInt(5)).thenReturn(d.getWeight());
        when(rs.getString(6)).thenReturn(d.getDescription());
    }

}
