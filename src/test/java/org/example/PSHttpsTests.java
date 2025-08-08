package org.example;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import com.google.inject.Inject;
import org.apache.http.HttpStatus;
import org.example.dto.UserDTO;
import org.example.extensions.APIExtension;
import org.example.helpers.PSHttpHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(APIExtension.class)
public class PSHttpsTests {

  @Inject
  PSHttpHelper httpHelper;

  @Test
  public void getStoreInventoryTest() {
    httpHelper.getStoreInventory()
        .then()
        .statusCode(HttpStatus.SC_OK);
  }

  @Test
  public void getUserByNameTest() {
    httpHelper.getUserByUsername("alex")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body("message", equalTo("User not found"));
  }

  @Test
  public void test() {
    UserDTO userDTO = UserDTO.builder()
        .firstName("Ryan")
        .lastName("Gosling")
        .id(409L)
        .userStatus(65L)
        .phone("89-9090909")
        .username("RyanGosling")
        .build();

    httpHelper.createUser(userDTO)
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body("type", equalTo("unknown"))
        .body("code", equalTo(HttpStatus.SC_OK))
        .body("message", matchesPattern("\\d+"));
  }

}
