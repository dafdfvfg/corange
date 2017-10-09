package com.example.liang.corange.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liang.corange.R;
import com.example.liang.corange.dialog.AnimationLoader;


/**
 * 自定义dialog
 * Created by liang on 2017/5/18.
 */

public class OperationDialog extends Dialog {

    private Context context;

    private TextView title;

    private LinearLayout content;

    protected Button cancel;
    protected Button ok;

    private EditText editText;
    private TextView textView;

    private OnClick onClick;

    public OperationDialog(Context context) {
        super(context);
        this.context = context;
        initView();

    }


    public OperationDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    protected OperationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        initView();
    }

    protected void initView() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_operation);
        title = (TextView) findViewById(R.id.dialog_title);
        content = (LinearLayout) findViewById(R.id.dialog_content);
        cancel = (Button) findViewById(R.id.dialog_cancel);
        ok = (Button) findViewById(R.id.dialog_ok);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    onClick.cancel();
                }
                cancel();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    String content = "";
                    if (editText != null) {
                        content = editText.getText().toString();
                    }
                    onClick.ok(content);
                }
                cancel();
            }
        });
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (editText != null) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        this.setCanceledOnTouchOutside(false);
    }

    public OperationDialog setOpTitle(String text) {
        title.setText(text);
        return this;
    }

    public OperationDialog setOpTitle(int id) {
        title.setText(id);
        return this;
    }

    public OperationDialog setCancel(String text) {
        cancel.setText(text);
        return this;
    }

    public OperationDialog setCancel(int id) {
        cancel.setText(id);
        return this;
    }

    public OperationDialog setOk(String text) {
        ok.setText(text);
        return this;
    }

    public OperationDialog setOk(int id) {
        ok.setText(id);
        return this;
    }

    /**
     * 隐藏取消按钮
     */
    public void hideCancleBtn() {
        if (cancel.getVisibility() == View.VISIBLE) {
            cancel.setVisibility(View.GONE);
        }

    }

    /**
     * 隐藏ok按钮
     */
    public void hideOkBtn() {
        if (ok.getVisibility() == View.VISIBLE) {
            ok.setVisibility(View.GONE);
        }
    }

    public void addEditContentViewHint(String hint) {
        editText = new EditText(context);
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        editText.setTextColor(getContext().getResources().getColor(R.color.c_666666));
        editText.setGravity(Gravity.CENTER);
        editText.setBackgroundDrawable(null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        editText.setLayoutParams(lp);
        content.removeAllViews();
        content.addView(editText);
    }

    public void addEditContentView(String string) {
        editText = new EditText(context);
        editText.setText(string);
        int point = string.lastIndexOf(".");
        if (point == -1) {
            editText.setSelection(0, string.length());
        } else {
            editText.setSelection(0, point);
        }
        editText.requestFocus();

        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        editText.setTextColor(getContext().getResources().getColor(R.color.c_666666));
        editText.setGravity(Gravity.CENTER);
        editText.setBackgroundDrawable(null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        editText.setLayoutParams(lp);
        content.removeAllViews();
        content.addView(editText);
    }

    public String getEditContentView() {
        if (editText != null) {
            return editText.getEditableText().toString();
        }
        return "";
    }

    public void addTextContentView(String text) {
        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(getContext().getResources().getColor(R.color.c_666666));
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        textView.setLayoutParams(lp);
        textView.setText(text);
        content.removeAllViews();
        content.addView(textView);
    }

    public void addOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    public void show() {
        super.show();
    }

    public interface OnClick {
        public void cancel();

        public void ok(String content);
    }

    public EditText getEditText() {
        return editText;
    }

}
