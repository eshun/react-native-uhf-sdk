package com.uhf.example;

import com.facebook.react.ReactActivity;
import com.uhf.sdk.RNUhfModule;

import android.view.KeyEvent;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "example";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        RNUhfModule.getInstance().onKeyDownEvent(keyCode,event);
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        RNUhfModule.getInstance().onKeyUpEvent(keyCode,event);
        return super.onKeyUp(keyCode,event);
    }
}
