package com.chengfu.android.fuplayer2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArraySet;


public abstract class BaseControlView extends FrameLayout implements PlayerControllerView {

    public interface ProgressUpdateListener {
        void onProgressUpdate(long position, long bufferedPosition);
    }

    private final CopyOnWriteArraySet<StateView.VisibilityChangeListener> visibilityChangeListeners;

    private final CopyOnWriteArraySet<ProgressUpdateListener> progressUpdateListeners;

    public BaseControlView(@NonNull Context context) {
        this(context, null);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        visibilityChangeListeners = new CopyOnWriteArraySet<>();
        progressUpdateListeners=new CopyOnWriteArraySet<>();
    }

    public boolean isFullScreen() {
        return false;
    }

    public void setFullScreen(boolean fullScreen) {

    }

    @Override
    public void addVisibilityChangeListener(StateView.VisibilityChangeListener l) {
        visibilityChangeListeners.add(l);
    }

    @Override
    public void removeVisibilityChangeListener(StateView.VisibilityChangeListener l) {
        visibilityChangeListeners.remove(l);
    }

    public void addProgressUpdateListener(ProgressUpdateListener l) {
        progressUpdateListeners.add(l);
    }

    public void removeProgressUpdateListener(ProgressUpdateListener l) {
        progressUpdateListeners.remove(l);
    }

    /**
     * Dispatch callbacks to {@link #addVisibilityChangeListener} down
     * the view hierarchy.
     */
    protected void dispatchVisibilityChanged(boolean visibility) {
        for (StateView.VisibilityChangeListener listener : visibilityChangeListeners) {
            if (listener != null) {
                listener.onVisibilityChange(this, visibility);
            }
        }
    }

    /**
     * Dispatch callbacks to {@link #addProgressUpdateListener} down
     * the view hierarchy.
     */
    protected void dispatchProgressUpdate(long position, long bufferedPosition) {
        for (ProgressUpdateListener listener : progressUpdateListeners) {
            if (listener != null) {
                listener.onProgressUpdate(position, bufferedPosition);
            }
        }
    }
}
