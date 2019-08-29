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
  Modal,
  TouchableOpacity,
} from 'react-native';

import RNUhf from 'react-native-uhf-sdk';

let isMultiScan=false;
let scanKeyCode=null;
export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      info: null,
      modalVisible: false,
      scanKey:[],
      epc:[],
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
        const {epc} = this.state;
        console.log("scan", data);
        if (epc.indexOf(data) === -1) {
          epc.push(data);
        }
        this.setState({
          epc
        });
      },
      fail: (err) => {
        console.log("fail", err);
        this.setState({
          info: err
        });
      },
      onKeyDown: (keyCode, keyEvent) => {
        const {scanKey, modalVisible} = this.state;
        console.log(keyCode, scanKey, scanKeyCode, modalVisible, scanKey.indexOf(keyCode));
        if (!modalVisible && scanKey.indexOf(keyCode) > -1) {
          if (scanKey.indexOf(scanKeyCode) === -1) {
            scanKeyCode = keyCode;
            //start scan
            console.log("start scan");
            RNUhf.scan();
          }
        }
      },
      onKeyUp: (keyCode, keyEvent) => {
        const {scanKey, modalVisible} = this.state;
        console.log(keyCode, scanKey, scanKeyCode, modalVisible, scanKey.indexOf(keyCode));
        if (modalVisible) {
          if (scanKey.indexOf(keyCode) === -1) {
            scanKey.push(keyCode);
          }
          this.setState({
            scanKey
          });

        } else {
          if (scanKey.indexOf(keyCode) > -1) {
            if (scanKey.indexOf(scanKeyCode) > -1) {
              scanKeyCode = null;
              //stop scan
              console.log("stop scan");
              RNUhf.stop();
            }
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

  renderModel(){
    const {scanKey}=this.state;
    return(
        <Modal
            animationType={"none"}
            transparent={true}
            visible={this.state.modalVisible}
        >
          <TouchableOpacity style={{flex:1}} onPress={() => {
            this.setState({modalVisible:!this.state.modalVisible})
          }}>
          <View style={styles.modal}>
            <TouchableOpacity activeOpacity={1}>
              <View style={styles.modalBody}>
                <View style={styles.sectionContainer}>
                  <Text style={styles.sectionHeader}>
                    热键设置
                  </Text>
                  <View style={styles.sectionContainer}>
                    <View style={{flexDirection: 'row'}}>
                    {scanKey.map(key=> <Text style={{marginRight: 8}}>{key}</Text>)}
                    </View>

                    <TouchableOpacity activeOpacity={1} onPress={() => {
                      this.setState({scanKey:[]})
                    }}>
                      <Text>clear</Text>
                    </TouchableOpacity>
                  </View>
                </View>
              </View>
            </TouchableOpacity>
          </View>
          </TouchableOpacity>
        </Modal>
    );
  }

  render() {
    const {info,epc} = this.state;
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
              <View style={{flexDirection: 'row', marginTop: 8}}>
                <View style={{width: '50%', marginRight: 4}}>
                  <Button
                      onPress={() => {
                        this.setState({modalVisible:true})
                      }}
                      title="hot key"
                      color="#841584"
                  /></View>
                <View style={{width: '50%', marginLeft: 4}}>

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
              <View>
                {epc.map(e=><Text>{e}</Text>)}
              </View>
            </View>
            {this.renderModel()}
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
    marginBottom: 8,
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
  modal:{
    flex:1,
    justifyContent:'center',
    backgroundColor:'rgba(0,0,0,0.5)'
  },
  modalBody:{
    margin:10,
    backgroundColor: '#FFF',
    borderRadius: 10,
  }
});

