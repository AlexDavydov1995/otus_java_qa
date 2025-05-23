package org.example.driver;

public enum Browser {
  CHROME("chrome"),
  FIREFOX("firefox");

  private final String name;

  Browser(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
