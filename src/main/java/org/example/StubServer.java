package org.example;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class StubServer {

  private WireMockServer wireMockServer;

  public void startServer() {
    wireMockServer = new WireMockServer(wireMockConfig().port(8080));
    wireMockServer.start();
    WireMock.configureFor("localhost", 8080);
    setupStubs();
  }

  public void stopServer() {
    if (wireMockServer != null) {
      wireMockServer.stop();
    }
  }

  private void setupStubs() {
    // Получение пользователя по ID
    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user/get/1"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"name\":\"Test user\",\"course\":\"QA\",\"email\":\"test@test.test\",\"age\":23}")));

    // Получение всех пользователей
    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user/get/all"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("[{\"name\":\"User1\",\"course\":\"QA\",\"email\":\"user1@test.test\",\"age\":23},"
                + "{\"name\":\"User2\",\"course\":\"Java\",\"email\":\"user2@test.test\",\"age\":25}]")));

    // Получение оценки пользователя
    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/user/score/1"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"name\":\"Test user\",\"score\":78}")));

    // Получение списка курсов
    WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/course/get/all"))
        .willReturn(WireMock.aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("[{\"name\":\"QA java\",\"price\":15000},"
                + "{\"name\":\"Java\",\"price\":12000}]")));
  }

}
