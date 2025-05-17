package org.example;

import static org.hamcrest.Matchers.*;

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
public class DeleteUserTest {

  @Inject
  @Named("successResponse")
  private ResponseSpecification responseSpecification;

  @Inject
  @Named("errorResponseNoType")
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

  // ========== Тесты для DELETE /user/{username} ==========
  /**
   * Проверяет удаление существующего пользователя
   * 1. Сначала создаем тестового пользователя
   * 2. Удаляем его по username
   * 3. Проверяем корректность возвращаемых данных
   */
  @ParameterizedTest
  @MethodSource("usernameProvider")
  void deleteUser_WithExistingUsername_ShouldReturnUsernameInMessage(UserDTO user) {
    petStoreApi.creteNewUser(user);

    petStoreApi.deleteUser(user.getUsername())
        .spec(responseSpecification)
        .statusCode(HttpStatus.SC_OK)
        .body("code", equalTo(HttpStatus.SC_OK))
        .body("type", equalTo("unknown"))
        .body("message", equalTo(user.getUsername()));
  }

  /**
   * Проверяет удаление несуществующего пользователя
   * 1. Пробуем удалить по заведомо несуществующему username
   * 2. Проверяем код ошибки 404 (Not Found)
   * 3. Проверяем, что в ответе нет тела
   */
  @Test
  void deleteUser_WithNonExistingUsername_ShouldReturnNotFound() {
    String nonExistingUsername = "nonexistinguser_" + System.currentTimeMillis();
    petStoreApi.deleteUser(nonExistingUsername)
        .spec(erorrResponseSpecification)
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .body(is(emptyOrNullString()));;
  }
}
