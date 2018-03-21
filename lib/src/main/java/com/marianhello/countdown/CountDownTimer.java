package com.marianhello.countdown;

import android.os.SystemClock;

/**
 * Inspired with Samet's
 * https://stackoverflow.com/a/40687509/3896616
 */

public abstract class CountDownTimer {

    private final long mMillisInFuture;
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    private Thread mThread;
    private boolean mCanceled = false;

    public CountDownTimer(long millisInFuture, long countdownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countdownInterval;
    }

    public abstract void onTick(long millisUntilFinished);

    public abstract void onFinish();

    public synchronized final CountDownTimer start() {
        mCanceled = false;
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;

        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // Do in mThread
                long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                while (millisLeft > 0) {
                    if (mCanceled) {
                        break;
                    }
                    onTick(millisLeft);
                    try {
                        mThread.sleep(mCountdownInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                }

                if (!mCanceled) {
                    onFinish();
                }
            }

        });

        mThread.start();

        return this;
    }

    public synchronized void cancel() {
        mCanceled = true;
    }
}