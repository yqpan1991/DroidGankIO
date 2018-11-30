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
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edus.gankio.R;
import com.edus.gankio.ui.adapter.BrowserShareAdapter;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

import java.util.ArrayList;
import java.util.List;

import apollo.edus.com.share.IShareCallback;
import apollo.edus.com.share.ShareInterface;
import apollo.edus.com.share.message.BaseShareMessage;
import apollo.edus.com.share.message.LinkMessage;
import apollo.edus.com.share.message.TextMessage;
import apollo.edus.com.share.unit.IShareUnit;

public class BrowserActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private static final int  MENU_ITEM_GROUP_ID = 1;

    private static final int  MENU_ITEM_BROWSER_REFRESH = 1;
    private static final int  MENU_ITEM_OPEN_WITH_SYS_BROWSER = 2;
    private static final int  MENU_ITEM_SYSTEM_SHARE = 3;
    public static final String INTENT_KEY_URL = "INTENT_KEY_URL";
    public static final String INTENT_KEY_HTML_PART = "INTENT_KEY_HTML_PART";

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_LINK = 1;


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
        menu.add(MENU_ITEM_GROUP_ID
                , MENU_ITEM_SYSTEM_SHARE
                , MENU_ITEM_SYSTEM_SHARE, R.string.dg_share)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
        }else if(item.getItemId() == MENU_ITEM_SYSTEM_SHARE){
            handleShare();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleShare() {
        if(mAgentWeb == null){
            return;
        }
        showSelectDialog();

    }

    private void showSelectDialog() {
        final List<BrowserShareAdapter.SelectItem> selectItemList = new ArrayList<>();
        BrowserShareAdapter.SelectItem selectItem = new BrowserShareAdapter.SelectItem();
        selectItem.type = TYPE_TEXT;
        selectItem.tips = getString(R.string.dg_select_text);
        selectItemList.add(selectItem);
        selectItem = new BrowserShareAdapter.SelectItem();
        selectItem.type = TYPE_LINK;
        selectItem.tips = getString(R.string.dg_select_link);
        selectItemList.add(selectItem);
        BrowserShareAdapter shareAdapter = new BrowserShareAdapter();
        shareAdapter.setDataList(selectItemList);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(shareAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BrowserShareAdapter.SelectItem selectItem1 = selectItemList.get(which);
                if(selectItem1 != null){
                    if(selectItem1.type == TYPE_LINK){
                        handleShareLink();
                    }else if(selectItem1.type == TYPE_TEXT){
                        handleShareText();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void handleShareLink() {
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        String link = webView.getUrl();
        String title = webView.getTitle();
        if(TextUtils.isEmpty(title)){
            title = link;
        }
        String content = null;
        LinkMessage linkMessage = new LinkMessage();
        linkMessage.setUrl(link);
        linkMessage.setTitle(title);
        linkMessage.setContent(content);
        ShareInterface.getImpl().shareLink(this, linkMessage, new IShareCallback() {
            @Override
            public void onStartShare(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                Log.e(TAG, "onStartShare");
            }

            @Override
            public void onShareSucceed(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                Log.e(TAG, "onShareSucceed");
            }

            @Override
            public void onShareFailed(IShareUnit sharePlugin, BaseShareMessage shareMessage, String code, String reason) {
                Log.e(TAG, "onShareFailed");
            }
        });
    }

    private void handleShareText(){

        WebView webView = mAgentWeb.getWebCreator().getWebView();
        String link = webView.getUrl();
        String title = webView.getTitle();
        if(TextUtils.isEmpty(title)){
            title = link;
        }
        TextMessage textMessage = new TextMessage();
        textMessage.setTitle(title);
        textMessage.setText(link);
        ShareInterface.getImpl().shareText(this, textMessage, new IShareCallback() {
            @Override
            public void onStartShare(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                Log.e(TAG, "onStartShare");
            }

            @Override
            public void onShareSucceed(IShareUnit sharePlugin, BaseShareMessage shareMessage) {
                Log.e(TAG, "onShareSucceed");
            }

            @Override
            public void onShareFailed(IShareUnit sharePlugin, BaseShareMessage shareMessage, String code, String reason) {
                Log.e(TAG, "onShareFailed");
            }
        });
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
