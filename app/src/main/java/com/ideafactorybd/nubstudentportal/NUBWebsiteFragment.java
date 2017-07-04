package com.ideafactorybd.nubstudentportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class NUBWebsiteFragment extends Fragment {

    View view;
    WebView nubWebsiteWebview;
    ProgressBar pbLoadNUBWebsite;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_nubwebsite, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nubWebsiteWebview = (WebView) view.findViewById(R.id.webview_nub_website);
        pbLoadNUBWebsite = (ProgressBar) view.findViewById(R.id.pb_load_nub_website);

        WebSettings webSettings = nubWebsiteWebview.getSettings();
        nubWebsiteWebview.loadUrl("http://nub.ac.bd/");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        nubWebsiteWebview.setWebViewClient(new MyWebViewClient());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("NUBWebsiteFragment", Uri.parse(url).getHost());
            if (Uri.parse(url).getHost().equals("nub.ac.bd") || Uri.parse(url).getHost()
                    .equals("www.nub.ac.bd") || Uri.parse(url).getHost().equals("182.160.97.196")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pbLoadNUBWebsite.setVisibility(View.INVISIBLE);
        }
    }
}
