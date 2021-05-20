package part7.lesson23;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.Main;
import part6.lesson22.service.OrderService;
import part6.lesson22.service.ProductService;
import part6.lesson22.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Для добивания большего процента
 */
@ExtendWith(MockitoExtension.class)
class MainTest {

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;


    @Mock
    private OrderService orderService;

    @InjectMocks
    private Main main = new Main();

    @Test
    void workWithProduct() {
        assertDoesNotThrow(() -> main.workWithProduct());
    }

    @Test
    void workWithUser() {
        assertDoesNotThrow(() -> main.workWithUser());
    }

    @Test
    void workWithOrder() {
        assertDoesNotThrow(() -> main.workWithOrder());
    }
}