package jp.radiumsoftware.unityplugin.admob;

import com.unity3d.player.UnityPlayerActivity;
import com.unity3d.player.UnityPlayer;

import com.google.ads.*;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class AdBannerController {
    static final int bannerViewId = 0x661ad306; // ggl admob

    // バナーの存在を確認する。
    static public boolean checkBanner() {
        return UnityPlayer.currentActivity.findViewById(bannerViewId) != null;
    }

    // バナーの生成を試行する。
    //
    // バナーの存在を確認したうえで、存在しない場合のみ生成する。
    // 組み込みやすさを重視して非効率な実装となっており、頻繁に実行することは推奨されない。
    // シーン切り替え時のみ実行する、あるいは数秒おきに実行する等の配慮が望ましい。
    static public void tryCreateBanner(final String publisher, final String testDevice) {
        if (!checkBanner()) {
            UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (!checkBanner()) {
                        Log.d("AdMobPlugin", "creates an ad banner.");
                        // レイアウトをでっち上げる。
                        RelativeLayout layout = new RelativeLayout(UnityPlayer.currentActivity);
                        UnityPlayer.currentActivity.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                        layout.setGravity(Gravity.BOTTOM);
                        // バナー広告を配置。
                        AdView adBanner = new AdView(UnityPlayer.currentActivity, AdSize.BANNER, publisher);
                        adBanner.setId(bannerViewId);
                        layout.addView(adBanner);
                        // テストデバイスの指定付きで広告をリクエストする。
                        AdRequest adRequest = new AdRequest();
                        if (testDevice != null) adRequest.addTestDevice(testDevice);
                        adBanner.loadAd(adRequest);
                    }
                }
            });
        }
    }
} 
