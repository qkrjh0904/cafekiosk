package sample.cafekiosk.spring.docs.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sample.cafekiosk.spring.api.product.controller.ProductController;
import sample.cafekiosk.spring.api.product.model.CreateProductRq;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.api.product.service.ProductService;
import sample.cafekiosk.spring.api.product.service.rq.CreateProductServiceRq;
import sample.cafekiosk.spring.docs.RestDocsSupport;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = Mockito.mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);

    }

    @Test
    @DisplayName("신규 상품 등록")
    void createProduct() throws Exception {
        CreateProductRq rq = CreateProductRq.of(HANDMADE, SELLING, "카푸치노", 5000);

        given(productService.createProduct(any(CreateProductServiceRq.class)))
                .willReturn(ProductRs.builder()
                        .id(1L)
                        .productNumber("001")
                        .productType(HANDMADE)
                        .productSellingType(SELLING)
                        .name("아메리카노")
                        .price(4000)
                        .build());

        // perfrom api 쏘는 수행
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(rq))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("product-create",
                        requestFields(
                                fieldWithPath("productType").type(JsonFieldType.STRING)
                                        .description("상품 타입"),
                                fieldWithPath("productSellingType").type(JsonFieldType.STRING)
                                        .description("상품 판매 상태"),
                                fieldWithPath("name").type(JsonFieldType.STRING)
                                        .description("상품 명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),

                                fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                        .description("상품 ID"),
                                fieldWithPath("data.productNumber").type(JsonFieldType.STRING)
                                        .description("상품 번호"),
                                fieldWithPath("data.productType").type(JsonFieldType.STRING)
                                        .description("상품 타입"),
                                fieldWithPath("data.productSellingType").type(JsonFieldType.STRING)
                                        .description("상품 판매 상태"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING)
                                        .description("상품 명"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                        .description("상품 가격")
                        )
                ));
    }


}
