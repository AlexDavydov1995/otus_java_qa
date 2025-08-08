package org.example.extensions;

import com.google.inject.Guice;
import org.example.modules.ApiHelpersModule;
import org.example.modules.StubServerModule;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class APIExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    Guice.createInjector(new StubServerModule(), new ApiHelpersModule()).injectMembers(extensionContext.getTestInstance().get());
  }
}
