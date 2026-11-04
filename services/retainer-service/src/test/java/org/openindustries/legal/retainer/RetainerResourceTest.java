package org.openindustries.legal.retainer;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RetainerResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"matterId\":\"11111111-1111-1111-1111-111111111111\",\"retainerBalanceMinor\":300000}")
                .when().post("/api/v1/retainers")
                .then().statusCode(201).body("retainerBalanceMinor", is(300000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"activityType\":\"DRAFTING\",\"amountMinor\":50000}")
                .when().post("/api/v1/retainers/" + id + "/charges")
                .then().statusCode(200)
                .body("remainingAfter", is(250000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"matterId\":\"11111111-1111-1111-1111-111111111111\",\"retainerBalanceMinor\":300000}")
                .when().post("/api/v1/retainers")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"activityType\":\"DRAFTING\",\"amountMinor\":900000}")
                .when().post("/api/v1/retainers/" + id + "/charges")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/retainers/" + id)
                .then().statusCode(200).body("retainerBalanceMinor", is(300000));
    }
}
