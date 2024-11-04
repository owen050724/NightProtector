package com.dusdhfkd.nightprotecterv2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Police extends AppCompatActivity {

    private WebView webView = null;
    private static final String URL_DAUM_MAP = "http://m.map.daum.net/";
    private static final String URL_NAVER_MAP = "http://m.map.naver.com/";
    private static final String TAG = Police.class.getSimpleName();
    private static final int MY_PERMISSION_REQUEST_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        Toast.makeText(Police.this, "홈 화면으로 돌아가려면 뒤로가기 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();


        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());  // 새 창 띄우기 않기
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setLoadWithOverviewMode(true);  // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
        webView.getSettings().setUseWideViewPort(true);  // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

        webView.getSettings().setSupportZoom(false);  // 줌 설정 여부
        webView.getSettings().setBuiltInZoomControls(false);  // 줌 확대/축소 버튼 여부

        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용여부
//        webview.addJavascriptInterface(new AndroidBridge(), "android");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        webView.getSettings().setSupportMultipleWindows(true); // 멀티 윈도우 사용 여부

        webView.getSettings().setDomStorageEnabled(true);  // 로컬 스토리지 (localStorage) 사용여부


        //웹페이지 호출
        webView.loadUrl("https://m.map.kakao.com/");

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }
        });

        Button imageButton = (Button) findViewById(R.id.policeoffice);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                webView.loadUrl("https://m.map.kakao.com/actions/searchView?q=%EA%B2%BD%EC%B0%B0%EC%84%9C&wxEnc=MOOUUP&wyEnc=QNMMPRN&lvl=7#!/all/map/place");
            }
        });

        Button imageButton1 = (Button) findViewById(R.id.hospita);
        imageButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                webView.loadUrl("https://m.map.kakao.com/actions/searchView?q=%EB%B3%91%EC%9B%90&wxEnc=MONROM&wyEnc=QNMMOMS&lvl=5#!/all/map/place");
            }
        });

        Button imageButton2 = (Button) findViewById(R.id.firestation);
        imageButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                webView.loadUrl("https://m.map.kakao.com/actions/searchView?q=%EC%86%8C%EB%B0%A9%EC%84%9C&wxEnc=LVQNPP&wyEnc=QNMMORS&lvl=4#!/all/map/place");
            }
        });

    }

}