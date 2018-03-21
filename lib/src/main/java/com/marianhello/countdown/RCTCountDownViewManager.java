package com.marianhello.countdown;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Created by finch on 20.3.2018.
 */

public class RCTCountDownViewManager extends SimpleViewManager<RCTNeonCountDownView> {
    private RCTNeonCountDownView mCountDownView;

    public static final String REACT_CLASS = "CountDownView";

    public RCTCountDownViewManager(ReactApplicationContext reactContext) {
        super();
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put(
                        "topChange",
                        MapBuilder.of(
                                "phasedRegistrationNames",
                                MapBuilder.of("bubbled", "onChange")))
                .build();
    }

    @Override
    protected RCTNeonCountDownView createViewInstance(ThemedReactContext reactContext) {
        mCountDownView = new RCTNeonCountDownView(reactContext);
        return mCountDownView;
    }

    @ReactProp(name = "millisInFuture")
    public void setMillisInFuture(RCTNeonCountDownView view, @Nullable Integer millisInFuture) {
        if (millisInFuture != null) {
            view.setMillisInFuture(millisInFuture.longValue());
        }
    }

    @ReactProp(name = "intervalMillis")
    public void seInterval(RCTNeonCountDownView view, @Nullable Integer millis) {
        if (millis != null) {
            view.setInterval(millis);
        }
    }

    @ReactProp(name = "secondColor")
    public void setSecondColor(RCTNeonCountDownView view, @Nullable String colorString) {
        if (colorString != null) {
            view.setSecondColor(toIntColor(colorString));
        }
    }

    @ReactProp(name = "secondColorDim")
    public void setSecondColorDim(RCTNeonCountDownView view, @Nullable String colorString) {
        if (colorString != null) {
            view.setSecondColorDim(toIntColor(colorString));
        }
    }

    @ReactProp(name = "minuteColor")
    public void setMinuteColor(RCTNeonCountDownView view, @Nullable String colorString) {
        if (colorString != null) {
            view.setMinuteColor(toIntColor(colorString));
        }
    }

    @ReactProp(name = "minuteColorDim")
    public void setMinuteColorDim(RCTNeonCountDownView view, @Nullable String colorString) {
        if (colorString != null) {
            view.setMinuteColorDim(toIntColor(colorString));
        }
    }

    public RCTNeonCountDownView getNeonCountDownViewInstance() {
        return mCountDownView;
    }

    private static int toIntColor(String colorString) {
        return Color.parseColor(colorString);
    }
}
