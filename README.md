### 概要

Unity 3.5 より追加された ADBannerView クラスは、今のところ iAd にしか対応していません。 Android では依然として外部プラグインを使用する必要があります。このプロジェクトは外部プラグインを使用して iOS と Android の両方でバナー広告を表示する仕組みの実装例を提示するものです。

![iOS](https://github.com/downloads/keijiro/unity-adbanner-example/ss_ios.png) <span /> ![Android](https://github.com/downloads/keijiro/unity-adbanner-example/ss_android.png)

### 使い方

ビルド済みのパッケージを用意しました。**他に Android プラグインを使用していない**場合はこれを問題無く使用できると思います。

[unity-adbanner-plugin-20120420.unitypackage](https://github.com/downloads/keijiro/unity-adbanner-example/unity-adbanner-plugin-20120420.unitypackage)

バナー広告を表示するには AdBannerObserver の Initialize 関数を呼ぶ必要があります。第1引数に AdMob のパブリッシャー ID を、第2引数にテストデバイスの ID を、第3引数に広告リフレッシュの間隔（秒数）を指定します。

    AdBannerObserver.Initialize("a14e4873bd055aa", "test_device_code_here", 60.0);

この関数は何度呼んでも大丈夫ですが、実際に適用されるのは最初の一回のみです。

### 他プラグインとの共存

他に Android プラグインを使用していて、なおかつ、そのプラグインが AndroidManifest.xml の置換を行っている場合、AndroidManifest.xml の統合を手動で行う必要があります。

このプラグインで行っている変更は以下の2点のみです。この変更を統合される側の AndroidManifest.xml に適用してください。

- ForwardNativeEventsToDalvik を true に変更

    <meta-data android:name="unityplayer.ForwardNativeEventsToDalvik" android:value="true" />

- 以下のパーミッションを追加

    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

### リフレッシュ間隔について

リフレッシュ間隔は AdMob の広告制御側でも行うことができますが、そちらの設定は考慮されず、このプラグインによって強制的にリフレッシュが行われます。これは、ネットワークが切断された後に広告のリフレッシュを復帰するための処理と、リフレッシュ処理自体を同じものとして実装しているためです（要するに手抜きです……）。

### Android 側のソースコード

AdMobPlugin.jar のソースコードは AndroidPlugin ディレクトリに格納してあります（Antプロジェクト）。プラグイン自体の改造を行う場合はこちらを利用してください。
