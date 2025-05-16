package org.example.steps.page;

import com.google.inject.Inject;
import io.cucumber.java.ru.Пусть;
import org.example.pages.MainPage;
import org.example.steps.LoggedSteps;

public class MainPageSteps extends LoggedSteps {

  @Inject
  private MainPage mainPage;

  @Пусть("Открыта главная страница в браузере$")
  public void openPage() {
    mainPage.open();
  }
}
