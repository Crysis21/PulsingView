# PulsingView

PulsingView is a simple android library designed to give you a ripple effect, if you ever need one. Ideally it can be used as a background for circle UI elements.

## Installation
Add `mavenCentral` to your projects root `build.gradle` file

```
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        ....
        mavenCentral()
        ....
    }
}
```

In your modules `build.gradle` add the dependency

```
implementation 'ro.holdone:pulseview:1.0.1'
```

## Usage

Add the `PulsingAnimationView` in your XML files and personalize it. You're done.

```
    <ro.holdone.pulseview.PulsingAnimationView
        android:id="@+id/button_companion"
        android:layout_width="140dp"
        android:layout_height="140dp"
        app:autoplay="true"
        app:baseRadius="32dp"
        app:pulseColor="@color/orange"
        app:strokeWidth="2.0dp"
        app:waveDistance="10dp" />
```

You can change the following params:
- `autoplay` - default is false. Will auto play the animation once the layout is loaded.
- `baseRadius` - the innermost circle size. Waves will start expanding from this size
- `pulseColor` - color used to draw
- `strokeWidth` - thickness of the drawn circles
- `waveDistance` - distance between 2 circles

This params can be changed in XML or programatically.

![ezgif com-gif-maker](https://user-images.githubusercontent.com/777169/114056287-2ea6c900-989a-11eb-9b80-59b6b510d66b.gif)
![GIF-210416_202658](https://user-images.githubusercontent.com/777169/115061817-4dd4d480-9ef2-11eb-9f0e-7f9c4ea9e061.gif)

