package com.asadkhan.global.di;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppModule {

    private final Application application;


    public AppModule(Application application) {
        this.application = application;
    }


    @NonNull
    @Provides
    @Singleton
    public Application application() {
        return this.application;
    }


    @NonNull
    @Provides
    @Singleton
    public Context appContext() {
        return this.application;
    }
}
