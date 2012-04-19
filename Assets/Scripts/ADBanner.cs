using UnityEngine;
using System.Collections;

public class ADBanner : MonoBehaviour {
    IEnumerator Start () {
#if UNITY_IPHONE
        ADBannerView banner = new ADBannerView();
        banner.autoSize = true;
        banner.autoPosition = ADPosition.Bottom;

        while (true) {
            if (banner.error != null) {
                Debug.Log("Error: " + banner.error.description);
                break;
            } else if (banner.loaded) {
                banner.Show();
                break;
            }
            yield return null;
        }
#elif UNITY_ANDROID
        AndroidJavaClass plugin = new AndroidJavaClass("jp.radiumsoftware.unityplugin.admob.AdBannerController");
        AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
        while (true) {
            plugin.CallStatic("tryCreateBanner", activity, "a14e4873bd055aa", "test_device_code_here");
            yield return new WaitForSeconds(30.0f);
        }
#else
        return null;
#endif
    }
}
