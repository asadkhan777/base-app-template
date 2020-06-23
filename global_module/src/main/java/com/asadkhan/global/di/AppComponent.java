package com.asadkhan.global.di;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.asadkhan.global.network.NetworkService;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(
        modules = {
                AppModule.class,
                DataModule.class,
                //ServicesModule.class
        }
)
public interface AppComponent {

    @NonNull
    Context getApplicationContext();


    @NonNull
    Application getApplication();


    @NonNull
    NetworkService networkService();


    @NonNull
    Gson gson();

}
