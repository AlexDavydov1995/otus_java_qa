package org.example.helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Properties;

public class StubHttpHelper {

  private static final String BASE_URL = System.getProperty("stub.url");

  public static Response getUserById(int userId) {
    return RestAssured.given()
        .baseUri(BASE_URL)
        .when()
        .get("/user/get/" + userId);
  }

  public static Response getAllUsers() {
    return RestAssured.given()
        .baseUri(BASE_URL)
        .when()
        .get("/user/get/all");
  }

  public static Response getUserScore(int userId) {
    return RestAssured.given()
        .baseUri(BASE_URL)
        .when()
        .get("/user/score/" + userId);
  }

  public static Response getAllCourses() {
    return RestAssured.given()
        .baseUri(BASE_URL)
        .when()
        .get("/course/get/all");
  }
}
