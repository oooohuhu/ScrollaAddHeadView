package com.jh.customscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jh.customscroll.exception.ParamsErrorException;

/**
 *
 */

public class MyScrollView extends ScrollView {
    private static final String TAG = "MyScrollView";

    private View headerView;
    private ImageView imgView;
    private int imgOriginalHeight;
    private int maxHeight;
    private int minHeight = 0;

    private boolean mScaling = false; // 是否正在放大
    private float downYPoint = 0;

    /**
     * 变化类型
     */
    enum ChangeType {
        /*缩小，放大*/
        Narrow, Enlarge
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setHeaderView(View headerView, ImageView imgView) {
        this.headerView = headerView;
        this.imgView = imgView;
        this.imgOriginalHeight = imgView.getHeight();
        this.maxHeight = headerView.getHeight();
        if (maxHeight <= 0 || minHeight < 0 || minHeight >= maxHeight) {
            throw new ParamsErrorException("参数错误...");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG, "down .. Y == " + ev.getY());
                downYPoint = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: {
                if (getScrollY() == 0) {
                    float distance = ev.getY() - downYPoint;
                    downYPoint = ev.getY();

                    if (distance < 0 && canNarrow()) {
                        measureHeaderView(distance, ChangeType.Narrow);
                        cancelPendingInputEvents();
                        return true;
                    } else if (distance > 0 && canEnlarge()) {
                        measureHeaderView(distance, ChangeType.Enlarge);
                        return true;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "up .. Y == " + ev.getY());
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 可以缩小
     *
     * @return
     */
    private boolean canNarrow() {
        if (headerView == null) {
            return false;
        }
        return headerView.getHeight() > minHeight;
    }

    /**
     * 可以放大
     *
     * @return
     */
    private boolean canEnlarge() {
        if (headerView == null) {
            return false;
        }
        return headerView.getHeight() < maxHeight;
    }

    /**
     * 根据移动的距离，重新计算headerView的高度
     *
     * @param distance
     * @return
     */
    private void measureHeaderView(float distance, ChangeType changeType) {
        ViewGroup.LayoutParams params = headerView.getLayoutParams();
        params.height = headerView.getHeight() + (int) distance;
        if (params.height < minHeight) {
            params.height = minHeight;
        } else if (params.height > maxHeight) {
            params.height = maxHeight;
        } else if (params.height < 0) {
            params.height = 0;
        }

        Log.i(TAG, "headerView.height == " + params.height);
        headerView.setLayoutParams(params);
        measureImageView(params.height);
    }

    /**
     * 重新计算图片大小
     *
     * @param headerViewHeight
     */
    private void measureImageView(int headerViewHeight) {
        if (imgView != null) {
            double multiple = (headerViewHeight * 1.00) / (maxHeight * 1.00);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imgView.getLayoutParams();
            params.height = (int) (imgOriginalHeight * multiple);
            if (params.height > imgOriginalHeight) {
                params.height = imgOriginalHeight;
            }
            imgView.setLayoutParams(params);
        }
    }

}
