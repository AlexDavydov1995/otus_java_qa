package org.example;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.example.dto.NewUserDTO;
import org.example.service.PetStoreApi;
import org.junit.jupiter.api.Test;

public class CreateNewUserTest {

  @Test
  void createUser() {
    long expectedMessage = 408L;
    PetStoreApi api = new PetStoreApi();

    NewUserDTO userDTO = NewUserDTO.builder()
        .id(expectedMessage)
        .firstName("SomeUser")
        .userStatus(65L)
        .phone("89-9090909")
        .username("Kolya")
        .build();

    api.creteNewUser(userDTO)
        .statusCode(HttpStatus.SC_OK)
        .time(lessThan(5000L))
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
        .body("type", equalTo("unknown"))
        .body("code", equalTo(200))
        .body("message", equalTo("408"));

    api.deleteUser("Sasha")
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}
