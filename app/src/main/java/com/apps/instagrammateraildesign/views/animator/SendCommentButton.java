package com.apps.instagrammateraildesign.views.animator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewAnimator;

import com.apps.instagrammateraildesign.R;

/**
 * Created by Mohamed on 28/01/15.
 */
public class SendCommentButton extends ViewAnimator implements View.OnClickListener {

    public static final int STATE_SEND = 0;
    public static final int STATE_DONE = 1;

    private static final long RESET_STATE_DELAY_MILLIS = 2000;

    private int currentState;

    private onSendClickListner onSendClickListner;

    private Runnable revertStateRunnable = new Runnable() {
        @Override
        public void run() {
            setCurrentState(STATE_SEND);
        }
    };

    public SendCommentButton(Context context) {
        super(context);
        init();
    }

    public SendCommentButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_send_comment_button, this, true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        currentState = STATE_SEND;
        super.setOnClickListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(revertStateRunnable);
        super.onDetachedFromWindow();
    }

    public void setCurrentState(int state) {
        if (state == currentState) {
            return;
        }

        currentState = state;
        if (state == STATE_DONE) {
            setEnabled(false);
            postDelayed(revertStateRunnable, RESET_STATE_DELAY_MILLIS);
            setInAnimation(getContext(), R.anim.slide_in_done);
            setOutAnimation(getContext(), R.anim.slide_out_send);
        } else if (state == STATE_SEND) {
            setEnabled(false);
            setInAnimation(getContext(), R.anim.slide_in_send);
            setOutAnimation(getContext(), R.anim.slide_out_done);
        }
        showNext();

    }

    @Override
    public void onClick(View v) {
        if (onSendClickListner != null) {
            onSendClickListner.onSendClickListner(this);
        }
    }

    public void setOnSendClickListner(onSendClickListner onSendClickListner) {
        this.onSendClickListner = onSendClickListner;
    }

    public interface onSendClickListner {
        public void onSendClickListner(View v);
    }
}
