package org.openindustries.legal.document;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class LegalDocumentResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Master Services Agreement\",\"documentType\":\"CONTRACT\",\"pages\":42}")
                .when().post("/api/v1/documents")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("documentType", is("CONTRACT"))
                .extract().path("id");

        given().when().post("/api/v1/documents/" + id + "/finalize")
                .then().statusCode(200).body("status", is("FINAL"));
    }
}
