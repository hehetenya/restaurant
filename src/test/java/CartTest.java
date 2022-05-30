import com.epam.koval.restaurant.database.CartManager;
import com.epam.koval.restaurant.database.DBManager;
import com.epam.koval.restaurant.exeptions.DBException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;


public class CartTest {
    @Mock
    private DBManager dbManager;

    @Mock
    private Connection c;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @Test
    public void addDishToCartTest() throws SQLException{
        try{
            when(dbManager.getConnection()).thenReturn(c);
            when(c.prepareStatement(DBManager.PUT_DISH_INTO_CART)).thenReturn(ps);
            when(ps.executeUpdate()).thenReturn(1);
            CartManager.addDishToCart(2, 1, 1);
        }catch (DBException ex){
            fail();
        }
    }

    @Test
    public void getCartTest(){

    }
}
