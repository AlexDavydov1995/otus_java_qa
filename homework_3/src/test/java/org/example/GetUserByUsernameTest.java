package org.example;

import static org.hamcrest.Matchers.equalTo;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.example.dto.UserDTO;
import org.example.extension.RestExtension;
import org.example.service.PetStoreApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@ExtendWith(RestExtension.class)
public class GetUserByUsernameTest {

  @Inject
  @Named("successResponse")
  private ResponseSpecification responseSpecification;

  @Inject
  @Named("errorResponseJson")
  private ResponseSpecification erorrResponseSpecification;

  @Inject
  PetStoreApi petStoreApi;

  public static Stream<Arguments> usernameProvider() {
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

    return Stream.of(
        Arguments.of(userFullDTO),
        Arguments.of(userNoIdDTO)
    );
  }

  // ========== Тесты для GET /user/{username} ==========
  /**
   * Проверяет получение информации о существующем пользователе
   * 1. Сначала создаем тестового пользователя
   * 2. Запрашиваем его данные по username
   * 3. Проверяем корректность возвращаемых данных
   */
  @ParameterizedTest
  @MethodSource("usernameProvider")
  void getUser_WithExistingUsername_ShouldReturnUserData(UserDTO user) {
    petStoreApi.creteNewUser(user);

    petStoreApi.getUser(user.getUsername())
        .spec(responseSpecification)
        .statusCode(HttpStatus.SC_OK)
        .body("username", equalTo(user.getUsername()))
        .body("firstName", equalTo(user.getFirstName()))
        .body("lastName", equalTo(user.getLastName()));;
  }

  /**
   * Проверяет обработку запроса несуществующего пользователя
   * 1. Запрашиваем данные по заведомо несуществующему username
   * 2. Проверяем код ошибки 404 (Not Found)
   * 3. Проверяем сообщение об ошибке
   */
  @Test
  void getUser_WithNonExistingUsername_ShouldReturnNotFound() {
    String nonExistingUsername = "nonexistinguser_" + System.currentTimeMillis();
    petStoreApi.getUser(nonExistingUsername)
        .spec(erorrResponseSpecification)
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body("code", equalTo(1))
        .body("type", equalTo("error"))
        .body("message", equalTo("User not found"));
  }
}

