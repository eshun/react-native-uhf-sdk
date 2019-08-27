
import { DeviceEventEmitter, NativeModules } from 'react-native';

const { RNUhf } = NativeModules;

//export default RNUhf;
let listener=null;


export default {
    init: function(callback) {
        if (!listener && callback && typeof callback === "function") {
            listener = DeviceEventEmitter.addListener('showResult', (ret) => callback(ret.data));
        }
    },
    reset: function () {
        return RNUhf.reset();
    },
    getFirmware: function () {
        return RNUhf.getFirmware();
    },
    getVersion: function () {
        return RNUhf.getVersion();
    },
    getMakerInfo: function () {
        return RNUhf.getMakerInfo();
    },
    getEpc: function () {
        return RNUhf.getEpc();
    },
    startScan: function () {

    },
    stopScan: function () {

    },
    setWorkArea: function () {

    },
}