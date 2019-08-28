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
} from 'react-native';

import RNUhf from 'react-native-uhf-sdk';

export default class App extends Component {

  componentDidMount() {
    RNUhf.init({
      info: function (data) {
        console.log("info", data);
      },
      scan: function (data) {
        console.log("scan", data);
      },
      fail: function (err) {
        console.log("fail", err);
      }
    });
  }

  componentWillUnmount() {

  }

  onPressFirmware() {
    RNUhf.getFirmware();
  }
  onStartScan() {
    //RNUhf.getEpc();
    //RNUhf.getPower();
    RNUhf.setPower(27);
  }
  onPower() {
    RNUhf.getPower();
  }

  render() {
    return (
        <ScrollView
            contentInsetAdjustmentBehavior="automatic"
            style={styles.scrollView}>
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Button
                  onPress={this.onPressFirmware}
                  title="getFirmware"
                  color="#841584"
              />

              <Button
                  onPress={this.onStartScan}
                  title="start scan"
                  color="#841584"
              />
              <Button
                  onPress={this.onPower}
                  title="get power"
                  color="#841584"
              />

              <Text style={styles.sectionDescription}>
                Receive Data
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
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: '#444',
  },
});

