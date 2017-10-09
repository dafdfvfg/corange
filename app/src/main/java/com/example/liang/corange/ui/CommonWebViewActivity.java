package com.example.liang.corange.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.example.liang.corange.R;
import com.example.liang.corange.views.TitleView;


/**
 * 通用webView
 * Created by liang on 2017/4/14.
 */
public class CommonWebViewActivity extends BaseActivity {

    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String URL_KEY = "URL_KEY";

    public static void startMe(Context context, String title, String url) {
        Intent intent = new Intent(context, CommonWebViewActivity.class);
        intent.putExtra(TITLE_KEY, title);
        intent.putExtra(URL_KEY, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview_activity);
        TitleView titleView = (TitleView) findViewById(R.id.title_view);
        WebView webView = (WebView) findViewById(R.id.webview);
        MllWebViewClient client = new MllWebViewClient(this);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(TITLE_KEY)) {
                titleView.setTitleText(intent.getStringExtra(TITLE_KEY));
            }
            if (intent.hasExtra(URL_KEY)) {
                String url = intent.getStringExtra(URL_KEY);
                webView.loadUrl(url);
            }
        }
    }

    /**
     *
     */
    public class MllWebViewClient extends android.webkit.WebViewClient {


        public MllWebViewClient(Context context) {
            super();


        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//		Toast.makeText(context, "MllWebViewClient.onPageStarted页面开始加载", Toast.LENGTH_SHORT).show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//		Toast.makeText(context, "MllWebViewClient.onPageFinished页面加载完成", Toast.LENGTH_SHORT).show();
            super.onPageFinished(view, url);
        }

        /**
         * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
         */
        @Override
        public void onLoadResource(WebView view, String url) {
//		Toast.makeText(context, "MllWebViewClient.onLoadResource", Toast.LENGTH_SHORT).show();
            super.onLoadResource(view, url);
        }

        /**
         * 重写此方法可以让webview处理https请求    [拓展]
         */
        @Override
        public void onReceivedSslError(WebView view,
                                       SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }


    }
}
