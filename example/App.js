/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {
  StyleSheet,
  ScrollView,
  View,
  Text,
  Button,
  Slider,
  ToastAndroid,
} from 'react-native';

import RNUhf from 'react-native-uhf-sdk';

let isMultiScan=false;
let scanKeyCode=null;
const scanKey=[103,104];
export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      info: null,
    };
  }

  componentDidMount() {
    RNUhf.init({
      info: (data) => {
        ToastAndroid.show(data, ToastAndroid.SHORT);
        console.log("info", data);
        this.setState({
          info: data
        });
      },
      scan: (data) => {
        console.log("scan", data);
        this.setState({
          info: data
        });
      },
      fail: (err) => {
        console.log("fail", err);
        this.setState({
          info: err
        });
      },
      onKeyDown: (keyCode,keyEvent) => {
        console.log(keyCode,scanKey,scanKeyCode);
        if(scanKey.indexOf(keyCode)>-1){
          if(scanKey.indexOf(scanKeyCode)===-1){
            scanKeyCode=keyCode;
            //start scan
            console.log("start scan");
            RNUhf.scan();
          }
        }
      },
      onKeyUp:(keyCode,keyEvent) => {
        console.log(keyCode,scanKey,scanKeyCode);
        if(scanKey.indexOf(keyCode)>-1){
          if(scanKey.indexOf(scanKeyCode)>-1){
            scanKeyCode=null;
            //stop scan
            console.log("stop scan");
            RNUhf.stop();
          }
        }
      }
    });
  }

  componentWillUnmount() {

  }

  onPressFirmware() {
    RNUhf.getFirmware();
  }
  onPressVersion() {
    RNUhf.getVersion();
  }
  onPressMakerInfo() {
    RNUhf.getMakerInfo();
  }
  onStartScan() {
    RNUhf.getEpc();
  }
  onMultiScan() {
    if (isMultiScan) {
      isMultiScan = false;
      RNUhf.stop();
    } else {
      isMultiScan = true;
      RNUhf.scan();
    }
  }
  onPower() {
    RNUhf.getPower();
  }
  onPowerChange =(value)=>{
    RNUhf.setPower(value);
  };

  render() {
    const {info} = this.state;
    const scanTitle = isMultiScan ? 'stop' : 'multi scan';

    return (
        <ScrollView
            contentInsetAdjustmentBehavior="automatic"
            style={styles.scrollView}>
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <View style={{flexDirection: 'row'}}>
                <View style={{width: '50%', marginRight: 4}}>
                  <Button
                      onPress={this.onPressFirmware}
                      title="firmware"
                      color="#841584"
                  />
                </View>
                <View style={{width: '50%', marginLeft: 4}}>
                  <Button
                      onPress={this.onPressVersion}
                      title="version"
                      color="#841584"
                  />
                </View>
              </View>
              <View style={{flexDirection: 'row', marginTop: 8}}>
                <View style={{width: '50%', marginRight: 4}}>
                  <Button
                      onPress={this.onPressMakerInfo}
                      title="maker info"
                      color="#841584"
                  /></View>
                <View style={{width: '50%', marginLeft: 4}}>
                  <Button
                      onPress={this.onPower}
                      title="get power"
                      color="#841584"
                  />
                </View>
              </View>
              <View style={{marginTop: 8}}>
                <Slider style={{width: 280}}
                        minimumValue={15}
                        maximumValue={26}
                        value={26}
                        onValueChange={this.onPowerChange}
                />
              </View>
            </View>
            <View style={styles.sectionContainer}>
              <View style={{flexDirection: 'row'}}>
                <View style={{width: '50%', marginRight: 4}}>
                  <Button
                      onPress={this.onMultiScan}
                      title={scanTitle}
                      color="#841584"
                  />
                </View>
                <View style={{width: '50%', marginLeft: 4}}>
                  <Button
                      onPress={this.onStartScan}
                      title="start scan"
                      color="#841584"
                  />
                </View>
              </View>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionHeader}>
                Receive Data
              </Text>
              <Text style={styles.sectionDescription}>
                {info}
              </Text>
            </View>
          </View>
        </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: '#F3F3F3',
  },
  body: {
    backgroundColor: '#FFF',
  },
  sectionContainer: {
    marginTop: 8,
    paddingHorizontal: 24,
  },
  sectionHeader: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: '#444',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 12,
    color: '#444',
  },
});

