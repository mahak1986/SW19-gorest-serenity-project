package com.gorest.gorestcrud;

import com.gorest.gorestinfo.GoRestSteps;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class GoRestCrudSteps extends TestBase {
    static int id;
    static String name = TestUtils.getRandomValue() + "Mahak";
    static String email = TestUtils.getRandomValue() + "mahakag@gmail.com";
    static String gender = "Female";
    static String status = "active";
    @Steps
    GoRestSteps goRestSteps;

    @Title ("Creating new user")
    @Test
    public void test001(){
        ValidatableResponse response = goRestSteps.createUser(name, email,gender,status);
        response.log().all().statusCode(201);
        id = response.extract().path("id");
    }

    @Title("Verify user was created")
    @Test
    public void test002(){
        HashMap<String, Object> usersMap = goRestSteps.getInfoByEmail(email);
        Assert.assertThat(usersMap,hasValue(email));
        System.out.println(usersMap);
    }

    @Title("Updating the user")
    @Test
    public void test003(){
        name = name + TestUtils.getRandomValue();
        email = "mahak123@gmail.com" + TestUtils.getRandomValue();
        goRestSteps.updateUser(id,name,email,gender,status).statusCode(200);
        HashMap<String,Object> userMap = goRestSteps.getInfoByEmail(email);
        Assert.assertThat(userMap,hasValue(email));

    }
    @Title("Deleting the user and verifying user was deleted.")
    @Test
    public void test004() {
        goRestSteps.deleteUser(id).statusCode(204);
        goRestSteps.gettingUserById(id).statusCode(404);
    }
}

