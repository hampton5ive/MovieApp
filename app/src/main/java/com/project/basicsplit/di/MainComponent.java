package com.project.basicsplit.di;

import com.project.basicsplit.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = MainModule.class)
@PerActivity
public interface MainComponent {
    void inject(MainActivity activity);
}
