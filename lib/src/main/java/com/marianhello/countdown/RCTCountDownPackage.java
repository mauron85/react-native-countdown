package com.marianhello.countdown;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by finch on 20.3.2018.
 */

public class RCTCountDownPackage implements ReactPackage {
    private RCTCountDownViewManager mCountDownViewManager;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        if (mCountDownViewManager == null) {
            mCountDownViewManager = new RCTCountDownViewManager(reactContext);
        }
        return Arrays.<NativeModule>asList(
            new RCTCountDownModule(reactContext, mCountDownViewManager)
        );
    }

    // Deprecated RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        if (mCountDownViewManager == null) {
            mCountDownViewManager = new RCTCountDownViewManager(reactContext);
        }
        return Arrays.<ViewManager>asList(
            mCountDownViewManager
        );
    }
}
