package org.example.helpers;

import static io.restassured.RestAssured.given;

import com.google.inject.Inject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.dto.UserDTO;

public class PSHttpHelper {

  private final RequestSpecification requestSpecification;

  @Inject
  public PSHttpHelper(){
    this.requestSpecification = given()
        .baseUri(System.getProperty("ps.url"))
        .contentType(ContentType.JSON);
  }


  /**
   * Получить информацию о питомце по ID
   * GET /pet/{petId}
   */
  public Response getPetById(long petId) {
    return given(requestSpecification)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .log().all()
        .when()
        .get("/pet/{petId}", petId)
        .then()
        .log().all()
        .extract()
        .response();
  }

  /**
   * Получить инвентарь магазина
   * GET /store/inventory
   */
  public Response getStoreInventory() {
    return given(requestSpecification)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .log().all()
        .when()
        .get("/store/inventory")
        .then()
        .log().all()
        .extract()
        .response();
  }


  /**
   * Получить информацию о пользователе по имени
   * GET /user/{username}
   */
  public Response getUserByUsername(String username) {
    return given(requestSpecification)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .log().all()
        .when()
        .get("/user/{username}", username)
        .then()
        .log().all()
        .extract()
        .response();
  }

  /**
   * Создать нового пользователя
   * POST /user
   */
  public Response createUser(UserDTO userDTO) {
    return given(requestSpecification)
        .contentType(ContentType.JSON)
        .accept(ContentType.JSON)
        .log().all()
        .body(userDTO)
        .when()
        .post("/user")
        .then()
        .log().all()
        .extract()
        .response();
  }
}
