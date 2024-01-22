package com.gorest.gorestinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

import java.util.HashMap;

public class GoRestSteps {
    @Step("Creating user with name : {0} , email : {1} , gender : {2} , status : {3}")
    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 3c20de2b55e8d246a9e5be58b6691907d3eb3342eaeaf2f270c6da6c28489f8b")
                .when()
                .body(userPojo)
                .post()
                .then().statusCode(201);
    }

    @Step("Getting user information from the email : {0}")
    public HashMap<String, Object> getInfoByEmail(String email) {
        String p1 = "findAll{it.email == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .header("Authorization", "Bearer 3c20de2b55e8d246a9e5be58b6691907d3eb3342eaeaf2f270c6da6c28489f8b")
                .get()
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("findAll{it.email == '" + email + "'}.get(0)");
    }


    @Step("Updating user with id : {0} , name : {1} ,email : {2} , gender : {3}, status : {4}")
    public ValidatableResponse updateUser(int id, String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name + "_Updated");
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 3c20de2b55e8d246a9e5be58b6691907d3eb3342eaeaf2f270c6da6c28489f8b")
                .pathParam("id", id)
                .body(userPojo)
                .when()
                .put(EndPoints.UPDATE_A_USER_BY_ID)
                .then().statusCode(200);
    }

    @Step("Deleting user with id : {0}")
    public ValidatableResponse deleteUser(int id) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 3c20de2b55e8d246a9e5be58b6691907d3eb3342eaeaf2f270c6da6c28489f8b")
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_A_USER_BY_ID)
                .then().statusCode(204).log().all();
    }

    @Step("Getting user by id : {0}")
    public ValidatableResponse gettingUserById(int id) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 3c20de2b55e8d246a9e5be58b6691907d3eb3342eaeaf2f270c6da6c28489f8b")
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_USER_BY_ID)
                .then();

    }
}
