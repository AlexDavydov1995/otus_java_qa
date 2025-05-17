package org.example;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.example.dto.UserDTO;
import org.example.extension.RestExtension;
import org.example.service.PetStoreApi;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@ExtendWith(RestExtension.class)
public class CreateNewUserTest {

  @Inject
  @Named("successResponse")
  private ResponseSpecification responseSpecification;

  @Inject
  private PetStoreApi petStoreApi;

  private static Stream<Arguments> dataProvider() {
    UserDTO userFullDTO = UserDTO.builder()
        .email("alexdavydov@mail.ru")
        .firstName("Alex")
        .id(408L)
        .lastName("Davydov")
        .password("passw0rd")
        .phone("123456789")
        .username("bestUser")
        .userStatus(30L)
        .build();

    UserDTO userNoIdDTO = UserDTO.builder()
        .firstName("Ryan")
        .lastName("Gosling")
        .userStatus(65L)
        .phone("89-9090909")
        .username("RyanGosling")
        .build();

    UserDTO userNoFirstNameDTO = UserDTO.builder()
        .id(409L)
        .userStatus(65L)
        .phone("89-9090909")
        .username("Kolya")
        .build();

    return Stream.of(
        Arguments.of(userFullDTO),
        Arguments.of(userNoFirstNameDTO),
        Arguments.of(userNoIdDTO)
    );
  }

  private static Stream<Arguments> duplicateDataProvider() {
    UserDTO userFullDTO = UserDTO.builder()
        .email("alexdavydov@mail.ru")
        .firstName("Alex")
        .id(408L)
        .lastName("Davydov")
        .password("passw0rd")
        .phone("123456789")
        .username("bestUser")
        .userStatus(30L)
        .build();

    UserDTO anotherUserFullDTO = UserDTO.builder()
        .email("ryangosling@mail.ru")
        .firstName("Ryan")
        .id(409L)
        .lastName("Gosling")
        .password("passw0rd")
        .phone("123456789")
        .username("RyanGosling")
        .userStatus(30L)
        .build();

    return Stream.of(
        Arguments.of(userFullDTO),
        Arguments.of(anotherUserFullDTO)
    );
  }

  // ========== Тесты для POST /user ==========
  /**
   * Проверяет успешное создание нового пользователя
   * 1. Отправляем корректные данные пользователя
   * 2. Проверяем код ответа 200
   * 3. Проверяем, что в ответе пришло сообщение об успешном создании
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  void createUser_WithCorrectData_ShouldBeProcessed(UserDTO userDTO) {
    petStoreApi.creteNewUser(userDTO)
        .spec(responseSpecification)
        .statusCode(HttpStatus.SC_OK)
        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"))
        .body("type", equalTo("unknown"))
        .body("code", equalTo(HttpStatus.SC_OK))
        .body("message", matchesPattern("\\d+"));
  }

  /**
   * Проверяет обработку дублирования пользователя
   * 1. Создаем пользователя
   * 2. Пытаемся создать пользователя с тем же username
   * 3. Проверяем что сервер принимает дубликат
   */
  @ParameterizedTest
  @MethodSource("duplicateDataProvider")
  void createUser_WithDuplicateUsername_ShouldBeProcessed(UserDTO userDTO) {

    // Первое создание
    petStoreApi.creteNewUser(userDTO)
        .spec(responseSpecification)
        .statusCode(HttpStatus.SC_OK)
        .body("type", equalTo("unknown"))
        .body("code", equalTo(HttpStatus.SC_OK))
        .body("message", matchesPattern("\\d+"));

    // Попытка создания дубликата
    petStoreApi.creteNewUser(userDTO)
        .spec(responseSpecification)
        .statusCode(HttpStatus.SC_OK)
        .body("type", equalTo("unknown"))
        .body("code", equalTo(HttpStatus.SC_OK))
        .body("message", matchesPattern("\\d+"));
  }
}