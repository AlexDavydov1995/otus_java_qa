package org.example.exceptions;

public class DelayException extends RuntimeException {
  public DelayException() {
    super("Exception while delay");
  }
}
