package com.project.basicsplit.di;

import com.project.basicsplit.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    MainComponent mainComponent(MainModule mainModule);

}
