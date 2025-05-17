package org.example.service;

import static io.restassured.RestAssured.given;

import com.google.inject.Inject;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.dto.UserDTO;

public class PetStoreApi {

  private final RequestSpecification requestSpecification;

  @Inject
  public PetStoreApi(RequestSpecification requestSpecification) {
    this.requestSpecification = requestSpecification;
  }

  public ValidatableResponse creteNewUser(UserDTO userDTO) {
    return given(requestSpecification)
        .basePath("/user")
        .body(userDTO)
        .log().all()
        .when()
        .post()
        .then()
        .log().all();
  }

  public ValidatableResponse getUser(String username) {
    return given(requestSpecification)
        .pathParam("username", username)
        .log().all()
        .when()
        .get("user/{username}")
        .then()
        .log().all();
  }

  public ValidatableResponse deleteUser(String username) {
    return given(requestSpecification)
        .pathParam("username", username)
        .log().all()
        .when()
        .delete("user/{username}")
        .then()
        .log().all();
  }

}
