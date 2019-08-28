
# react-native-uhf-sdk

## Getting started

`$ npm install react-native-uhf-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-uhf-sdk`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.uhf.RNUhfPackage;` to the imports at the top of the file
  - Add `new RNUhfPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-uhf-sdk'
  	project(':react-native-uhf-sdk').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-uhf-sdk/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-uhf-sdk')
  	```
4. Open up `android/app/src/main/java/[...]/MainActivity.java`
    ```
   public class MainActivity extends ReactActivity {
         ......
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event){
            RNUhfModule.getInstance().onKeyDownEvent(keyCode,event);
            return super.onKeyDown(keyCode,event);
        }
    
        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event){
            RNUhfModule.getInstance().onKeyUpEvent(keyCode,event);
            return super.onKeyUp(keyCode,event);
        }
        ......
   }
    ```

## Usage
```javascript
import RNUhf from 'react-native-uhf-sdk';

// TODO: What to do with the module?
componentDidMount() {
    RNUhf.init({
      info: (data) => {
        console.log("info", data);
      },
      scan: (data) => {
        console.log("scan", data);
      },
      fail: (err) => {
        console.log("fail", err);
      },
      onKeyDown: (keyCode,keyEvent) => {
        console.log(keyCode,scanKey,scanKeyCode);
      },
      onKeyUp:(keyCode,keyEvent) => {
        console.log(keyCode,scanKey,scanKeyCode);
      }
    });
}

RNUhf.getFirmware()
RNUhf.getVersion()
RNUhf.getMakerInfo()
RNUhf.getEpc()
RNUhf.scan()
RNUhf.stop()
RNUhf.getPower()
RNUhf.setPower(power)
```
  