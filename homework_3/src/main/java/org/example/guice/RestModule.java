package org.example.guice;

import static io.restassured.RestAssured.given;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.example.service.PetStoreApi;

public class RestModule extends AbstractModule {

  private final RequestSpecification requestSpecification;

  public RestModule(RequestSpecification requestSpecification) {
    this.requestSpecification = requestSpecification;
  }

  @Provides
  @Named("successResponse")
  public ResponseSpecification getResponseSpecification() {
    return new ResponseSpecBuilder()
        .expectStatusCode(HttpStatus.SC_OK)
        .expectContentType(ContentType.JSON)
        .build();
  }

  @Provides
  @Named("errorResponseJson")
  public ResponseSpecification getErrorResponseSpecificationJson() {
    return new ResponseSpecBuilder()
        .expectContentType(ContentType.JSON)
        .build();
  }

  @Provides
  @Named("errorResponseNoType")
  public ResponseSpecification getErrorResponseSpecificationNoType() {
    return new ResponseSpecBuilder()
        .build();
  }

  @Provides
  @Singleton
  public PetStoreApi getPetStoreApi() {
    return new PetStoreApi(requestSpecification);
  }
}
