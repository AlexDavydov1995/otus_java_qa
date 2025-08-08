package org.example;

import static org.hamcrest.Matchers.*;

import com.google.inject.Inject;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.example.extensions.APIExtension;
import org.example.helpers.StubHttpHelper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(APIExtension.class)
public class StubTests {

  @Inject
  private StubServer stubServer;

  @BeforeEach
  public void setUp() {
    stubServer.startServer();
  }

  @AfterEach
  public void tearDown() {
    stubServer.stopServer();
  }

  @Test
  public void getUserByIdTest() {
    StubHttpHelper.getUserById(1)
        .then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/user-schema.json"))
        .body("name", equalTo("Test user"))
        .body("course", equalTo("QA"))
        .body("email", equalTo("test@test.test"))
        .body("age", equalTo(23));
  }

  @Test
  public void getAllUsersTest() {
    StubHttpHelper.getAllUsers()
        .then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/users-array-schema.json"))
        .body("size()", greaterThanOrEqualTo(1))
        .body("[0].name", not(emptyString()));
  }

  @Test
  public void getUserScoreTest() {
    StubHttpHelper.getUserScore(1)
        .then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/score-schema.json"))
        .body("name", equalTo("Test user"))
        .body("score", greaterThanOrEqualTo(0));
  }

  @Test
  public void getAllCoursesTest() {
    StubHttpHelper.getAllCourses()
        .then()
        .statusCode(200)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/courses-array-schema.json"))
        .body("size()", greaterThanOrEqualTo(1))
        .body("[0].name", not(emptyString()))
        .body("[0].price", greaterThan(0));
  }
}
