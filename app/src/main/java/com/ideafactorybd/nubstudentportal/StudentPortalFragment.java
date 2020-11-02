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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class StudentPortalFragment extends Fragment {

    View view;
    static WebView studentPortalWebview;
    ProgressBar pbLoadStudentPortal;
    AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_student_portal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        studentPortalWebview = (WebView) view.findViewById(R.id.webview_student_portal);
        pbLoadStudentPortal = (ProgressBar) view.findViewById(R.id.pb_load_student_portal);
        WebSettings webSettings = studentPortalWebview.getSettings();
        studentPortalWebview.loadUrl("http://182.160.97.196:8088/studentportal/");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        studentPortalWebview.setWebViewClient(new MyWebViewClient());

        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
            .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("StudentPortalFragment", Uri.parse(url).getHost());
            if (Uri.parse(url).getHost().equals("182.160.97.196")) {
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
            pbLoadStudentPortal.setVisibility(View.INVISIBLE);
        }
    }

    public static boolean canGoBack(){
        return studentPortalWebview.canGoBack();
    }

    public static void goBack(){
        studentPortalWebview.goBack();
    }
}
