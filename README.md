[ ![Download](https://api.bintray.com/packages/pes8/maven/Material-Color-Picker-Dialog/images/download.svg) ](https://bintray.com/pes8/maven/Material-Color-Picker-Dialog/_latestVersion)

# Android Material Color Picker Dialog
A simple, minimalistic and beautiful dialog color picker for Android 4.1+ devices. This color picker is easy-to-use and easy-to-integrate in your application to let users of your app choose color in a simple way.

Features
- Get Hex & RGB color codes
- Set color using RGB values and get HEX codes
- Set colo using HEX value
- Separate UI for portrait and landscape devices
- Support for pre-lollipop devices

Design inspired from [Dribbble](https://dribbble.com/shots/1858968-Material-Design-colorpicker?list=following&offset=4) by [Lucas Bonomi](http://lucasbonomi.com/)

Portrait

![portrait](/screenshots/main.jpg)

Landscape

![landscape](/screenshots/main_land.jpg)


## HOW TO USE IT

### Adding the library to your project
The aar artifact is available at the **jcenter** repository. Declare the repository and the
dependency in your `build.gradle`.
    
(root)
```groovy
    repositories {
        jcenter()
    }
```
    
(module)
```groovy    
    dependencies {
        compile 'com.pes.materialcolorpicker:library:1.0.+'
    }
```

### Use the library

Import the class
```java
    import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
```

Create a color picker dialog object

```java
    final ColorPicker cp = new ColorPicker(MainActivity.this, defaultColorR, defaultColorG, defaultColorB);
```

defaultColorR, defaultColorG, defaultColorB are 3 integer ( value 0-255) for the initialization of the color picker with your custom color value. If you don't want to start with a color set them to 0 or use only the first argument

Then show the dialog (when & where you want) and save the selected color

```java

    /* Show color picker dialog */
    cp.show();
    
    /* On Click listener for the dialog, when the user select the color */
    Button okColor = (Button)cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                /* You can get single channel (value 0-255) */
                selectedColorR = cp.getRed();
                selectedColorG = cp.getGreen();
                selectedColorB = cp.getBlue();
                
                /* Or the android RGB Color (see the android Color class reference) */
                selectedColorRGB = cp.getColor();

                cp.dismiss();
            }
        });
```

That's all :)
## Translations

If you would like to help localise this library please fork the project, create and verify your language files, then create a pull request.


##Example

Example app that use Android Material Color Picker Dialog to let users choose the color of the Qr Code:

[Qr Code Generator Direct Download](http://www.simonepessotto.it/App/QrCodeGeneratorRevolution.apk)
[Qr Code Generator Play Store](https://play.google.com/store/apps/details?id=com.pes.qrcodegeneratorv2)

##LICENSE
```
The MIT License (MIT)

Copyright (c) 2016 Simone Pessotto (http://www.simonepessotto.it)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
