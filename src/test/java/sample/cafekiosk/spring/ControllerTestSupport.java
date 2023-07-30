package sample.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.order.controller.OrderController;
import sample.cafekiosk.spring.api.order.service.OrderService;
import sample.cafekiosk.spring.api.product.controller.ProductController;
import sample.cafekiosk.spring.api.product.service.ProductService;

@WebMvcTest(controllers = {
        ProductController.class,
        OrderController.class
})
public abstract class ControllerTestSupport {

    // 서비스 레이어 하위로 Mock 처리를 할 때 MockMvc 라는 테스트 프레임워크를 사용한다.
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected ProductService productService;

}
