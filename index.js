
import { DeviceEventEmitter, NativeModules } from 'react-native';

const { RNUhf } = NativeModules;

//export default RNUhf;
let listener=null;

export default {
    getFirmware: function () {
        return RNUhf.getFirmware();
    },
    getEpc: function () {
        return RNUhf.getEpc();
    },
    showResult:function (callback) {
        if (!listener && callback && typeof callback === "function") {
            listener = DeviceEventEmitter.addListener('showResult', (ret) => callback(ret.data));
        }
    }
}