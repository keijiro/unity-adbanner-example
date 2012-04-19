### 概要

Unity 3.5 より追加された ADBannerView クラスは、今のところ iAd にしか対応していません。 Android では依然として外部プラグインを使用する必要があります。このプロジェクトは外部プラグインを使用して iOS と Android の両方でバナー広告を表示する仕組みの実装例を提示するものです。

![iOS](https://github.com/downloads/keijiro/unity-adbanner-example/ss_ios.png) <span /> ![Android](https://github.com/downloads/keijiro/unity-adbanner-example/ss_android.png)

### 仕組み

[ADBanner.cs](https://github.com/keijiro/unity-adbanner-example/blob/master/Assets/Scripts/ADBanner.cs) の中にバナー広告の制御が記述されています。 iOS では単に ADBannerView を使って iAd をロード・表示しているだけです。 Android ではプラグインを1秒毎に呼び出してロード・表示を試行しています。定期的に試行するのは、アクティビティが一旦バックグラウンドに入ってから復帰した場合にバナーも復帰できるようにするためです。

実際のゲームプロジェクトにおいては、このスクリプトがシーン切り替え時に消滅しないようにする必要があります。このスクリプトに DontDestroyOnLoad を与えて存続させるか、あるいは全シーンにこのスクリプトを配置する等の対処が考えられます。

### Android 側の構成

Plugins/Android ディレクトリの中に AdMobPlugin.jar があります。これがプラグイン本体です。ただしこのプラグインを使用するには、同時に AdMob SDK の jar ファイルも入れておく必要があります。このプロジェクトでは GoogleAdMobAdsSdk-4.3.1.jar を入れてあります。

また、アクティビティとパーミッションの追加を行うために AndroidManifest.xml も含めておく必要があります。既に AndroidManifest.xml の置き換えを行っている場合は手動で結合編集してください。

### Android 側のソースコード

AdMobPlugin.jar のソースコードは AndroidPlugin ディレクトリに格納してあります（Antプロジェクト）。AdMob SDK のバージョンアップを行う場合などにこちらを利用してください。