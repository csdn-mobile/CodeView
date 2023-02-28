package net.csdn.codeview.demo;

import android.app.Application;

import net.csdn.codeview.classifier.CodeProcessor;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // train classifier on app start
        CodeProcessor.init(this);
    }
}
