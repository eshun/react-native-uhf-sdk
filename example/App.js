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
    RNUhf.
  }

  componentWillUnmount() {

  }

  onPressFirmware() {
    console.log("init");
    RNUhf.getFirmware();

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

              <Text style={styles.sectionDescription}>
                Edit
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

