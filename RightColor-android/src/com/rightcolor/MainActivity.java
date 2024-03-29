package com.rightcolor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.facebook.Session;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AndroidApplication {
    public static final int BACKGROUND_COLOR = Color.BLACK;
    
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RelativeLayout layout = new RelativeLayout(this);
        layout.setBackgroundColor(BACKGROUND_COLOR);
        
        RelativeLayout.LayoutParams gameViewParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        gameViewParams.bottomMargin = 150;
        
        View gameView = initializeForView(new MainGame(new FacebookAPI(this), new TwitterAPI(this)), cfg);
        layout.addView(gameView, gameViewParams);

        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3293663299631285/8227381454");
        adView.setAdSize(AdSize.BANNER);
        
        RelativeLayout.LayoutParams adParams = 
            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        layout.addView(adView, adParams);
        
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        
        setContentView(layout);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.rightcolor", 
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        }
    }
}