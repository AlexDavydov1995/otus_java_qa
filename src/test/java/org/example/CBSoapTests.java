package org.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.extensions.APIExtension;
import org.example.helpers.CBSoapHelper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.xml.soap.SOAPMessage;

@ExtendWith(APIExtension.class)
public class CBSoapTests {

  @Inject
  CBSoapHelper soapHelper;

  @Test
  void testCreateAndSendSoapMessage() throws Exception {

    ValidatableResponse response = soapHelper.sendSoapRequestForDate("02/03/2002").then().statusCode(HttpStatus.SC_OK);
    String currencyRate = soapHelper.findCurrencyValue(response, "USD");
    assertThat(currencyRate, equalTo("30,9436"));
  }
}
