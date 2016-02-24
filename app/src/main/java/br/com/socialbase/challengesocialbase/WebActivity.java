package br.com.socialbase.challengesocialbase;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import socialbase.com.br.challengesocialbase.R;
import br.com.socialbase.challengesocialbase.util.ConnectionUtil;
import br.com.socialbase.challengesocialbase.util.Constants;

public class WebActivity extends BaseActivity implements Constants{

    private WebView webView;
    private SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setActionBar(getIntent().getStringExtra(KEY_TITLE));
        setHomeButtonEnable(true);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeLayout.setColorSchemeResources(R.color.colorPrimary);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        String url = getIntent().getStringExtra(KEY_URL);

        if (ConnectionUtil.isConnected(WebActivity.this)) {
            setWebview(url);
        } else {
            ConnectionUtil.generateDialog(WebActivity.this, getString(R.string.error), getString(R.string.connection_error));
        }

    }

    private void setWebview(final String url) {
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });

        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                if (swipeLayout != null && swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }
        });
    }
}
