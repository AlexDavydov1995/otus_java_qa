package org.example;

import com.google.inject.Inject;

public class StubServerRunner {

  public static void main(String... args) {
    new StubServer().startServer();
  }
}
