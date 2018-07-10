package com.project.basicsplit;

import android.app.Application;
import android.content.Context;

import com.project.basicsplit.di.AppComponent;
import com.project.basicsplit.di.AppModule;
import com.project.basicsplit.di.DaggerAppComponent;


public class BasicApplication extends Application {

    private AppComponent appComponent;

    // unlike in activity, App onCreate is public
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    // static method to get Application instance to access getAppComponent.
    // YOU NEED TO ADD A NAME ATTR TO AndroidManifest.xml OR GET A CLASSCASTEXCEPTION!!!
    public static BasicApplication get(Context context) {
        return (BasicApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
