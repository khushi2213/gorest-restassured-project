package com.gorest.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasEntry;

/*
1. Verify the if the total record is 20
2. Verify the if the name of id = 5487 is equal to ”Hamsini Trivedi”
3. Check the single ‘Name’ in the Array list (Subhashini Talwar)
4. Check the multiple ‘Names’ in the ArrayList (Mrs. Menaka Bharadwaj, Msgr. Bodhan
Guha, Karthik Dubashi IV)
5. Verify the email of userid = 5471 is equal “adiga_aanjaneya_rep@jast.org”
6. Verify the status is “Active” of username is “Shanti Bhat V”
7. Verify the Gender = male of username is “Niro Prajapat”
 */
public class UserAssertionTest {
    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.basePath = "/public/v2";

        response = given()
                .queryParam("page", 1)
                .queryParam("per_page", 20)
                .when()
                .get("/users")
                .then().statusCode(200);

    }

    //1. Verify the if the total record is 20
    @Test
    public void test001() {
        response.body("size", equalTo(20));
    }

    //2. Verify the if the name of id = 5487 is equal to ”Hamsini Trivedi”

    @Test
    public void Test002() {
        response.body("findAll{it.id == 4123663}", hasItem(hasEntry("name", "Chakrika Bandopadhyay")));
    }

    //3. Check the single ‘Name’ in the Array list (Subhashini Talwar)
    @Test
    public void test003() {
        response.body("name", hasItem("Bhasvan Kapoor"));
    }

    //4. Check the multiple ‘Names’ in the ArrayList (Mrs. Menaka Bharadwaj, Msgr. Bodhan

    @Test
    public void test004() {
        response.body("name", hasItems("Bhasvan Kapoor", "Chaturbhuj Reddy", "Fr. Preity Guneta"));
    }

    //   5. Verify the email of userid = 5471 is equal “adiga_aanjaneya_rep@jast.org”

    @Test
    public void Test005() {
        response.body("findAll{it.id ==4123662 }.email", hasItem(equalTo("kama_pilla@barton.test")));
    }

    //6. Verify the status is “Active” of username is “Shanti Bhat V”

    @Test
    public void Test006() {
        response.body("findAll{it.name == 'Devi Pandey'}.status", hasItem(equalTo("active")));
    }

    //7. Verify the Gender = male of username is “Niro Prajapat

    @Test
    public void test007() {
        response.body("[0].gender", equalTo("female"));
        response.body("[0].name", equalTo("Bhasvan Kapoor"));
    }
}
