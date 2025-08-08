package org.example.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.helpers.CBSoapHelper;
import org.example.helpers.PSHttpHelper;

public class ApiHelpersModule extends AbstractModule {

  @Provides
  @Singleton
  public PSHttpHelper getPSHttpHelper() {
    return new PSHttpHelper();
  }

  @Provides
  @Singleton
  public CBSoapHelper getCBSoapHelper() {
    return new CBSoapHelper();
  }
}
