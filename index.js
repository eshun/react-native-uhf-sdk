
import { DeviceEventEmitter, NativeModules } from 'react-native';

const { RNUhf } = NativeModules;

//export default RNUhf;
let listener=null;

export default {
    getFirmware: function () {
        return RNUhf.getFirmware();
    },
    showResult:function (callback) {
        if (!listener && callback && typeof callback === "function") {
            DeviceEventEmitter.addListener('showResult', (data) => callback(data));
        }
    }
}