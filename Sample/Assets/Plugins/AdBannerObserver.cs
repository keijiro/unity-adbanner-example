using UnityEngine;
using System.Collections;

public class AdBannerObserver : MonoBehaviour {
    private static AdBannerObserver sInstance;
    
    public static void Initialize() {
        Initialize(null, null, 0.0f);
    }
    
    public static void Initialize(string publisherId, string testDeviceId, float refresh) {
        if (sInstance == null) {
            // Make a game object for observing.
            GameObject go = new GameObject("_AdBannerObserver");
            go.hideFlags = HideFlags.HideAndDontSave;
            DontDestroyOnLoad(go);
            // Add and initialize this component.
            sInstance = go.AddComponent<AdBannerObserver>();
            sInstance.mAdMobPublisherId = publisherId;
            sInstance.mAdMobTestDeviceId = testDeviceId;
            sInstance.mRefreshTime = refresh;
        }
    }
    
    public string mAdMobPublisherId;
    public string mAdMobTestDeviceId;
    public float mRefreshTime;
    
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
#elif UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass plugin = new AndroidJavaClass("jp.radiumsoftware.unityplugin.admob.AdBannerController");
        AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
        while (true) {
            plugin.CallStatic("tryCreateBanner", activity, mAdMobPublisherId, mAdMobTestDeviceId);
            yield return new WaitForSeconds(Mathf.Max(30.0f, mRefreshTime));
        }
#else
        return null;
#endif
    }
}
