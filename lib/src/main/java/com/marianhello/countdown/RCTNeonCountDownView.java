package com.marianhello.countdown;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by finch on 21.3.2018.
 */

public class RCTNeonCountDownView extends NeonCountDownView {
    public RCTNeonCountDownView(Context context) {
        super(context);
    }

    public RCTNeonCountDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RCTNeonCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RCTNeonCountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void runOnUiThread(Runnable runnable) {
        com.facebook.react.bridge.UiThreadUtil.runOnUiThread(runnable);
    }

    @Override
    protected void onFinish() {
        WritableMap event = Arguments.createMap();
        event.putString("message", "finish");
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "topChange", event);
    }

    @Override
    protected void onTick(long millisUntilFinished) {
        WritableMap event = Arguments.createMap();
        event.putString("message", "tick");
        event.putInt("payload", safeLongToInt(millisUntilFinished));
        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), "topChange", event);
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }
}
