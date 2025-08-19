package com.madrid.detectImageContent;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SensitiveContentDetection_Factory implements Factory<SensitiveContentDetection> {
  private final Provider<Context> contextProvider;

  public SensitiveContentDetection_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SensitiveContentDetection get() {
    return newInstance(contextProvider.get());
  }

  public static SensitiveContentDetection_Factory create(Provider<Context> contextProvider) {
    return new SensitiveContentDetection_Factory(contextProvider);
  }

  public static SensitiveContentDetection newInstance(Context context) {
    return new SensitiveContentDetection(context);
  }
}
