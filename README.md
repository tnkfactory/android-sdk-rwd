# Tnkfactory SDK Rwd

## SDK 설정하기

### 라이브러리 등록

아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.

```java
dependencies {
    implementation 'com.tnkfactory.ad:android-sdk-rwd:x.y.z'
}
```



### Manifest 설정하기

#### Application ID 설정하기

Tnk 사이트에서 앱 등록하면 상단에 App ID 가 나타납니다. 이를 AndroidMenifest.xml 파일의 <application> tag 안에 아래와 같이 설정합니다. 

(*your-application-id-from-tnk-site* 부분을 실제 App ID 값으로 변경하세요.)

```xml
<application>

     ...

    <meta-data android:name="tnkad_app_id" android:value="your-application-id-from-tnk-site" />

</application>
```



#### <uses-permission> tag 설정하기

```xml
<uses-permission android:name="android.permission.INTERNET" />
```



#### <Activity> tag 추가하기

광고 목록을 띄우기 위한 Activity 2개를 <activity>로 아래와 같이 설정합니다. 매체앱인 경우에만 설정하시면 됩니다. 광고만 진행하실 경우에는 설정하실 필요가 없습니다.

```xml
<activity android:name="com.tnkfactory.ad.AdWallActivity" />
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="landscape"/>

<!-- 또는 아래와 같이 설정-->
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="sensorLandscape"/>

<!-- 동영상 세로 화면으로 설정하려면 아래와 같이 설정 -->
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="portrait"/>
```



### Proguard 사용

Proguard를 사용하실 경우 Proguard 설정내에 아래 내용을 반드시 넣어주세요.

```
-keep class com.tnkfactory.** { *;}
```



### 동영상 광고 설정

동영상 광고 적용 시 ACCESS_WIFI_STATE 권한은 필수 설정 권한입니다.

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```



### COPPA 설정 예시

COPPA는 [미국 어린이 온라인 개인정보 보허법](https://www.ftc.gov/tips-advice/business-center/privacy-and-security/children's-privacy{: target="_blank"}) 및 관련 법규입니다. 구글 에서는 앱이 13세 미만의 아동을 대상으로 서비스한다면 관련 법률을 준수하도록 하고 있습니다. 연령에 맞는 광고가 보일 수 있도록 아래의 옵션을 설정하시기 바랍니다.

```java
TnkSession.setCOPPA(MainActivity.this, true);
```





## Publisher API

게시앱(Publisher)을 위한 가이드입니다.

이를 위해서는 Tnk 사이트에서 앱 등록 및 Android프로젝트의 [SDK 설정하기](##SDK-설정하기)가 우선 선행되어야합니다.



Tnk의 SDK를 적용하여 게시앱을 구현하는 것은 크게 3단계로 이루어집니다.

1) Tnk 사이트에서 앱 등록 및 매체 정보 등록

2) 앱 내에 Tnk 충전소로 이동하는 버튼 구현

3) 사용자가 충전한 포인트 조회 및 사용

