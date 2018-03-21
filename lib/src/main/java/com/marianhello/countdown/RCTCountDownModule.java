package com.marianhello.countdown;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by finch on 20.3.2018.
 */

public class RCTCountDownModule extends ReactContextBaseJavaModule {
    private RCTCountDownViewManager mCountDownViewManager;

    public static final String REACT_CLASS = "CountDown";

    public RCTCountDownModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    public RCTCountDownModule(ReactApplicationContext reactContext, RCTCountDownViewManager countDownViewManager) {
        super(reactContext);
        if (countDownViewManager != null) {
            mCountDownViewManager = countDownViewManager;
        }

    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void start() {
        RCTNeonCountDownView view = getNeonCountDownView();
        if (view != null) {
            view.startCountDown();
        }
    }

    @ReactMethod
    public void stop() {
        RCTNeonCountDownView view = getNeonCountDownView();
        if (view != null) {
            view.stopCountDown();
        }
    }

    private RCTNeonCountDownView getNeonCountDownView() {
        if (mCountDownViewManager != null) {
            return mCountDownViewManager.getNeonCountDownViewInstance();
        }
        return null;
    }
}
