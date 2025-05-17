package org.example.service;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.dto.NewUserDTO;

public class PetStoreApi {

  private static final String BASE_URL = System.getProperty("baseUrl", "https://petstore.swagger.io/v2");
  private RequestSpecification spec;

  public PetStoreApi() {
    spec = given()
        .baseUri(BASE_URL)
        .contentType(ContentType.JSON);
  }

  public ValidatableResponse creteNewUser(NewUserDTO userDTO) {

    return given(spec)
        .basePath("/user")
        .body(userDTO)
        .log().all()
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUser(String userName) {

    return given(spec)
        .basePath("/user/{username}")
        .pathParam("username", userName)
        .log().all()
        .when()
        .delete()
        .then()
        .log().all();
  }

}
