package org.example.extension;

import static io.restassured.RestAssured.given;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.example.guice.RestModule;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

@Slf4j
public class RestExtension implements BeforeEachCallback, AfterEachCallback {

  private Injector injector = null;

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    log.info("Завершен тест {}", extensionContext.getTestMethod().get());
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    RequestSpecification requestSpecification = given()
        .baseUri(System.getProperty("baseUrl", "https://petstore.swagger.io/v2"))
        .contentType(ContentType.JSON);
    log.info("Запущен тест {}", extensionContext.getTestMethod().get());
    injector = Guice.createInjector(new RestModule(requestSpecification));
    injector.injectMembers(extensionContext.getTestInstance().get());
  }
}
