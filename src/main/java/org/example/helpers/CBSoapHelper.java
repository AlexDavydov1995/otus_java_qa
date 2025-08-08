package org.example.helpers;

import static io.restassured.RestAssured.given;

import com.google.inject.Inject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;


public class CBSoapHelper {
  private final RequestSpecification requestSpecification;

  @Inject
  public CBSoapHelper() {
    this.requestSpecification = given()
        .baseUri(System.getProperty("cb.url"))
        .contentType(ContentType.XML);
  }

  public Response sendSoapRequestForDate(String date) {
    return given(requestSpecification)
        .contentType(ContentType.XML)
        .accept(ContentType.XML)
        .log().all()
        .when()
        .get("/XML_daily.asp?date_req={date}", date)
        .then()
        .log().all()
        .extract()
        .response();
  }


  public String findCurrencyValue(ValidatableResponse response, String currencyCode) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();

      InputStream stream = response.extract().asInputStream();
      Document document = builder.parse(stream);

      NodeList valuteList = document.getElementsByTagName("Valute");

      for (int i = 0; i < valuteList.getLength(); i++) {
        Element valute = (Element) valuteList.item(i);
        String charCode = valute.getElementsByTagName("CharCode").item(0).getTextContent();
        if (currencyCode.equals(charCode)) {
          return valute.getElementsByTagName("Value").item(0).getTextContent();
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    throw new RuntimeException("Currency " + currencyCode +" found");
  }
}