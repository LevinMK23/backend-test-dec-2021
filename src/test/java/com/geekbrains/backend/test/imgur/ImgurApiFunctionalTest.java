package com.geekbrains.backend.test.imgur;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiFunctionalTest extends ImgurApiAbstractTest {

    private static String CURRENT_IMAGE_ID;

    @Test
    @Order(1)
    void getAccountBase() {
        String userName = "levinmk23";
        given()
                .spec(requestSpecification)
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
    @Order(2)
    void postImageTest() {
        CURRENT_IMAGE_ID = given()
                .spec(requestSpecification)
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
                .post("upload")
                .body()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @Order(3)
    void deleteImageById() {
        given()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .body("status", is(200))
                .log()
                .all()
                .when()
                .delete("image/" + CURRENT_IMAGE_ID);
    }

    @Test
    void testCreateComment() {
        given()
                .spec(requestSpecification)
                .formParam("image_id", "8xGCvWR")
                .formParam("comment", "Hello world")
                .log()
                .all()
                .expect()
                .body("success", is(true))
                .body("status", is(200))
                .log()
                .all()
                .when()
                .post("comment");
    }
}
