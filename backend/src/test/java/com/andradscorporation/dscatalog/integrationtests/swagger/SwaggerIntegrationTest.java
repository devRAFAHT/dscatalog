package com.andradscorporation.dscatalog.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import com.andradscorporation.dscatalog.configs.TestsConfigs;
import com.andradscorporation.dscatalog.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void shouldDisplaySwaggerUiPage(){
        var content = given().basePath("/swagger-ui.html").port(TestsConfigs.SERVER_PORT).when().get().then().statusCode(200).extract().body().asString();

        Assertions.assertTrue(content.contains("Swagger UI"));
    }

}
