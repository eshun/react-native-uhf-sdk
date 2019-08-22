
# react-native-uhf-sdk

## Getting started

`$ npm install react-native-uhf-sdk --save`

### Mostly automatic installation

`$ react-native link react-native-uhf-sdk`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.xmky.uhf.RNUhfRnPackage;` to the imports at the top of the file
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


## Usage
```javascript
import RNUhf from 'react-native-uhf-sdk';

// TODO: What to do with the module?
RNUhf;
```
  