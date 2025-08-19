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
public final class GetImageBitmap_Factory implements Factory<GetImageBitmap> {
  private final Provider<Context> contextProvider;

  public GetImageBitmap_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public GetImageBitmap get() {
    return newInstance(contextProvider.get());
  }

  public static GetImageBitmap_Factory create(Provider<Context> contextProvider) {
    return new GetImageBitmap_Factory(contextProvider);
  }

  public static GetImageBitmap newInstance(Context context) {
    return new GetImageBitmap(context);
  }
}
