package org.example.exceptions;

public class BrowserNotSupported extends RuntimeException {
  public BrowserNotSupported(String message) {
    super(String.format("Browser not suppported, %s", message));
  }
}
