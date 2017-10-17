package com.example.liang.corange.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liang.corange.R;
import com.example.liang.corange.common.Configure;
import com.example.liang.corange.ui.BaseFragment;
import com.example.liang.corange.ui.BaseFragmentActivity;
import com.example.liang.corange.utils.getStatusBarHeight;


/**
 * Created by liang on 2017/4/5.
 * 功能:
 */
public class TitleView extends FrameLayout implements View.OnClickListener {

    protected TextView title_tv, title_right_tv, title_left_tv;
    protected FrameLayout title_left_fl, title_right_fl;
    protected ImageView title_left_iv, title_right_iv;
    protected RelativeLayout title_ll;

    public TitleView(Context context) {
        super(context);
        init(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * It's good for viewgroup,not for view.U can put view in framelayout.
     */
    public static void setViewPadding(Context context, View... views) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Configure.barHeight == 0) {
            new getStatusBarHeight().getStatusBarHeight(context);
        }
        //		if(null==views || views.length==0){
        //
        //		}
        for (View v : views) {
            if (null == v)
                continue;
            v.setPadding(0, Configure.barHeight, 0, 0);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_title_bar, this);

        title_ll = (RelativeLayout) findViewById(R.id.title_ll);
        title_tv = (TextView) findViewById(R.id.title_tv);
        title_left_fl = (FrameLayout) findViewById(R.id.title_left_fl);
        title_left_iv = (ImageView) findViewById(R.id.title_left_iv);
        title_right_fl = (FrameLayout) findViewById(R.id.title_right_fl);
        title_right_iv = (ImageView) findViewById(R.id.title_right_iv);
        title_right_tv = (TextView) findViewById(R.id.title_right_tv);
        title_left_tv = (TextView) findViewById(R.id.title_left_tv);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

            String leftText = a.getString(R.styleable.TitleView_leftText);
            String rightText = a.getString(R.styleable.TitleView_rightText);
            String titleText = a.getString(R.styleable.TitleView_titleText);
            Drawable leftDrawable = a.getDrawable(R.styleable.TitleView_leftDrawable);
            Drawable rightDrawable = a.getDrawable(R.styleable.TitleView_rightDrawable);
            if (leftText != null) {
                title_left_tv.setText(leftText);
            }
            if (rightText != null) {
                title_right_tv.setText(rightText);
            }
            if (titleText != null) {
                title_tv.setText(titleText);
            }
            if (leftDrawable != null) {
                title_left_iv.setImageDrawable(leftDrawable);
            }
            if (rightDrawable != null) {
                title_right_iv.setImageDrawable(rightDrawable);
            }

            a.recycle();
        }

        // ClickableImageTouchListener.addTouchDrak(title_left_iv);
        setLeftOnClickListener(this);
        setBackgroundResource(R.color.orange);

        setViewPadding(getContext(), this);
    }

    public void setTitleBackground(int color) {
        setBackgroundResource(color);
        title_ll.setBackgroundResource(color);
    }

    public void setLeftText(String text) {
        title_left_tv.setText(text);
        title_left_tv.setVisibility(VISIBLE);
    }

    public void setLeftText(int res) {
        title_left_tv.setText(res);
    }

    public void setRightText(String text) {
        title_right_tv.setText(text);
    }

    public void setRightText(int res) {
        title_right_tv.setText(res);
    }

    public void setTitleText(String text) {
        title_tv.setText(text);
    }

    public void setTitleText(int res) {
        title_tv.setText(res);
    }

    public void setShowLeft(boolean isShow) {
        if (isShow) {
            title_left_fl.setVisibility(VISIBLE);
        } else {
            title_left_fl.setVisibility(INVISIBLE);
        }
    }

    public void setLeftDrawable(int res) {
        if (res == 0) {
            title_left_iv.setImageDrawable(null);
        }
        title_left_iv.setImageResource(res);
    }

    public void setRightDrawable(int res) {
        title_right_iv.setImageResource(res);
    }

    public void setLeftDrawableVisible(boolean visible) {
        if (visible) {
            title_left_iv.setVisibility(VISIBLE);
        } else {
            title_left_iv.setVisibility(INVISIBLE);
        }
    }

    public void setRightDrawableVisible(boolean visible) {
        if (visible) {
            title_right_iv.setVisibility(VISIBLE);
        } else {
            title_right_iv.setVisibility(INVISIBLE);
        }
    }

    public void setRightTextVisible(boolean visible) {
        if (visible) {
            title_right_tv.setVisibility(VISIBLE);
        } else {
            title_right_tv.setVisibility(INVISIBLE);
        }
    }

    public void setLeftOnClickListener(OnClickListener listener) {
        title_left_fl.setOnClickListener(listener);
    }

    public void setRightOnClickListener(OnClickListener listener) {
        title_right_fl.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        if (v == title_left_fl) {
            if (getContext() instanceof BaseFragmentActivity) {
                BaseFragmentActivity activity = (BaseFragmentActivity) getContext();
                Fragment fragment = activity.getFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null && fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onBackPressed(activity);
                    ((Activity) getContext()).overridePendingTransition(0, 0);
                    return;
                }
            }
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).onBackPressed();
                ((Activity) getContext()).overridePendingTransition(0, 0);
            }
        }
    }
}
