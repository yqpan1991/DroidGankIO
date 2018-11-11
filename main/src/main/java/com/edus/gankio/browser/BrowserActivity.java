package com.edus.gankio.browser;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edus.gankio.R;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import org.w3c.dom.Text;

public class BrowserActivity extends AppCompatActivity {

    private static final int  MENU_ITEM_GROUP_ID = 1;

    private static final int  MENU_ITEM_BROWSER_REFRESH = 1;
    private static final int  MENU_ITEM_OPEN_WITH_SYS_BROWSER = 2;
    public static final String INTENT_KEY_URL = "INTENT_KEY_URL";
    public static final String INTENT_KEY_HTML_PART = "INTENT_KEY_HTML_PART";

    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private AlertDialog mAlertDialog;
    private String mUrl;
    private String mHtmlPart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        parseIntent();
        initView();
        initData();
    }

    private void parseIntent() {
        mUrl = getIntent().getStringExtra(INTENT_KEY_URL);
        mHtmlPart = getIntent().getStringExtra(INTENT_KEY_HTML_PART);

    }

    private void initView() {
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        mTitleTextView = (TextView) this.findViewById(R.id.toolbar_title);
        this.setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowserActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(MENU_ITEM_GROUP_ID
                , MENU_ITEM_BROWSER_REFRESH
                , MENU_ITEM_BROWSER_REFRESH, R.string.dg_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(MENU_ITEM_GROUP_ID
                , MENU_ITEM_OPEN_WITH_SYS_BROWSER
                , MENU_ITEM_OPEN_WITH_SYS_BROWSER, R.string.dg_open_with_system_browser)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == MENU_ITEM_OPEN_WITH_SYS_BROWSER){
            openSystemBrowser();
            return true;
        }else if(item.getItemId() == MENU_ITEM_BROWSER_REFRESH){
            handleRefresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleRefresh() {
        if(mAgentWeb != null){
            mAgentWeb.getUrlLoader().reload();
        }
    }

    private void openSystemBrowser() {
        if(mAgentWeb != null){
            openBrowser(mAgentWeb.getWebCreator().getWebView().getUrl());
        }
    }

    public static Intent makeIntent(Context context, String url){
        if(context == null){
            return null;
        }
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), BrowserActivity.class);
        intent.putExtra(BrowserActivity.INTENT_KEY_URL, url);
        return intent;
    }

    public static void startMe(Context context, String htmlPart){
        if(context == null){
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), BrowserActivity.class);
        intent.putExtra(BrowserActivity.INTENT_KEY_HTML_PART, htmlPart);
        context.startActivity(intent);
    }


    private void initData() {
        long p = System.currentTimeMillis();

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(getUrl());

        if(TextUtils.isEmpty(getUrl()) && !TextUtils.isEmpty(mHtmlPart)){
            mAgentWeb.getWebCreator().getWebView().getSettings().setSupportZoom(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setBuiltInZoomControls(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setUseWideViewPort(true);
            mAgentWeb.getWebCreator().getWebView().getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            mAgentWeb.getWebCreator().getWebView().getSettings().setLoadWithOverviewMode(true);
            mAgentWeb.getWebCreator().getWebView().loadData(mHtmlPart, "text/html;charset=utf-8", "utf-8");
        }

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (mTitleTextView != null) {
                mTitleTextView.setText(title);
            }
        }
    };

    public String getUrl() {
        return mUrl;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "onResult:" + requestCode + " onResult:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    private void openBrowser(String targetUrl) {
        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            Toast.makeText(this, targetUrl + " 该链接无法使用浏览器打开。", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri mUri = Uri.parse(targetUrl);
            intent.setData(mUri);
            startActivity(intent);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


}
