package com.test.flikrsearch.dagger;

import com.test.flikrsearch.AppController;
import com.test.flikrsearch.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RetroModule.class})
public interface APIComponent {

    void inject(AppController controller);

}
