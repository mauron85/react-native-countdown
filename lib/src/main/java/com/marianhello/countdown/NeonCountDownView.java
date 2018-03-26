package com.marianhello.countdown;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richpath.RichPath;
import com.richpath.RichPathView;

/**
 * Created by finch on 20.3.2018.
 */

public class NeonCountDownView extends LinearLayout {

    public static final String DEFAULT_SECOND_COLOR = "#f57df5";
    public static final String DEFAULT_MINUTE_COLOR = "#ff3300";
    public static final String DEFAULT_SECOND_COLOR_DIM = "#ffe6ff";
    public static final String DEFAULT_MINUTE_COLOR_DIM = "#fbe6e6";
    public static final int DEFAULT_INTERVAL_MILLIS = 1000;

    private boolean mIsStarted;
    private long mMillisInFuture, mCurrentMillisInFuture;
    private int mIntervalMillis;
    private int[] mClock;

    private int mSecondColor, mMinuteColor;
    private int mSecondColorDim, mMinuteColorDim;

    private CountDownTimer mCountDownTimer;
    private RichPathView mNeonClockView;
    private TextView mTimeView;

    private static final int RING_SECONDS = 0;
    private static final int RING_MINUTES = 1;
    private static final int RING_HOURS = 2;

    private final static int HOUR_IN_SECONDS = 60 * 60;

    public NeonCountDownView(Context context) {
        this(context, null);
    }

    public NeonCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NeonCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NeonCountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        mIsStarted = false;
        mMillisInFuture = 0;
        mCurrentMillisInFuture = 0;
        mIntervalMillis = DEFAULT_INTERVAL_MILLIS;
        mClock = new int[]{0, 0, 0, 0, 0, 0};

        mSecondColor = Color.parseColor(DEFAULT_SECOND_COLOR);
        mMinuteColor = Color.parseColor(DEFAULT_MINUTE_COLOR);
        mSecondColorDim = Color.parseColor(DEFAULT_MINUTE_COLOR_DIM);
        mMinuteColorDim = Color.parseColor(DEFAULT_SECOND_COLOR_DIM);

        //Inflate xml resource, pass "this" as the parent, we use <merge> tag in xml to avoid
        //redundant parent, otherwise a LinearLayout will be added to this LinearLayout ending up
        //with two view groups
        inflate(getContext(), R.layout.view_neon_countdown, this);

        mNeonClockView = (RichPathView) findViewById(R.id.neon_clock);
        mTimeView = findViewById(R.id.time_text);
    }

    protected void runOnUiThread(Runnable runnable) {
        ((Activity) getContext()).runOnUiThread(runnable);
    }

    protected int getClockPathIndex(int index, int clockRing) {
        switch (clockRing) {
            case RING_SECONDS:
                return index + 60;
            case RING_MINUTES:
                return index;
            case RING_HOURS:
                return index + 120;
            default:
                return -1;
        }
    }

    protected int getClockColor(int clockRing, boolean highlight) {
        if (clockRing == RING_SECONDS) {
            return highlight ? mSecondColor : mSecondColorDim;
        } else if (clockRing == RING_MINUTES) {
            return highlight ? mMinuteColor : mMinuteColorDim;
        }

        return 0;
    }

    protected void highlightClockInterval(final int from, final int to, final int clockRing) {
        for (int i = from; i < to; i++) {
            RichPath richPath = mNeonClockView.findRichPathByIndex(getClockPathIndex(i, clockRing));
            richPath.setFillColor(getClockColor(clockRing, true));
        }
    }

    protected void dimClockInterval(final int from, final int to, final int clockRing) {
        for (int i = from; i < to; i++) {
            RichPath richPath = mNeonClockView.findRichPathByIndex(getClockPathIndex(i, clockRing));
            richPath.setFillColor(getClockColor(clockRing, false));
        }
    }

    protected void updateClockTime(int hours, int minutes, int seconds) {
        String time = padLeft(hours) + ":" + padLeft(minutes) + ":" + padLeft(seconds);
        mTimeView.setText(time);
    }

    protected void render(long millisUntilFinished) {
        final int[] previousClock = mClock;

        if (millisUntilFinished != mCurrentMillisInFuture) {
            int secondsInFuture = (int) Math.floor(millisUntilFinished / 1000);
            int hours = (int) Math.floor(secondsInFuture / HOUR_IN_SECONDS);
            int hours12 = (int) Math.min(hours, 12);
            int minutes = (int) Math.floor((secondsInFuture - hours * HOUR_IN_SECONDS) / 60);
            int seconds = (int) secondsInFuture % 60;
            int dMinutes = minutes;
            int dSeconds = seconds;

            if (seconds == 0) {
                if (minutes > 0 || hours > 0) {
                    seconds = 60;
                }
                if (minutes == 0 && hours > 0) {
                    minutes = 60;
                }
            }

            mCurrentMillisInFuture = millisUntilFinished;
            mClock = new int[]{seconds, minutes, hours12, hours, dMinutes, dSeconds};
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    int previousClockVal = previousClock[i];
                    int currentClockVal = mClock[i];

                    if (currentClockVal < previousClockVal) {
                        // eg. 45s -> 30s => dimClockInterval(31, 45, i)
                        dimClockInterval(currentClockVal, previousClockVal, i);
                    } else if (currentClockVal > previousClockVal) {
                        // we started new minute/hour/..
                        // eg. 1:05s -> 0:50 =>
                        // eg. 1:45s -> 0:50s =>
                        highlightClockInterval(previousClockVal, currentClockVal, i);
                    }
                }
                updateClockTime(mClock[3], mClock[4], mClock[5]);
            }
        });
    }

    protected void onFinish() {
        // override in child
    }

    protected void onTick(long millisUntilFinished) {
        // override in child
    }

    public static String padLeft(int number) {
        return String.format("%02d", number);
    }

    public void setMillisInFuture(long millisInFuture) {
        mMillisInFuture = millisInFuture;
        render(millisInFuture);
    }

    public void setSecondColor(int color) {
        mSecondColor = color;
        highlightClockInterval(0, mClock[0], RING_SECONDS);
    }

    public void setMinuteColor(int color) {
        mMinuteColor = color;
        highlightClockInterval(0, mClock[1], RING_MINUTES);
    }

    public void setSecondColorDim(int color) {
        mSecondColorDim = color;
        dimClockInterval(mClock[0], 60, RING_SECONDS);
    }

    public void setMinuteColorDim(int color) {
        mMinuteColorDim = color;
        dimClockInterval(mClock[1], 60, RING_MINUTES);
    }

    public void setInterval(int millis) {
        mIntervalMillis = millis;
    }

    public void setTextColor(int color) {
        mTimeView.setTextColor(color);
    }

    public void setTextSize(float size) {
        mTimeView.setTextSize(size);
    }

    public synchronized void startCountDown() {
        if (mIsStarted) {
            return;
        }

        mCountDownTimer = new CountDownTimer(mCurrentMillisInFuture, mIntervalMillis) {
            public void onTick(long millisUntilFinished) {
                render(millisUntilFinished);
                NeonCountDownView.this.onTick(millisUntilFinished);
            }

            public void onFinish() {
                mIsStarted = false;
                mCurrentMillisInFuture = mMillisInFuture;
                NeonCountDownView.this.onFinish();
            }
        }.start();

        mIsStarted = true;
    }

    public synchronized void stopCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mIsStarted = false;
        }
    }
}
