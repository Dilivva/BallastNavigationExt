# Ballast Navigation Extension

An opinionated fast [Ballast Navigation](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/) set-up for Compose 
multiplatform **(Android & iOS for now)**

![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.0-orange)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/Dilivva/BallastNavigationExt)](https://github.com/Dilivva/BallastNavigationExt/releases)
[![Maven Central](https://img.shields.io/maven-central/v/com.dilivva.ballastnavigationext/ballastnavigationext)](https://search.maven.org/artifact/com.dilivva.ballastnavigationext/ballastnavigationext)

### Set up

### Add dependency

Android:
````kotlin
implementation("com.dilivva.ballastnavigationext:ballastnavigationext:LATEST")
````
Multiplatform: **Android** & **iOS**
```kotlin
val commonMain by getting {
    dependencies { 
        //put your multiplatform dependencies here
        api("com.dilivva.ballastnavigationext:ballastnavigationext:LATEST")
    }
}
```

### Define your Screens/Routes
Extend the **`BaseRoute`** interface:
```kotlin
enum class MainRoute(
    override val route: String
): BaseRoute{
    Home("/home"),
    SignIn("/sign-in"),
    SignUp("/sign-up"),
    ForgotPassword("/forgot-password"),
    Dashboard("/dashboard?email={?}"),
    Profile("/profile");
}
```
More on Ballast [Route Matching](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/#route-matching)

### Create your Navigator Object

```kotlin
@Composable
fun MainNavigation() {
    val applicationScope = rememberCoroutineScope()
    val navigator = rememberNavigator(applicationScope, MainRoute.values())
}
```
The `rememberNavigator` assumes your first defined route in your
enum class in the initial route. You can override that by passing in
a callback that will return your desired initial route:
```kotlin
@Composable
fun MainNavigation() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val applicationScope = rememberCoroutineScope()
    val navigator = rememberNavigator(applicationScope, MainRoute.values()) {
        if (isLoggedIn) MainRoute.Dashboard else MainRoute.Home
    }
}
```

### Handle route changes
```kotlin
Destination(
    navigator = navigator,
    onNavigate = {
        when (it) {
        //Handle changes here...
        }
    }
)
```

### Navigate!
Navigate to a screen:
```kotlin
navigator.navigate(MainRoute.SignIn)
```
Navigate back
```kotlin
navigator.navigateUp()
```
Navigate to a screen popping up backstack to a particular screen:
```kotlin
navigator.navigateWithPop(destination = MainRoute.Profile, popUpTo = MainRoute.Home)
```
Navigate to a screen after popping the backstack:
```kotlin
navigator.navigateWithPopAll(destination = MainRoute.Profile)
```
### Passing data
Ballast Navigation supports passing data between destinations
using query or path parameters. Read [more](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/#route-matching)

To pass data, just override the `extras` callback of any of the
`navigate**` function:
```kotlin
navigator.navigate(MainRoute.Profile){
    pathParameter("user","john").queryParameter("email","john@gmail.com")
}
```



### EXTRAS

**Device Back press (Android):**

The library automatically handles device back pressed, if you want
to override that, pass in a value for `onDeviceBackPressed`:
```kotlin
Destination(
    //..//
    onDeviceBackPressed = {
        if (it == MainRoute.Dashboard){
        //close app
        }else{
            navigator.navigateUp() 
        }
    }
)
```

#### **Transitions:**
There's a default enter and exit animations (Slide in/out), but you can
override that and pass in your own enter and exit animations to the
`animateIn` and `animateOut` callbacks:
```kotlin
Destination(
    //..//
    animateIn = { },
    animateOut = { }
)
```

If a route cannot be matched `noDestination` callback will be invoked
the library automatically does nothing, you can override that.

## DeepLinking

Deep links are supported automatically with few extra setups

### Android:
Define your scheme in the android Manifest:
```xml
<intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <!-- Accepts URIs that begin with "ballast://dashboard” -->
    <data android:scheme="ballast"
          android:host="app" />
</intent-filter>
```
That's all!

Test your deep link:
```text
adb shell am start -W -a android.intent.action.VIEW -d "ballast://app/dashboard?email=dilivva@email.com"
```
The above command deep links to the dashboard screen passing in an email
address. So when deep linking to a screen in your app the pattern
is as follows
`scheme://host/screen`.

### iOS
Define your URL scheme:

![](../BallastNavigationExt/images/ios_url_scheme.png)

Lastly let the library handle the linking:
```swift
ContentView().onOpenURL { link in
    //Set up to support URL scheme
    DeepLinkResult.companion.setUrl(url: link.absoluteString)
}
```
That's all!!

## Example screens
Android:

iOS:


## Acknowledgements
[Ballast](https://github.com/copper-leaf/ballast): for the awesome routing/navigation library

# License

BallastNavigationExt is licensed under the MIT License, see [LICENSE.md](https://github.com/Dilivva/BallastNavigationExt/tree/main/LICENSE.md).
