### 概要

Unity 3.5 より追加された ADBannerView クラスは、今のところ iAd にしか対応していません。 Android では依然として外部プラグインを使用する必要があります。このプロジェクトは外部プラグインを使用して iOS と Android の両方でバナー広告を表示する仕組みの実装例を提示するものです。

![iOS](https://github.com/downloads/keijiro/unity-adbanner-example/ss_ios.png) ![Android](https://github.com/downloads/keijiro/unity-adbanner-example/ss_android.png)

### 仕組み

[ADBanner.cs](https://github.com/keijiro/unity-adbanner-example/blob/master/Assets/Scripts/ADBanner.cs) の中にバナー広告の制御が記述されています。 iOS では単に ADBannerView を使って iAd をロード・表示しているだけです。 Android ではプラグインを1秒毎に呼び出してロード・表示を試行しています。1秒毎に行っているのは、アクティビティが一旦バックグラウンドに入ってから復帰した場合にバナーも復帰できるようにするためです。

実際のゲームプロジェクトにおいては、このスクリプトがシーン切り替え時に消滅しないようにする必要があります。このスクリプトに DontDestroyOnLoad を与えて存続させるか、あるいは全シーンにこのスクリプトを配置する、等々の方法が考えられます。

### Android 側の構成

Plugins/Android ディレクトリの中に AdMobPlugin.jar があります。これがプラグイン本体です。ただしこのプラグインを使用するには、同時に AdMob の jar ファイルも入れておく必要があります。このプロジェクトでは GoogleAdMobAdsSdk-4.3.1.jar というファイル名で入れてあります。

また、アクティビティとパーミッションの追加を行うために AndroidManifest.xml も含める必要があります。既に AndroidManifest.xml の置き換えを行っている場合は結合してください。

#### 注意

起動時のアクティビティを UnityPlayerNativeActivity にしてしまうと、バナー広告にタッチイベントが届かなくなるという弊害が発生する模様です。この問題に対処するため、この AndroidManifest.xml においては UnityPlayerActivity を起動アクティビティにするという変更を施してあります。独自に AndroidManifest.xml を記述する際は注意してください。

### Android 側のソースコード

AdMobPlugin.jar のソースコードは AndroidPlugin ディレクトリに格納してあります（Antプロジェクト）。ほとんどの場合は AdMobPlugin.jar のリビルドを行う必要は無いはずですが、何らかの問題が発生しリビルドが必要になった場合は、これを利用してください。
