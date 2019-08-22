
import { NativeModules } from 'react-native';

const { RNUhf } = NativeModules;

//export default RNUhf;

export default {
    getFirmware: function () {
        return RNUhf.getFirmware();
    },
}