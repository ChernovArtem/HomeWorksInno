package part7.lesson23.service.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import part6.lesson22.pojo.Product;
import part6.lesson22.pojo.User;
import part6.lesson22.service.ProductService;
import part6.lesson22.service.UserService;
import part6.lesson22.service.utils.OrderUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUtilsTest {

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @InjectMocks
    private OrderUtils orderUtils = new OrderUtils();


    @Test
    void getProductById() {
        Product product = new Product("A", "B", "C", 100500);

        when(productService.getProductById(1)).thenReturn(product);
        assertEquals(product, orderUtils.getProductById(1));
    }

    @Test
    void getUserById() {
        User user = new User(1, "A", "B", "C", "D");

        when(userService.getUserById(1)).thenReturn(user);
        assertEquals(user, orderUtils.getUserById(1));
    }
}