package jp.radiumsoftware.unityplugin.admob;

import com.google.ads.*;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class AdBannerController {
    static final int bannerViewId = 0x661ad306; // "ggl admob"
    
    static public void tryCreateBanner(final Activity activity, final String publisher, final String testDevice) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                AdView adBanner = (AdView)activity.findViewById(bannerViewId);
                if (adBanner == null) {
                    Log.d("AdMobPlugin", "creates an ad banner.");
                    // Make a layout for ad banner.
                    RelativeLayout layout = new RelativeLayout(activity);
                    activity.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    layout.setGravity(Gravity.BOTTOM);
                    // Make a banner.
                    adBanner = new AdView(activity, AdSize.BANNER, publisher);
                    adBanner.setId(bannerViewId);
                    layout.addView(adBanner);
                }
                // Request an ad for the banner.
                Log.d("AdMobPlugin", "requests an ad.");
                AdRequest adRequest = new AdRequest();
                if (testDevice != null) adRequest.addTestDevice(testDevice);
                adBanner.loadAd(adRequest);
            }
        });
    }
}
