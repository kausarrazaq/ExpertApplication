package com.example.expertapplication.utilis;

import android.app.Application;

public class ApplicationExpert extends Application {
    ShareMemory shareMemory;

    @Override
    public void onCreate() {
        super.onCreate();

        ShareMemory.init(this);
        shareMemory = ShareMemory.getmInstence();
    }
}
