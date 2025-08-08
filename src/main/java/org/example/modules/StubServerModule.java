package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.StubServer;

public class StubServerModule extends AbstractModule {

  @Provides
  @Singleton
  public StubServer getStubServer() {
    return new StubServer();
  }
}
