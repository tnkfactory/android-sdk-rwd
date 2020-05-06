# Tnkfactory SDK Rwd

## 목차

1. [SDK 설정하기](#1-sdk-설정하기)

   * [라이브러리 등록](#라이브러리-등록) 
   * [Manifest 설정하기](#manifest-설정하기)
     * [Application ID 설정하기](#application-id-설정하기)
     * [권한 설정](#권한-설정)
     * [Activity tag 추가하기](#activity-tag-추가하기)
   * [Proguard 사용](#proguard-사용)
   * [COPPA 설정](#coppa-설정)

2. [Publisher API](#2-publisher-api)

   가. [광고 목록 띄우기](#가-광고-목록-띄우기)

   * [유저 식별 값 설정](#유저-식별-값-설정)
     * [Method](#method)
     * [Parameters](#parameters)
   * [광고 목록 띄우기 (Activity)](#광고-목록-띄우기-activity)
     * [Method](#method-1)
     * [Description](#description)
     * [Parameters](#parameters-1)
     * [적용예시](#적용예시)
   * [광고 목록 띄우기 (View)](#광고-목록-띄우기-view)
     * [Method](#method-2)
     * [Description](#description-1)
     * [Parameters](#parameters-2)
     * [적용예시](#적용예시-1)
   * [AdListView](#adlistview)
     * [Method](#method-3)
     * [Parameters](#parameters-3)
     * [Popup Sample](#popup-sample)
     * [Embed Sample](#embed-sample)
   * [Listener 이용하기](#listener-이용하기)

   나. [포인트 조회 및 인출](#나-포인트-조회-및-인출)

      * [TnkSession.queryPoint()](#tnksessionquerypoint)

   * [TnkSession.purchaseItem()](#tnksessionpurchaseitem)
   * [TnkSession.withdrawPoints()](#tnksessionwithdrawpoints)
   * [TnkSession.getEarnPoints()](#tnksessiongetearnpoints)

   다. [그밖의 기능들](#다-그밖의-기능들)

      * [TnkSession.setAdWallListType()](#tnksessionsetadwalllisttype)
   * [TnkSession.queryPublishState()](#tnksessionquerypublishstate)
   * [TnkSession.queryAdvertiseCount()](#tnksessionqueryadvertisecount)
   * [TnkSession.enableLogging()](#tnksessionenablelogging)

   라. [디자인 변경하기](#라-디자인-변경하기)

     * [TnkLayout](#tnklayout)
       * [TnkLayout 객체](#tnklayout-객체)
       * [적용 예시](#적용-예시)
      * [템플릿 디자인 제공](#템플릿-디자인-제공)
           * [사용방법 예시](#사용방법-예시)
           * [템플릿 디자인](#템플릿-디자인)

## 1. SDK 설정하기

### 라이브러리 등록

아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.

[![Download](https://api.bintray.com/packages/tnkfactory/android-sdk/rwd/images/download.svg)](https://bintray.com/tnkfactory/android-sdk/rwd/_latestVersion)

```gradle
dependencies {
    implementation 'com.tnkfactory.ad:ppi:7.03.1'
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

#### 권한 설정

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

동영상 광고 적용 시 **ACCESS_WIFI_STATE** 권한은 필수 설정 권한입니다.

```xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

#### Activity tag 추가하기

광고 목록을 띄우기 위한 Activity 2개를 <activity>로 아래와 같이 설정합니다. 매체앱인 경우에만 설정하시면 됩니다. 광고만 진행하실 경우에는 설정하실 필요가 없습니다.

```xml
<activity android:name="com.tnkfactory.ad.AdWallActivity" />
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="landscape"/>

<!-- 또는 아래와 같이 설정-->
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="sensorLandscape"/>

<!-- 동영상 세로 화면으로 설정하려면 아래와 같이 설정 -->
<activity android:name="com.tnkfactory.ad.AdMediaActivity" android:screenOrientation="portrait"/
```

### Proguard 사용

Proguard를 사용하실 경우 Proguard 설정내에 아래 내용을 반드시 넣어주세요.

```
-keep class com.tnkfactory.** { *;}
```

### COPPA 설정

COPPA는 [미국 어린이 온라인 개인정보 보호법](https://www.ftc.gov/tips-advice/business-center/privacy-and-security/children's-privacy) 및 관련 법규입니다. 구글 에서는 앱이 13세 미만의 아동을 대상으로 서비스한다면 관련 법률을 준수하도록 하고 있습니다. 연령에 맞는 광고가 보일 수 있도록 아래의 옵션을 설정하시기 바랍니다.

```java
TnkSession.setCOPPA(MainActivity.this, true); // ON - 13세 미안 아동을 대상으로 한 서비스 일경우 사용
TnkSession.setCOPPA(MainActivity.this, false); // OFF
```

## 2. Publisher API

게시앱(Publisher)을 위한 가이드입니다.

이를 위해서는 Tnk 사이트에서 앱 등록 및 Android프로젝트의 [SDK 설정하기](#1-sdk-설정하기)가 우선 선행되어야합니다.



Tnk의 SDK를 적용하여 게시앱을 구현하는 것은 크게 3단계로 이루어집니다.

1) Tnk 사이트에서 앱 등록 및 매체 정보 등록

2) 앱 내에 Tnk 충전소로 이동하는 버튼 구현

3) 사용자가 충전한 포인트 조회 및 사용

### 가. 광고 목록 띄우기

<u>테스트 상태에서는 테스트하는 장비를 개발 장비로 등록하셔야 광고목록이 정상적으로 나타납니다.</u>

#### 유저 식별 값 설정

앱이 실행되면 우선 앱 내에서 사용자를 식별하는 고유한 ID를 아래의 API를 사용하시어 Tnk SDK에 설정하시기 바랍니다. 

사용자 식별 값으로는 게임의 로그인 ID 등을 사용하시면 되며, 적당한 값이 없으신 경우에는 Device ID 값 등을 사용할 수 있습니다.

(유저 식별 값이 Device ID 나 전화번호, 이메일 등 개인 정보에 해당되는 경우에는 암호화하여 설정해주시기 바랍니다.) 

유저 식별 값을 설정하셔야 이후 사용자가 적립한 포인트를 개발사의 서버로 전달하는 callback 호출 시에  같이 전달받으실 수 있습니다.

##### Method

- void TnkSession.setUserName(Context context, String userName)

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| context       | 현재 Activity 또는 Context 객체                              |
| userName      | 앱에서 사용자를 식별하기 위하여 사용하는 고유 ID 값 (로그인 ID 등)  길이는 256 bytes 이하입니다. |

#### 광고 목록 띄우기 (Activity)

자신의 앱에서 광고 목록을 띄우기 위하여 TnkSession.showAdList() 함수를 사용합니다. 광고목록을 보여주기 위하여 새로운 Activity를 띄웁니다.

##### Method

- void TnkSession.showAdList(Activity activity)

- void TnkSession.showAdList(Activity activity, String title)

- void TnkSession.showAdList(Activity activity, String title, TnkLayout userLayout)

##### Description

광고 목록 화면 (AdWallActivity)를 화면에 띄웁니다. 

반드시 Main UI Thread 상에서 호출하여야 합니다.

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| context       | 현재 Activity 객체                                           |
| title         | 광고 리스트의 타이틀을 지정함  (기본값 : 무료 포인트 받기)   |
| userLayout    | 원하는 Layout을 지정할 수 있습니다. 자세한 내용은  [[디자인 변경하기](#라-디자인-변경하기)] 내용을 참고해주세요. |

##### 적용예시

```java
@Override

public void onCreate(Bundle savedInstanceState) {

    ...

    final Button button = (Button)findViewById(R.id.main_ad);

    button.setOnClickListener(new OnClickListener() {

        @Override

        public void onClick(View v) {

            TnkSession.showAdList(MainActivity.this,"Your title here");

        }

    });
}
```

#### 광고 목록 띄우기 (View)

광고 목록을 현재 화면에 팝업으로 띄우기 위하여 TnkSession.popupAdList() 함수를 사용합니다. 광고목록을 보여주기 위하여 AdListView를 생성하여 현재 화면에 팝업형태로 띄워줍니다.

##### Method

- void TnkSession.popupAdList(Activity activity)

- void TnkSession.popupAdList(Activity activity, String title)

- void TnkSession.popupAdList(Activity activity, String title, TnkAdListener listener)

- void TnkSession.popupAdList(Activity activity, String title, TnkAdListener listener, TnkLayout userLayout)

  Description


##### Description

광고 목록 화면 (AdListView)를 현재 화면에 팝업형태로 띄웁니다.

반드시 Main UI Thread 상에서 호출하여야 합니다.

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| activity      | 현재 Activity 객체                                           |
| title         | 광고 리스트의 타이틀을 지정함  (기본값 : 무료 포인트 받기)   |
| listnener     | TnkAdListener 객체. 자세한 내용은 아래 [[Listener 이용하기](#listener-이용하기)] 내용을 참고해주세요. |
| userLayout    | 원하는 Layout을 지정할 수 있습니다. 자세한 내용은  [[디자인 변경하기](#라-디자인-변경하기)] 내용을 참고해주세요. |

##### 적용예시

```java
@Override

public void onCreate(Bundle savedInstanceState) {

    ...

    final Button button = (Button)findViewById(R.id.main_ad);

    button.setOnClickListener(new OnClickListener() {

        @Override

        public void onClick(View v) {

            TnkSession.popupAdList(MainActivity.this,"Your title here");

        }

    });
}
```

#### AdListView

AdListView는 보상형 광고목록을 제공하는 View 객체입니다. 개발자는 createAdListView() 메소드를 사용하여 AdListView 객체를 생성할 수 있습니다.

생성된 AdListView 객체를 현재 Activity에 팝업형태로 띄우거나 자신의 구성한 화면의 하위 View로 추가(addView) 할 수 있습니다.

##### Method

- AdListView TnkSession.createAdListView(Activity activity, boolean popupStyle)
- AdListView TnkSession.createAdListView(Activity activity, TnkLayout userLayout)
- AdListView TnkSession.createAdListView(Activity activity, boolean popupStyle, TnkAdListener listener)
- AdListView TnkSession.createAdListView(Activity activity, TnkLayout userLayout, TnkAdListener listener)
- AdListView TnkSession.createAdListView(Activity activity, TnkLayout userLayout, boolean popupStyle, TnkAdListener listener)

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| activity      | 현재 Activity 객체                                           |
| popupStyle    | 생성되는 AdListView 화면을 팝업 화면 형태(true) 또는 전체 화면 형태(false)로 지정합니다. |
| listnener     | TnkAdListener 객체. 자세한 내용은 아래 [[Listener 이용하기](#listener-이용하기)] 내용을 참고해주세요. |
| userLayout    | 원하는 Layout을 지정할 수 있습니다. 자세한 내용은  [[라. 디자인 변경하기](#라-디자인-변경하기)] 내용을 참고해주세요. |

아래의 메소드들은 AdListView에서 제공하는 기능들입니다.

###### void loadAdList()

- 광고목록을 서버에서 가져와 화면에 뿌려줍니다.
- 주로 AdListView를 하위 View로 추가하는 경우에 사용합니다. 


###### void show(Activity activity)

- AdListView를 현재 Activity의 최상위 View로 팝업형태로 띄워줍니다. 
- 내부적으로는 activity의 addContentView() 를 사용합니다.
- 화면에 나타날때에 Animation 효과가 적용됩니다.  아래의 setAnimationType() 메소드를 참고하세요.
- 화면에 나타난 후에는 내부적으로 loadAdList()가 호출되어 바로 광고목록이 나타납니다.

###### void setTitle(String title)

- 광고목록 상단 타이틀을 설정합니다.


###### void setListener(TnkAdListener listener)

- AdListView 팝업 화면이 나타날때와 사라질때의 event를 받기 위하여 TnkAdListener 객체를 설정합니다.

- show() 메소드를 사용할 때에만 적용됩니다.

- 자세한 내용은 하단의 TnkAdListener 내용을 참고해주세요.

###### void setAnimationType(int showType, int hideType)

- AdListView를 화면에 팝업으로 띄울 때 사용하는 애니메이션을 지정합니다.
- 나타날 때(showType)와 사라질 때(hideType)을 별도로 지정합니다.
- show() 메소드를 사용할 때에만 적용됩니다.
- 사용가능한 Animation의 종류들은 아래와 같습니다.

| 값                          | 나타날때                                        | 사라질때                                        |
| --------------------------- | ----------------------------------------------- | ----------------------------------------------- |
| TnkSession.ANIMATION_RANDOM | 임의의 애니메이션이 적용됩니다.                 | 임의의 애니메이션이 적용됩니다.                 |
| TnkSession.ANIMATION_NONE   | 애니메이션이 적용되지 않습니다.                 | 애니메이션이 적용되지 않습니다.                 |
| TnkSession.ANIMATION_ALPHA  | 서서히 화면에 나타납니다.                       | 서서히 화면에서 사라집니다.                     |
| TnkSession.ANIMATION_BOTTOM | 아래에서 위로 슬라이드되어 나타납니다.          | 아래로 슬라이드되어 사라집니다.                 |
| TnkSession.ANIMATION_TOP    | 화면 위에서 아래로 슬라이드되어 나타납니다.     | 화면 위로 슬라이드되어 사라집니다.              |
| TnkSession.ANIMATION_LEFT   | 화면 왼쪽에서 슬라이드되어 나타납니다.          | 화면 왼쪽으로 슬라이드되어 사라집니다.          |
| TnkSession.ANIMATION_RIGHT  | 화면 오른쪽에서 슬라이드되어 나타납니다.        | 화면 오른쪽으로 슬라이드되어 사라집니다.        |
| TnkSession.ANIMATION_SPIN   | 화면 중앙에서 빙빙돌면서 커지면서 나타납니다.   | 화면 중앙에서 빙빙돌면서 작아지면서 사라집니다. |
| TnkSession.ANIMATION_FLIP   | 화면 왼쪽에서부터 뒤집히는 방식으로 나타납니다. | 화면 오른쪽으로 뒤집히면서 사라집니다.          |

##### Popup Sample

```java
AdListView adlistView = TnkSession.createAdListView(MainActivity.this, true);
adlistView.setListener(new TnkAdListener() {
  @Override
  public void onClose(int type) {
    Log.d("tnkad", "#### onClose " + type);
  }

  @Override
  public void onShow() {
    Log.d("tnkad", "#### onShow "); 
  }

  @Override
  public void onFailure(int errCode) {
  }

  @Override
  public void onLoad() {
  }
});

adlistView.setTitle("Get Free Coins!!");

adlistView.setAnimationType(TnkSession.ANIMATION_BOTTOM, TnkSession.ANIMATION_BOTTOM);

adlistView.show(MainActivity.this);
```

##### Embed Sample

```java
AdListView adlistView = TnkSession.createAdListView(MainActivity.this, true);
adlistView.setTitle("Get Free Coins!!");

ViewGroup viewGroup = (ViewGroup)findViewById(R.id.adlist);
viewGroup.addView(adlistView);

adlistView.loadAdList();
```

#### Listener 이용하기

AdListView를 팝업화면으로 화면에 띄울 경우 화면이 나타나는 시점과 화면이 닫히는 시점을 알고 싶을 때 아래의 TnkAdListener 인터페이스를 사용합니다.

TnkAdListener는 원래 전면 광고([Interstitial Ad](http://docs.tnkad.net/tnk-interstitial-ad) 참고)에서 사용되지만 광고 리스트 화면에서도 화면이 팝업형태로 나타날 때 (onShow)와 닫힐 때(onClose)의 이벤트를 받기 위하여 사용될 수 있습니다.

##### TnkAdListener Interface

```java
 // 사용자가 닫기버튼이나 Back key를 눌러서 광고화면을 닫은 경우
 public static final int CLOSE_SIMPLE = 0;

 // 사용자가 광고를 클릭해서 화면이 닫히는 경우
 public static final int CLOSE_CLICK = 1;

 // 종료시 띄워주는 광고화면에서 종료 버튼을 클릭해서 닫은 경우
 public static final int CLOSE_EXIT = 2;

 public static final int FAIL_NO_AD = -1;  // no ad available
 public static final int FAIL_NO_IMAGE = -2; // ad image not available
 public static final int FAIL_TIMEOUT = -3; // ad arrived after 5 secs.
 public static final int FAIL_CANCELED = -4; // ad frequency settings 

 public static final int FAIL_SYSTEM = -9;

 /**
  * 팝업 화면이 닫힐 때 호출됩니다. 
  * 화면이 닫히는 이유를 파라메터로 전달해 줍니다.
  * @param type 
  */
 public void onClose(int type);


 /**
  * 팝업 화면이 나타나는 시점에 호출됩니다.
  */
 public void onShow();

 public void onFailure(int errCode);

 public void onLoad();
}
```

AdListView와 관련되어 TnkAdListener에서 발생하는 이벤트의 내용은 아래와 같습니다.

- onClose(int type) : 팝업 화면이 닫히는 시점에 호출됩니다. 화면이 닫히는 이유가 type 파라메터로 전달됩니다.
- - CLOSE_SIMPLE (0) : 사용자가 전면 화면의 닫기 버튼이나 Back 키를 눌러서 닫은 경우입니다.
  - CLOSE_CLICK (1) : 사용자가 전면 화면의 광고를 클릭하여 해당 광고로 이동하는 경우 입니다.
- onShow() : 팝업화면이 나타날 때 호출됩니다.
- AdListView에서는 위 2가지 이외의 이벤트는 발생하지 않습니다.

### 나. 포인트 조회 및 인출

사용자가 광고참여를 통하여 획득한 포인트는 Tnk서버에서 관리되거나 앱의 자체서버에서 관리될 수 있습니다.

포인트가 Tnk 서버에서 관리되는 경우에는 다음의 포인트 조회 및 인출 API를 사용하시어 필요한 아이템 구매 기능을 구현하실 수 있습니다.

#### TnkSession.queryPoint()

Tnk서버에 적립되어 있는 사용자 포인트 값을 조회합니다. 

동기 방식과 비동기 방식 2가지 호출 방식을 제공하고 있으며 화면 멈춤 현상이 없도록 구현하기 위해서는 비동기 방식을 사용할 것을 권장합니다.

다만 Main UI Thread 가 아닌 별도 Thread를 생성하여 호출하는 경우 (주로 게임 앱)에는 비동기 방식을 사용할 수 없으므로 별도 Thread를 생성하여 동기 방식으로 호출하셔야 합니다.

##### [비동기로 호출하기]

###### Method 

  - void TnkSession.queryPoint(Context context, boolean showProgress, ServiceCallback callback)

###### Description

Tnk 서버에 적립되어 있는 사용자 포인트 값을 조회합니다. 비동기 방식으로 호출되며 결과를 받으면 callback이 호출됩니다. Main UI Thread 상에서만 호출이 가능합니다.
ServiceCallback의 사용법은 아래 적용예시를 참고해주세요.

###### Parameters

| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| context       | 현재 Activity 또는 Context 객체                              |
| showProgress  | 서버에서 결과가 올때까지 화면에 progress dialog를 띄울지 여부를 지정 |
| callback      | 서버에서 결과가 오면 callback 객체의 OnReturn(Context context, Object result) 메소드가 호출됩니다. 메소드 호출은 Main UI Thread 상에서 이루어 집니다. 전달된 result 객체는 Integer 객체이며 사용자 포인트가 담겨 있습니다. |

###### 적용예시

```java
@Override
public void onCreate(Bundle savedInstanceState) {

    // ...

    final TextView pointView = (TextView)findViewById(R.id.main_point);

    TnkSession.queryPoint(this, true, new ServiceCallback() {

        @Override
        public void onReturn(Context context, Object result) {
            Integer point = (Integer)result;
            pointView.setText(String.valueOf(point));
        }
	});

	// ...
}
```

##### [동기방식으로 호출하기]

###### Method 

  - int TnkSession.queryPoint(Context context)

###### Description

Tnk 서버에 적립되어 있는 사용자 포인트 값을 조회하여 그 결과를 int 값으로 반환합니다. 

###### Parameters

| 파라메터 명칭 | 내용                            |
| ------------- | ------------------------------- |
| context       | 현재 Activity 또는 Context 객체 |

###### Return : int

  - 서버에 적립되어 있는 포인트 값

###### 적용예시

```java
static public void getPoint() {

    new Thread() {

        public void run() {
            int point = TnkSession.queryPoint(mActivity);
            showPoint(point); // 결과를 받아서 필요한 로직을 수행한다.
        }
    }.start();
}
```

#### TnkSession.purchaseItem()

TnK 서버에서는 별도로 아이템 목록을 관리하는 기능을 제공하지는 않습니다. 
다만 게시앱에서 제공하는 아이템을 사용자가 구매할 때 Tnk 서버에 해당 포인트 만큼을 차감 할 수 있습니다. 이 API 역시 비동기 방식과 동기 방식을 모두 제공합니다.

##### [비동기로 호출하기]

###### Method 

  - void TnkSession.purchaseItem(Context context, int pointCost, String itemId, boolean showProgress, ServiceCallback callback)

###### Description

Tnk 서버에 적립되어 있는 사용자 포인트를 차감합니다. 차감내역은 Tnk사이트의 보고서 페이지에서 조회하실 수 있습니다.

###### Parameters

| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| context       | 현재 Activity 또는 Context 객체                              |
| pointCost     | 차감할 포인트                                                |
| itemId        | 구매할 아이템의 고유 ID (게시앱에서 정하여 부여한 ID) Tnk 사이트의 보고서 페이지에서 함께 보여줍니다. |
| showProgress  | 서버에서 결과가 올때까지 화면에 progress dialog를 띄울지 여부를 지정 |
| callback      | 서버에서 결과가 오면 callback 객체의 OnReturn(Context context, Object result) 메소드가 호출됩니다. 메소드 호출은 Main UI Thread 상에서 이루어 집니다. 전달된 result 객체는 long[] 객체이며 long[0] 값은 차감 후 남은 포인트 값이며, long[1] 값은 고유한 거래 ID 값이 담겨 있습니다. long[1] 값이 음수 인경우에는 포인트 부족 등으로 오류가 발생한 경우입니다. |

###### 적용예시

```java
@Override
public void onClick(View v) {

    TnkSession.purchaseItem(MainActivity.this, 30, "item.00001", true,

        new ServiceCallback() {

            @Override
            public void onReturn(Context context, Object result) {

                long[] ret = (long[])result;

                if (ret[1] < 0) {
                     // error
                } else {
                     Log.d("tnkad", "current point = " + ret[0] + ", transaction id = " + ret[1]);
                     pointView.setText(String.valueOf(ret[0]));
                }
            }
    	});
}
```

##### [동기방식으로 호출하기]

###### Method 

  - long[] TnkSession.purchaseItem(Context context, int pointCost, String itemId)

###### Description

Tnk 서버에 적립되어 있는 사용자 포인트를 차감하고 그 결과를 long[] 로 반환합니다. 차감내역은 Tnk사이트의 보고서 페이지에서 조회하실 수 있습니다.

###### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| context       | 현재 Activity 또는 Context 객체                              |
| pointCost     | 차감할 포인트                                                |
| itemId        | 구매할 아이템의 고유 ID (게시앱에서 정하여 부여한 ID) Tnk 사이트의 보고서 페이지에서 함께 보여줍니다. |

###### Return : long[]

  - long[0] 은 포인트 차감후 남은 포인트 값입니다.
  - long[1] 은 고유한 거래 번호 값입니다. 이 값이 음수 인 경우에는 오류가 발생한 것입니다. (포인트 부족 등)

#### TnkSession.withdrawPoints()

Tnk 서버에서 관리되는 사용자 포인트 전체를 한번에 인출하는 기능입니다.

##### [비동기로 호출하기]

###### Method 

  - void TnkSession.withdrawPoints(Context context, String desc, boolean showProgress, ServiceCallback callback)

###### Description

Tnk 서버에 적립되어 있는 사용자의 모든 포인트를 차감합니다. 차감내역은 Tnk사이트의 보고서 페이지에서 조회하실 수 있습니다.

###### Parameters

| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| context       | 현재 Activity 또는 Context 객체                              |
| desc          | 인출과 관련된 설명 등을 넣어줍니다. Tnk 사이트의 보고서 페이지에서 함께 보여줍니다. |
| showProgress  | 서버에서 결과가 올때까지 화면에 progress dialog를 띄울지 여부를 지정 |
| callback      | 서버에서 결과가 오면 callback 객체의 OnReturn(Context context, Object result) 메소드가 호출됩니다. 메소드 호출은 Main UI Thread 상에서 이루어 집니다. 전달된 result 객체는 Integer 객체이며 인출된 포인트 값입니다. 해당 사용자에게 충전된 포인트가 없는 경우에는 0이 반환됩니다. |

###### 적용예시

```java
@Override
public void onClick(View v) {

    TnkSession.withdrawPoints(MainActivity.this, "user_delete", true,

        new ServiceCallback() {

            @Override
            public void onReturn(Context context, Object result) {

                int point = (Integer)result;
                Log.d("tnkad", "withdraw point = " + point);
                
                pointView.setText(String.valueOf(point));
            }
        });
}
```

##### [동기방식으로 호출하기]

###### Method 

  - int TnkSession.withdrawPoints(Context context, String desc)

###### Description

Tnk 서버에 적립되어 있는 사용자의 모든 포인트를 차감하고 차감된 포인트 값을 반환합니다. 차감내역은 Tnk사이트의 보고서 페이지에서 조회하실 수 있습니다.

###### Parameters

| 파라메터 명칭 | 내용                                                         |
| ------------- | ------------------------------------------------------------ |
| context       | 현재 Activity 또는 Context 객체                              |
| desc          | 인출과 관련된 설명 등을 넣어줍니다. Tnk 사이트의 보고서 페이지에서 함께 보여줍니다. |

###### Return : int

  - 인출된 포인트 값, 사용자에게 인출할 포인트가 없으면 0이 반환됩니다.

#### TnkSession.getEarnPoints()

Tnk서버에서 사용자가 참여 가능한 모든 광고의 적립 가능한 총 포인트 값을 조회합니다. 
동기 방식을 제공하고 있으며 별도 Thread를 생성하여 호출하셔야 합니다.

##### [동기방식으로 호출하기]

###### Method 

  - int TnkSession.getEarnPoints(Context context)

###### Description

Tnk서버에서 사용자가 참여 가능한 모든 광고의 적립 가능한 총 포인트 값을 조회하여 그 결과를 int 값으로 반환합니다. 

###### Parameters

| 파라메터 명칭 | 내용                            |
| ------------- | ------------------------------- |
| context       | 현재 Activity 또는 Context 객체 |

###### Return : int

  - 참여 가능한 광고의 적립 가능한 총 포인트 값

```java
static public void getEarnPoint() {

    new Thread() {

        public void run() {
            int points = TnkSession.getEarnPoints(mActivity);
            showPoint(points); // 결과를 받아서 필요한 로직을 수행한다.
        }
    }.start();
}
```

### 다. 그밖의 기능들

#### TnkSession.setAdWallListType()

오퍼월 리스트 광고 타입을 설정합니다. 광고 타입으로는 보상형과 구매형 두 가지가 있으며 기본 설정은 두가지 타입 모두 나오는 것이지만 해당 기능을 통해 한가지 타입만 나오도록 설정이 가능합니다. 

##### Method

* void TnkSession.setAdWallListType(int listType)

##### Parameters
| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| listType       | 오퍼월 리스트 광고 타입                         |

* 사용가능한 타입의 종류는 아래와 같습니다.

| 값                          | 타입                                     |
| --------------------------- | -------------------------------------------------------------- |
| TnkSession.AD_LIST_TYPE_DEFAULT | 보상형과 구매형 모두 표시               |
| TnkSession.AD_LIST_TYPE_PPI | 보상형만 표시                            |
| TnkSession.AD_LIST_TYPE_CPS| 구매형만 표시                            |


#### TnkSession.queryPublishState()

Tnk 사이트의 [게시정보]에서 광고 게시 중지를 하게 되면 이후에는 사용자가 광고 목록 창을 띄워도 광고들이 나타나지 않습니다.
그러므로 향후 광고 게시를 중지할 경우를 대비하여 화면에 충전소 버튼 자체를 보이지 않게 하는 기능을 갖추는 것이 바람직합니다.
이를 위하여 현재 게시앱의 광고게시 상태를 조회하는 기능을 제공합니다.

##### Method 

  - void TnkSession.queryPublishState(Context context, boolean showProgress, ServiceCallback callback)

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| context       | 현재 Activity 또는 Context 객체                              |
| showProgress  | 서버에서 결과가 올때까지 화면에 progress dialog를 띄울지 여부를 지정 |
| callback      | 서버에서 결과가 오면 callback 객체의 OnReturn(Context context, Object result) 메소드가 호출됩니다. 메소드 호출은 Main UI Thread 상에서 이루어 집니다. 전달된 result 객체는 Integer 객체이며 상태코드가 담겨 있습니다. 상태코드 값이 TnkSession.STATE_YES 인 경우(실제 값은 1)는 광고게시상태를 의미합니다. |

##### 적용예시

```java
final Button button = (Button)findViewById(R.id.main_ad);

// ... 

TnkSession.queryPublishState(this, false, new ServiceCallback() {

    @Override
    public void onReturn(Context context, Object result) {

        int state = (Integer)result;

        if (state == TnkSession.STATE_YES) {
            button.setVisibility(View.VISIBLE);
        }
    }
});
```

#### TnkSession.queryAdvertiseCount()

광고 게시 상태를 확인하여 충전소 버튼을 보이게하거나 안보이게 하는 것으로도 좋지만 실제적으로 현재 적립 가능한 광고가 있는지 여부를 판단해서 버튼을 노출하는 것이 보다 바람직합니다.
이를 위하여 현재 적립가능한 광고 정보를 확인하는 기능을 아래와 같이 제공합니다.

##### Method 

  - void TnkSession.queryAdvertiseCount(Context context, boolean showProgress, ServiceCallback callback)

##### Parameters

| 파라메터 명칭 | 내용                                                         |
| -------------- | ----------------------------------------------------------- |
| context       | 현재 Activity 또는 Context 객체                              |
| showProgress  | 서버에서 결과가 올때까지 화면에 progress dialog를 띄울지 여부를 지정 |
| callback      | 서버에서 결과가 오면 callback 객체의 OnReturn(Context context, Object result) 메소드가 호출됩니다. 메소드 호출은 Main UI Thread 상에서 이루어 집니다. 전달된 result 객체는 int[] 객체이며 int[0]는 광고 건수, int[1] 에는 적립가능한 포인트 합계가 담겨 있습니다. 만약 현재 광고 게시상태가 아니라면 int[0]에는 0이 담겨있습니다. |

#### TnkSession.enableLogging()

Tnk의 SDK에서 생성하는 로그를 출력할지 여부를 결정합니다. 테스트 시에는 true로 설정하시고 Release 빌드시에는 false로 설정해주시기 바랍니다.

##### Method 

  - void TnkSession.enableLogging(boolean trueOrFalse)

### 라. 디자인 변경하기

광고 리스트 화면(AdListView)는 기본 스타일을 그대로 사용하셔도 충분하지만, 원하시는 경우 매체앱과 통일감 있도록 UI를 변경하실 수 있습니다.

AdListView의 UI를 변경하기 위해서 TemplateLayoutUtils와 TnkLayout 기능을 제공합니다.  TemplateLayoutUtils은 다양한 디자인을 쉽게 사용할 수 있도록 몇가지 디자인을 가지고 있으며 원하시는 디자인을 선택하여 사용하시면 됩니다. 만약 TemplateLayoutUtils에서도 원하는 디자인을 찾을 수 없고 기본 화면 구성과 완전히 다르게 UI를 배치하고자 하신다면 TnkLayout 기능를 사용하시어 원하는 화면 구성으로 완전히 변경하실 수 있습니다.

[**템플릿 디자인 보기**](#템플릿-디자인)

#### TnkLayout

TnkLayout 기능을 사용하면 화면 구성 자체를 원하는 UI로 변경이 가능합니다.

SDK에 포함된 템플릿 또한 TnkLayout을 이용해 구성된 UI로 어떤 형태든 원하는 UI로 변경이 가능합니다.



TnkLayout을 적용하기 위한 단계는 다음과 같습니다.

1. 광고 리스트 화면 구성, 리스트 Item의 화면구성(아이콘형, 피드형), 상세 팝업 화면구성을 위한 Layout을 XML로 정의합니다. 3가지 Layout XML을 모두 작성해야하는 것은 아니며 변경하기를 윈하는 Layout만 작성하시면됩니다.
2. 작성한 Layout XML 내에서 정의한 구성 요소의 ID 들을 TnkLayout 객체에 지정합니다.
3. 화면을 띄울때 TnkLayout 객체를 같이 전달합니다.



##### TnkLayout 객체

TnkLayout 객체를 생성하시고 아래의 속성값을 지정합니다. 모든 속성을 지정할 필요는 없습니다.

| 광고 목록 화면 Layout        | 상세 설명                                                    |
| ---------------------------- | ------------------------------------------------------------ |
| adwall                       | 광고목록 화면의 Layout을 설정하기 위한 속성                  |
| adwall.numColumnsPortrait    | 화면이 세로 모드일때 item의 컬럼수 (기본값 : 1)              |
| adwall.numColumnsLandscape   | 화면이 가로 모드일때 item의 컬럼수 (기본값 : 2)              |
| adwall.layout                | 광고 목록 화면의 Layout ID                                   |
| adwall.idTitle               | 광고 목록 화면내의 상단 타이틀을 출력하기 위한 TextView의 ID |
| adwall.idList                | 광고 목록을 출력하기 위한 ListView의 ID                      |
| adwall.idClose               | 광고 목록 화면 닫기 용 Button 의 ID                          |
| adwall.idHelpdesk            | 포인트 지급 문의 용 Button 의 ID                             |
| adwall.idListStyle           | 리스트 스타일 변경 Button 의 ID                              |
| adwall.bgListStyleIcon       | Icon형 리스트 스타일 변경 Button 의 배경 이미지 Drawable ID  |
| adwall.bgListStyleFeed       | Feed형 리스트 스타일 변경 Button 의 배경 이미지 Drawable ID  |
| adwall.listDividerHeightIcon | Icon형 리스트 아이템 간 구분선 Height                        |
| adwall.listDividerHeightFeed | Feed형 리스트 아이템 간 구분선 Height                        |
| adwall.isHelpDeskPopupStyle  | 이용문의 팝업 스타일 사용 여부                               |

| 광고 목록의 Header Layout   | 상세 설명                                                    |
| --------------------------- | ------------------------------------------------------------ |
| adwall.header               | 광고 목록 상단 지금 획득 가능 포인트 Layout을 설정하기 위한 속성 |
| adwall.header.layout        | 광고 목록 상단 지금 획득 가능 포인트 Layout 의 ID            |
| adwall.header.idPointAmount | 광고 상단 지금 획득 가능 포인트 TextView 의 ID               |
| adwall.header.idPointUnit   | 광고 상단 지금 획득 가능 포인트 단위 TextView 의 ID          |

| 광고 항목 화면 Layout (아이콘형) | 상세 설명                                                    |
| -------------------------------- | ------------------------------------------------------------ |
| adwall.itemIcon                  | 광고 항목의 Layout을 설정하기 위한 속성                      |
| adwall.itemIcon.layout           | 광고 항목 화면의 Layout ID                                   |
| adwall.itemIcon.height           | 광고 항목 화면의 Height                                      |
| adwall.itemIcon.idIcon           | 광고 아이콘 이미지 용 ImageView 의 ID                        |
| adwall.itemIcon.idTitle          | 광고 명칭 TextView 의 ID                                     |
| adwall.itemIcon.idSubtitle       | 광고 설명 TextView 의 ID                                     |
| adwall.itemIcon.idTag            | 광고 태그 표시용 TextView 의 ID                              |
| adwall.itemIcon.idTagPoint       | 광고 적립금 표시용 TextView 의 ID                            |
| adwall.itemIcon.idTagUnit        | 광고 적립금 단위 표시용 TextView 의 ID                       |
| adwall.itemIcon.idCampnType      | 광고 종류 TextView의 ID                                      |
| adwall.itemIcon.idImage          | 광고 전면 이미지 용 ImageView 의 ID                          |
| adwall.itemIcon.bgItemEven       | 광고 항목 배경을 번갈이 다르게 지정하고 싶을 경우 사용.  짝수번째에 표시할 배경 이미지의 Drawable ID를 지정 |
| adwall.itemIcon.bgItemOdd        | 광고 항목 배경을 번갈이 다르게 지정하고 싶을 경우 사용.   홀수번째에 표시할 배경 이미지의 Drawable ID를 지정 |
| adwall.itemIcon.campn            | 광고 종료 TextView의 배경 이미지와 색상을 정의 (아래 별도 설명) |
| adwall.itemIcon.tag              | 광고 적립금 표시용 Tag의 배경 이미지와 색상을 정의 (아래 별도 설명) |

| 광고 항목 화면 Layout (피드형) | 상세 설명                                                    |
| -------------------------------- | ------------------------------------------------------------ |
| adwall.itemFeed              | 광고 항목의 Layout을 설정하기 위한 속성                      |
| adwall.itemFeed.layout       | 광고 항목 화면의 Layout ID                                   |
| adwall.itemFeed.height       | 광고 항목 화면의 Height                                      |
| adwall.itemFeed.idIcon       | 광고 아이콘 이미지 용 ImageView 의 ID                        |
| adwall.itemFeed.idTitle      | 광고 명칭 TextView 의 ID                                     |
| adwall.itemFeed.idSubtitle   | 광고 설명 TextView 의 ID                                     |
| adwall.itemFeed.idTag        | 광고 태그 표시용 TextView 의 ID                              |
| adwall.itemFeed.idTagPoint   | 광고 적립금 표시용 TextView 의 ID                            |
| adwall.itemFeed.idTagUnit    | 광고 적립금 단위 표시용 TextView 의 ID                       |
| adwall.itemFeed.idCampnType  | 광고 종류 TextView의 ID                                      |
| adwall.itemFeed.idImage      | 광고 전면 이미지 용 ImageView 의 ID                          |
| adwall.itemFeed.bgItemEven   | 광고 항목 배경을 번갈이 다르게 지정하고 싶을 경우 사용.  짝수번째에 표시할 배경 이미지의 Drawable ID를 지정 |
| adwall.itemFeed.bgItemOdd    | 광고 항목 배경을 번갈이 다르게 지정하고 싶을 경우 사용.   홀수번째에 표시할 배경 이미지의 Drawable ID를 지정 |
| adwall.itemFeed.campn        | 광고 종료 TextView의 배경 이미지와 색상을 정의 (아래 별도 설명) |
| adwall.itemFeed.tag          | 광고 적립금 표시용 Tag의 배경 이미지와 색상을 정의 (아래 별도 설명) |

| 광고 상세 화면 Layout            | 상세 설명                                                    |
| -------------------------------- | ------------------------------------------------------------ |
| adwall.detail                    | 광고 상세 화면의 Layout을 설정하기 위한 속성                 |
| adwall.detail.layout             | 광고 상세 화면의 Layout ID                                   |
| adwall.detail.idHeaderTitle      | 광고 상세 화면의 헤더 타이틀 TextView ID                     |
| adwall.detail.idIcon             | 광고 아이콘 이미지 용 ImageView 의 ID                        |
| adwall.detail.idTitle            | 광고 명칭 TextView 의 ID                                     |
| adwall.detail.idSubtitle         | 광고 설명 TextView 의 ID                                     |
| adwall.detail.idTag              | 광고 태그 표시용 TextView 의 ID                              |
| adwall.detail.idAction           | 사용자 수행 내용 표시 TextView 의 ID                         |
| adwall.detail.idActionList       | 사용자 수행 내용 표시 리스트 ViewGrop 의 ID                  |
| adwall.detail.idConfirm          | 이동(광고 참여) Button 의 ID                                 |
| adwall.detail.idCancel           | 닫기 Button 의 ID                                            |
| adwall.detail.idJoinDesc         | 참여시 주의사항 TextView 의 ID                               |
| adwall.detail.idAppDesc          | 설명문 TextView 의 ID                                        |
| adwall.detail.idAppDescSeparator | 참여시 주의 사항과 설명문 사이의 구분선 View 의 ID           |
| adwall.detail.idImage            | 광고 이미지 ImageView 의 ID                                  |
| adwall.detail.idCampnType        | 광고 종류 TextView의 ID                                      |
| adwall.detail.confirmText        | 이동 버튼 Default Text                                       |
| adwall.detail.campn              | 광고 종료 TextView의 배경 이미지와 색상을 정의 (아래 별도 설명) |
| adwall.detail.tag                | 광고 적립금 표시용 Tag의 배경 이미지와 색상을 정의 (아래 별도 설명) |

| 사용자 수행 내용 Layout | 상세 설명                            |
| --------------------- | -------------------------------------------------------- |
| adwall.detail.actionItem | 사용자 수행 내용 Layout을 설정하기 위한 속성 |
| adwall.detail.actionItem.layout | 사용자 수행 내용 Layout ID |
| adwall.detail.actionItem.idTag | 태그 TextView 의 ID |
| adwall.detail.actionItem.idAction | 설명 TextView 의 ID |
| adwall.detail.actionItem.idTagPoint | 태그 포인트 TextView 의 ID |
| adwall.detail.actionItem.idTagUnit | 태그 포인트 단위 TextView 의 ID |

| Campaign 속성 | 상세 설명                            |
| ------------- | ------------------------------------ |
| bgCampnCPI    | CPI인 경우 배경 이미지의 Drawable ID |
| bgCampnCPS    | CPS인 경우 배경 이미지의 Drawable ID |
| tcCampnCPI    | CPI인 경우 Text Color 값 (ARGB)      |
| tcCampnCPS    | CPS의 Text Color 값 (ARGB)           |

| Tag 속성        | 상세 설명                                               |
| --------------- | ------------------------------------------------------- |
| bgTagNormal     | 참여 전 대상 태그 TextView 의 배경 이미지 Drawable ID   |
| bgTagCheck      | 참여 확인 대상 태그 TextView 의 배경 이미지 Drawable ID |
| tcTagNormal     | 참여 전 대상 태그 TextView 의 Text Color 값 (ARGB)      |
| tcTagCheck      | 참여 확인 대상 태그 TextView 의 Text Color 값 (ARGB)    |
| tagNormalFormat | 참여 전 대상 태그 TextView 의 텍스트 포맷               |
| tagCheckformat  | 참여 확인 대상 태그 TextView 의 텍스트 포맷             |
| pointFormat     | 포인트 TextView 의 포맷                                 |
| pointUnitFormat | 포인트 단위 TextView 의 포맷                            |


##### 적용 예시

###### 광고 목록 화면 Layout XML 작성

> offerwall_layout.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <RelativeLayout
        android:id="@+id/com_tnk_offerwall_layout_header"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@color/com_tnk_offerwall_list_header_blue">

        <Button
            android:id="@+id/com_tnk_offerwall_layout_help"
            android:layout_width="24dp"
            android:layout_height="24dp"
            android:layout_alignParentLeft="true"
            android:layout_centerVertical="true"
            android:layout_marginLeft="10dp"
            android:background="@drawable/com_tnk_icon_help"/>

        <TextView
            android:id="@+id/com_tnk_offerwall_layout_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_toRightOf="@+id/com_tnk_offerwall_layout_help"
            android:layout_toLeftOf="@+id/com_tnk_offerwall_layout_style"
            android:layout_centerVertical="true"
            android:layout_marginLeft="45dp"
            android:layout_marginRight="10dp"
            android:textColor="@color/color_white"
            android:textSize="20dp"
            android:maxLines="1"
            android:ellipsize="end"
            android:gravity="center"
            tools:text="Test Title Test TitleTest Title Test Title" />

        <Button
            android:id="@+id/com_tnk_offerwall_layout_style"
            android:layout_width="20dp"
            android:layout_height="20dp"
            android:layout_toLeftOf="@+id/com_tnk_offerwall_layout_close"
            android:layout_centerVertical="true"
            android:layout_marginRight="15dp"
            android:background="@drawable/com_tnk_icon_feed"/>

        <Button
            android:id="@+id/com_tnk_offerwall_layout_close"
            android:layout_width="20dp"
            android:layout_height="20dp"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:layout_marginRight="10dp"
            android:background="@drawable/com_tnk_icon_close"/>

    </RelativeLayout>

    <ListView
        android:id="@+id/com_tnk_offerwall_layout_adlist"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:divider="#EEEEEE"
        android:dividerHeight="5dp"
        android:background="@color/com_tnk_offerwall_list_bg_color"/>
</LinearLayout>
```

###### 광고 목록의 Header Layout XML 작성

> offerwall_layout_header.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/color_white">

    <TextView
        android:id="@+id/com_tnk_offerwall_layout_header_msg"
        android:layout_width="match_parent"
        android:layout_height="40dp"
        android:layout_alignParentLeft="true"
        android:layout_toLeftOf="@+id/com_tnk_offerwall_layout_header_point"
        android:layout_marginLeft="10dp"
        android:textSize="14dp"
        android:lines="1"
        android:maxLines="1"
        android:ellipsize="end"
        android:gravity="center_vertical"
        android:text="지금 획득 가능한 코인"/>

    <TextView
        android:id="@+id/com_tnk_offerwall_layout_header_point"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_toLeftOf="@+id/com_tnk_offerwall_layout_header_unit"
        android:textColor="@color/color_blue"
        android:textSize="16dp"
        android:textStyle="bold"
        android:lines="1"
        android:maxLines="1"
        android:gravity="center"
        tools:text="9999999"/>

    <TextView
        android:id="@+id/com_tnk_offerwall_layout_header_unit"
        android:layout_width="wrap_content"
        android:layout_height="40dp"
        android:layout_alignParentRight="true"
        android:layout_marginLeft="5dp"
        android:layout_marginRight="10dp"
        android:textColor="@color/color_blue"
        android:textSize="16dp"
        android:textStyle="bold"
        android:lines="1"
        android:maxLines="1"
        android:gravity="center"
        android:text="코인"/>
</RelativeLayout>
```



###### 광고 항목 화면 Layout XML 작성

리스트 스타일은 아이콘형과 피드형 총 2가지가 존재합니다. 둘 다 변경을 원하시는 경우 두 레이아웃 모두 작성하셔야 합니다.

> offerwall_item_icon.xml (아이콘형)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="10dp"
    android:background="@drawable/com_tnk_offerwall_item_bg">

    <ImageView
        android:id="@+id/com_tnk_offerwall_item_icon"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:layout_centerVertical="true"
        android:layout_alignParentLeft="true"
        android:layout_marginRight="8dp"
        android:scaleType="fitXY"
        tools:background="@color/color_orange"/>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="70dp"
        android:layout_toRightOf="@+id/com_tnk_offerwall_item_icon"
        android:layout_toLeftOf="@+id/com_tnk_offerwall_item_tag_container">

        <TextView
            android:id="@+id/com_tnk_offerwall_item_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentTop="true"
            android:layout_above="@+id/com_tnk_offerwall_item_campaign"
            android:textColor="@color/com_tnk_offerwall_item_title_text_color"
            android:textSize="15dp"
            android:textStyle="bold"
            android:maxLines="2"
            android:ellipsize="end"
            android:gravity="center_vertical"
            tools:text="Test Title Test Title Test Title Test Title Test Title Test Title Test Title Test Title Test Title"
            tools:background="@color/color_blue"/>

        <TextView
            android:id="@+id/com_tnk_offerwall_item_campaign"
            android:layout_width="wrap_content"
            android:layout_height="20dp"
            android:layout_alignParentBottom="true"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:textSize="11dp"
            android:lines="1"
            android:maxLines="1"
            android:ellipsize="end"
            android:gravity="center_vertical"
            tools:text="액션형"
            tools:textColor="@color/color_blue"
            tools:background="@drawable/com_tnk_campaign_label_cpi"/>
    </RelativeLayout>

    <RelativeLayout
        android:id="@+id/com_tnk_offerwall_item_tag_container"
        android:layout_width="55dp"
        android:layout_height="55dp"
        android:layout_centerVertical="true"
        android:layout_alignParentRight="true"
        android:layout_marginLeft="8dp" >

        <TextView
            android:id="@+id/com_tnk_offerwall_item_tag"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:paddingTop="10dp"
            android:textSize="13dp"
            android:textColor="@color/color_blue"
            android:textStyle="bold"
            android:gravity="center_horizontal"
            tools:text="7777777"
            tools:background="@drawable/com_tnk_tag_label_square_blue" />

        <TextView
            android:id="@+id/com_tnk_offerwall_item_tag_unit"
            android:layout_width="match_parent"
            android:layout_height="17dp"
            android:layout_alignParentBottom="true"
            android:textSize="11dp"
            android:textColor="@color/color_white"
            android:gravity="center_horizontal"
            tools:text="코인받기"/>
    </RelativeLayout>
</RelativeLayout>
```

> offerwall_item_feed.xml (피드형)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/color_white">

    <ImageView
        android:id="@+id/com_tnk_offerwall_item_image"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:adjustViewBounds="true"
        tools:layout_height="250dp"
        tools:background="#FF9800"/>

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:layout_below="@+id/com_tnk_offerwall_item_image"
        android:layout_margin="10dp">

        <TextView
            android:id="@+id/com_tnk_offerwall_item_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_toLeftOf="@+id/com_tnk_offerwall_item_tag_container"
            android:textSize="15dp"
            android:textColor="@color/com_tnk_offerwall_item_title_text_color"
            android:textStyle="bold"
            android:lines="1"
            android:maxLines="1"
            android:ellipsize="end"
            tools:text="Test Title Test Title Test Title Test Title Test Title"
            tools:background="@color/color_blue"/>

        <TextView
            android:id="@+id/com_tnk_offerwall_item_sub_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/com_tnk_offerwall_item_title"
            android:layout_above="@+id/com_tnk_offerwall_item_campaign"
            android:layout_toLeftOf="@+id/com_tnk_offerwall_item_tag_container"
            android:textSize="13dp"
            android:textColor="@color/com_tnk_offerwall_item_subtitle_text_color"
            android:lines="2"
            android:maxLines="2"
            android:ellipsize="end"
            tools:text="Test Sub Title Test Sub Title Test Sub Title Test Sub Title Test Sub Title Test Sub Title Test Sub Title Test Sub Title"
            tools:background="@color/color_yellow"/>

        <TextView
            android:id="@+id/com_tnk_offerwall_item_campaign"
            android:layout_width="wrap_content"
            android:layout_height="20dp"
            android:layout_alignParentBottom="true"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:textSize="11dp"
            android:lines="1"
            android:maxLines="1"
            android:ellipsize="end"
            android:gravity="center_vertical"
            tools:text="액션형"
            tools:textColor="@color/color_blue"
            tools:background="@drawable/com_tnk_campaign_label_cpi"/>

        <RelativeLayout
            android:id="@+id/com_tnk_offerwall_item_tag_container"
            android:layout_width="55dp"
            android:layout_height="55dp"
            android:layout_centerVertical="true"
            android:layout_alignParentRight="true"
            android:layout_marginLeft="8dp" >

            <TextView
                android:id="@+id/com_tnk_offerwall_item_tag"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:paddingTop="10dp"
                android:textSize="13dp"
                android:textColor="@color/color_blue"
                android:textStyle="bold"
                android:gravity="center_horizontal"
                tools:text="7777777"
                tools:background="@drawable/com_tnk_tag_label_square_blue" />

            <TextView
                android:id="@+id/com_tnk_offerwall_item_tag_unit"
                android:layout_width="match_parent"
                android:layout_height="17dp"
                android:layout_alignParentBottom="true"
                android:textSize="11dp"
                android:textColor="@color/color_white"
                android:gravity="center_horizontal"
                tools:text="코인받기"/>
        </RelativeLayout>
    </RelativeLayout>
</RelativeLayout>
```

###### 광고 상세 화면 Layout XML 작성

> offerwall_detail.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/color_white">

    <RelativeLayout
        android:id="@+id/com_tnk_offerwall_detail_header"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@color/com_tnk_offerwall_list_header_blue">

        <TextView
            android:id="@+id/com_tnk_offerwall_detail_header_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_toLeftOf="@+id/com_tnk_offerwall_detail_close"
            android:layout_centerVertical="true"
            android:layout_marginLeft="34dp"
            android:textColor="@color/color_white"
            android:textSize="20dp"
            android:gravity="center"
            android:text="무료 코인 받기" />

        <Button
            android:id="@+id/com_tnk_offerwall_detail_close"
            android:layout_width="20dp"
            android:layout_height="20dp"
            android:layout_alignParentRight="true"
            android:layout_centerVertical="true"
            android:layout_marginRight="10dp"
            android:background="@drawable/com_tnk_icon_close"/>
    </RelativeLayout>

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/com_tnk_offerwall_detail_header">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/com_tnk_offerwall_detail_header">

            <ImageView
                android:id="@+id/com_tnk_offerwall_detail_image"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_alignParentTop="true"
                android:adjustViewBounds="true"
                tools:layout_height="350dp"
                tools:background="#FF9800"/>

            <TextView
                android:id="@+id/com_tnk_offerwall_detail_title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/com_tnk_offerwall_detail_image"
                android:layout_marginTop="15dp"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                android:textColor="@color/com_tnk_offerwall_detail_title_text_color"
                android:textSize="17sp"
                android:textStyle="bold"
                android:maxLines="2"
                android:ellipsize="end"
                tools:text="Test Title Tes Test Title Test Title Test Title Test Title Tes Test Title Test Title Test Title Test Title Test Title"
                tools:background="@color/color_blue"/>

            <TextView
                android:id="@+id/com_tnk_offerwall_detail_sub_title"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/com_tnk_offerwall_detail_title"
                android:layout_marginTop="5dp"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                android:textColor="@color/com_tnk_offerwall_detail_subtitle_text_color"
                android:textSize="13sp"
                android:maxLines="2"
                android:ellipsize="end"
                tools:text="Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description"
                tools:background="@color/color_yellow"/>

            <View
                android:id="@+id/com_tnk_offerwall_detail_separator_1"
                android:layout_width="match_parent"
                android:layout_height="0.5dp"
                android:layout_below="@+id/com_tnk_offerwall_detail_sub_title"
                android:layout_marginTop="15dp"
                android:layout_marginBottom="10dp"
                android:background="#aaa" />

            <LinearLayout
                android:id="@+id/com_tnk_offerwall_detail_action_items"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/com_tnk_offerwall_detail_separator_1"
                android:layout_marginBottom="10dp"
                android:orientation="vertical"
                tools:layout_height="50dp"/>

            <TextView
                android:id="@+id/com_tnk_offerwall_detail_confirm"
                android:layout_width="match_parent"
                android:layout_height="60dp"
                android:layout_below="@+id/com_tnk_offerwall_detail_action_items"
                android:textSize="25dp"
                android:textStyle="bold"
                android:textColor="@color/color_white"
                android:gravity="center"
                android:background="@color/color_blue"
                tools:text="코인받기" />

            <TextView
                android:id="@+id/com_tnk_offerwall_detail_join_desc"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/com_tnk_offerwall_detail_confirm"
                android:paddingTop="10dp"
                android:paddingLeft="15dp"
                android:paddingRight="15dp"
                android:textSize="13sp"
                android:textColor="@color/com_tnk_offerwall_detail_desc_text_color"
                android:lineSpacingExtra="2dp"
                tools:text="참여시 주의사항"/>

            <View
                android:id="@+id/com_tnk_offerwall_detail_separator_2"
                android:layout_width="match_parent"
                android:layout_height="0.5dp"
                android:layout_below="@+id/com_tnk_offerwall_detail_join_desc"
                android:layout_marginLeft="10dp"
                android:layout_marginRight="10dp"
                android:layout_marginBottom="35dp"
                android:background="#aaa" />

            <TextView
                android:id="@+id/com_tnk_offerwall_detail_app_desc"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_below="@+id/com_tnk_offerwall_detail_separator_2"
                android:paddingLeft="15dp"
                android:paddingRight="15dp"
                android:textSize="13sp"
                android:textColor="@color/com_tnk_offerwall_detail_desc_text_color"
                android:lineSpacingExtra="2dp"
                tools:text="앱 설명문"/>

            <View
                android:id="@+id/com_tnk_offerwall_detail_bottom_margin"
                android:layout_width="match_parent"
                android:layout_height="40dp"
                android:layout_below="@id/com_tnk_offerwall_detail_app_desc"/>
        </RelativeLayout>
    </ScrollView>
</RelativeLayout>
```



###### TnkLayout 객체 생성 및 AdListView 띄우기

```java
public class OfferwallTemplateActivity extends AppCompatActivity {
	...
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	...
        
        TnkSession.showAdList(OfferwallTemplateActivity.this, "Title", makeCustomLayout());
    
    	...
    }

    private TnkLayout makeCustomLayout() {
        TnkLayout res = new TnkLayout();

        res.adwall.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_layout_blue;
        res.adwall.idTitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_title;
        res.adwall.idList = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_adlist;
        res.adwall.idClose = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_close;
        res.adwall.idHelpdesk = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_help;
        res.adwall.idListStyle = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_style;
        res.adwall.bgListStyleIcon = com.tnkfactory.ad.R.drawable.com_tnk_icon_list;
        res.adwall.bgListStyleFeed = com.tnkfactory.ad.R.drawable.com_tnk_icon_feed;
        res.adwall.listDividerHeightIcon = 3;
        res.adwall.listDividerHeightFeed = 20;

        res.adwall.header.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_layout_header_blue;
        res.adwall.header.idPointAmount = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_header_point;
        res.adwall.header.idPointUnit = com.tnkfactory.ad.R.id.com_tnk_offerwall_layout_header_unit;

        res.adwall.itemIcon.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_item_icon_square;
        res.adwall.itemIcon.idIcon = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_icon;
        res.adwall.itemIcon.idTitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_title;
        res.adwall.itemIcon.idTag = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_tag;
        res.adwall.itemIcon.idTagUnit = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_tag_unit;
        res.adwall.itemIcon.idCampnType = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_campaign;
        res.adwall.itemIcon.campn.bgCampnCPI = com.tnkfactory.ad.R.drawable.com_tnk_campaign_label_cpi;
        res.adwall.itemIcon.campn.bgCampnCPS = com.tnkfactory.ad.R.drawable.com_tnk_campaign_label_cps;
        res.adwall.itemIcon.campn.tcCampnCPI = Color.parseColor("#28A5FF");
        res.adwall.itemIcon.campn.tcCampnCPS = Color.parseColor("#CC003F");
        res.adwall.itemIcon.tag.bgTagNoraml = com.tnkfactory.ad.R.drawable.com_tnk_tag_label_square_blue;
        res.adwall.itemIcon.tag.bgTagCheck = com.tnkfactory.ad.R.drawable.com_tnk_tag_label_square_grey;
        res.adwall.itemIcon.tag.tcTagNormal = Color.parseColor("#28A5FF");
        res.adwall.itemIcon.tag.tcTagCheck = Color.parseColor("#BDBDBD");
        res.adwall.itemIcon.tag.tagNormalFormat = "{point}";
        res.adwall.itemIcon.tag.tagCheckFormat = "{point}";
        res.adwall.itemIcon.tag.pointUnitFormat = "{unit}받기";

        res.adwall.itemFeed.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_item_feed_square;
        res.adwall.itemFeed.idImage = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_image;
        res.adwall.itemFeed.idTitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_title;
        res.adwall.itemFeed.idSubtitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_sub_title;
        res.adwall.itemFeed.idTag = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_tag;
        res.adwall.itemFeed.idTagUnit = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_tag_unit;
        res.adwall.itemFeed.idCampnType = com.tnkfactory.ad.R.id.com_tnk_offerwall_item_campaign;
        res.adwall.itemFeed.campn.bgCampnCPI = com.tnkfactory.ad.R.drawable.com_tnk_campaign_label_cpi;
        res.adwall.itemFeed.campn.bgCampnCPS = com.tnkfactory.ad.R.drawable.com_tnk_campaign_label_cps;
        res.adwall.itemFeed.campn.tcCampnCPI = Color.parseColor("#28A5FF");
        res.adwall.itemFeed.campn.tcCampnCPS = Color.parseColor("#CC003F");
        res.adwall.itemFeed.tag.bgTagNoraml = com.tnkfactory.ad.R.drawable.com_tnk_tag_label_square_blue;
        res.adwall.itemFeed.tag.bgTagCheck = com.tnkfactory.ad.R.drawable.com_tnk_tag_label_square_grey;
        res.adwall.itemFeed.tag.tcTagNormal = Color.parseColor("#28A5FF");
        res.adwall.itemFeed.tag.tcTagCheck = Color.parseColor("#BDBDBD");
        res.adwall.itemFeed.tag.tagNormalFormat = "{point}";
        res.adwall.itemFeed.tag.tagCheckFormat = "{point}";
        res.adwall.itemFeed.tag.pointUnitFormat = "{unit}받기";

        res.adwall.detail.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_detail_blue;
        res.adwall.detail.idHeaderTitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_header_title;
        res.adwall.detail.idCancel = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_close;
        res.adwall.detail.idImage = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_image;
        res.adwall.detail.idTitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_title;
        res.adwall.detail.idSubtitle = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_sub_title;
        res.adwall.detail.idConfirm = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_confirm;
        res.adwall.detail.idJoinDesc = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_join_desc;
        res.adwall.detail.idAppDescSeparator = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_separator_2;
        res.adwall.detail.idAppDesc = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_app_desc;
        res.adwall.detail.idActionList = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_action_items;
        res.adwall.detail.actionItem.layout = com.tnkfactory.ad.R.layout.com_tnk_offerwall_detail_action_item_blue;
        res.adwall.detail.actionItem.idAction = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_aciton_item_desc;
        res.adwall.detail.actionItem.idTagPoint = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_aciton_item_point;
        res.adwall.detail.actionItem.idTagUnit = com.tnkfactory.ad.R.id.com_tnk_offerwall_detail_aciton_item_unit;
        res.adwall.detail.confirmText = "코인받기";

        return res;
    }
  
  ...
}
```

#### 템플릿 디자인 제공

SDK는 템플릿 디자인 16가지를 내장하고 있습니다. 내장되어 있는 디자인은 TemplateLayoutUtils을 통해 사용이 가능합니다.

```java
// Blue Style 
TemplateLayoutUtils.getBlueStyle_01(); // IconItem : Basic Square / FeedItem : Square
TemplateLayoutUtils.getBlueStyle_02(); // IconItem : Basic Square / FeedItem : Button
TemplateLayoutUtils.getBlueStyle_03(); // IconItem : Basic Ellipse / FeedItem : Square
TemplateLayoutUtils.getBlueStyle_04(); // IconItem : Basic Ellipse / FeedItem : Button
TemplateLayoutUtils.getBlueStyle_05(); // IconItem : Tall Square / FeedItem : Square
TemplateLayoutUtils.getBlueStyle_06(); // IconItem : Tall Square / FeedItem : Button
TemplateLayoutUtils.getBlueStyle_07(); // IconItem : Tall Ellipse / FeedItem : Square
TemplateLayoutUtils.getBlueStyle_08(); // IconItem : Tall Ellipse / FeedItem : Button

// Red Style
TemplateLayoutUtils.getRedStyle_01(); // IconItem : Basic Square / FeedItem : Square
TemplateLayoutUtils.getRedStyle_02(); // IconItem : Basic Square / FeedItem : Button
TemplateLayoutUtils.getRedStyle_03(); // IconItem : Basic Ellipse / FeedItem : Square
TemplateLayoutUtils.getRedStyle_04(); // IconItem : Basic Ellipse / FeedItem : Button
TemplateLayoutUtils.getRedStyle_05(); // IconItem : Tall Square / FeedItem : Square
TemplateLayoutUtils.getRedStyle_06(); // IconItem : Tall Square / FeedItem : Button
TemplateLayoutUtils.getRedStyle_07(); // IconItem : Tall Ellipse / FeedItem : Square
TemplateLayoutUtils.getRedStyle_08(); // IconItem : Tall Ellipse / FeedItem : Button

```

##### 사용방법 예시

```java
// 광고 목록 (Activity)
TnkSession.showAdList(this, "Title", TemplateLayoutUtils.getBlueStyle_01());

// 광고 목록 (View)
TnkSession.popupAdList(this, "Title", null, TemplateLayoutUtils.getBlueStyle_01());

// AdListView
TnkSession.createAdListView(this, TemplateLayoutUtils.getBlueStyle_01());
```

##### 템플릿 디자인

###### BlueStyle_01

![BlueStyle_01](./img/BlueStyle_01.png)

###### BlueStyle_02

![BlueStyle_02](./img/BlueStyle_02.png)

###### BlueStyle_03

![BlueStyle_03](./img/BlueStyle_03.png)

###### BlueStyle_04

![BlueStyle_04](./img/BlueStyle_04.png)

###### BlueStyle_05

![BlueStyle_05](./img/BlueStyle_05.png)

###### BlueStyle_06

![BlueStyle_06](./img/BlueStyle_06.png)

###### BlueStyle_07

![BlueStyle_07](./img/BlueStyle_07.png)

###### BlueStyle_08

![BlueStyle_08](./img/BlueStyle_08.png)



###### RedStyle_01

![RedStyle_01](./img/RedStyle_01.png)

###### RedStyle_02

![RedStyle_02](./img/RedStyle_02.png)

###### RedStyle_03

![RedStyle_03](./img/RedStyle_03.png)

###### RedStyle_04

![RedStyle_04](./img/RedStyle_04.png)
###### RedStyle_05

![RedStyle_05](./img/RedStyle_05.png)
###### RedStyle_06

![RedStyle_06](./img/RedStyle_06.png)
###### RedStyle_07

![RedStyle_07](./img/RedStyle_07.png)
###### RedStyle_08

![RedStyle_08](./img/RedStyle_08.png)