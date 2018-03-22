package com.marianhello.countdown;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

/**
 * Created by finch on 20.3.2018.
 */

public class RCTCountDownViewManager extends SimpleViewManager<RCTNeonCountDownView> {
    public static final String REACT_CLASS = "CountDownView";

    public static final int COMMAND_START = 1;
    public static final int COMMAND_STOP = 2;

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
        return new RCTNeonCountDownView(reactContext);
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "start",
                COMMAND_START,
                "stop",
                COMMAND_STOP);
    }

    @Override
    public void receiveCommand(
            RCTNeonCountDownView view,
            int commandType,
            @Nullable ReadableArray args) {
        Assertions.assertNotNull(view);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case COMMAND_START: {
                view.startCountDown();
                return;
            }
            case COMMAND_STOP: {
                view.stopCountDown();
                return;
            }
            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        commandType,
                        getClass().getSimpleName()));
        }
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

    @ReactProp(name = "textColor")
    public void setTextColor(RCTNeonCountDownView view, @Nullable String colorString) {
        if (colorString != null) {
            view.setTextColor(toIntColor(colorString));
        }
    }

    @ReactProp(name = "textSize")
    public void setTextSize(RCTNeonCountDownView view, @Nullable Integer size) {
        if (size != null) {
            view.setTextSize(size);
        }
    }

    private static int toIntColor(String colorString) {
        return Color.parseColor(colorString);
    }
}
