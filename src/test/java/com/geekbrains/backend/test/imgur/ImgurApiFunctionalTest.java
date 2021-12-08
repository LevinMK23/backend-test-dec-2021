package com.geekbrains.backend.test.imgur;

import java.util.Properties;

import com.geekbrains.backend.test.FunctionalTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ImgurApiFunctionalTest extends FunctionalTest {


    private static Properties properties;
    private static String TOKEN;

    @BeforeAll
    static void beforeAll() throws Exception {
        properties = readProperties();
        RestAssured.baseURI = properties.getProperty("imgur-api-url");
        TOKEN = properties.getProperty("imgur-api-token");
    }

    @Test
    void getAccountBase() {
        String userName = "levinmk23";
        given()
                .auth()
                .oauth2(TOKEN)
                .log()
                .all()
                .expect()
                .body("data.id", is(153514053))
                .log()
                .all()
                .when()
                .get("account/" + userName);
    }

    @Test
    void postImageTest() {
        given()
                .auth()
                .oauth2(TOKEN)
                .multiPart("image", getFileResource("img.jpg"))
                .formParam("name", "Picture")
                .formParam("title", "The best picture!")
                .log()
                .all()
                .expect()
                .body("data.size", is(46314))
                .body("data.type", is("image/jpeg"))
                .body("data.name", is("Picture"))
                .body("data.title", is("The best picture!"))
                .log()
                .all()
                .when()
                .post("upload");
    }

    // TODO: 08.12.2021 Домашка протестировать через RA минимум 5 различных end point-ов
}
