package com.project.basicsplit.di;

import com.project.basicsplit.MainPresentation;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainPresentation presentation;

    public MainModule(MainPresentation presentation) {
        this.presentation = presentation;
    }

    @Provides
    @PerActivity
    public MainPresentation providePresentation() {
        return presentation;
    }
}
