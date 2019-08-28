
import { DeviceEventEmitter, NativeModules } from 'react-native';

const { RNUhf } = NativeModules;

//export default RNUhf;
let listener=null;

let defaultOptions={
    info:function (data) {
        
    },
    scan:function (data) {
        
    },
    fail:function (err) {
        
    }
};

export default {
    init: function(options) {
        Object.assign(defaultOptions, options);

        if (!listener) {
            listener = DeviceEventEmitter.addListener('uhfEvent', (ret) => {
                if (ret.info) {
                    defaultOptions.info(ret.info);
                } else if (ret.scan) {
                    defaultOptions.scan(ret.scan);
                } else if (ret.err) {
                    defaultOptions.fail(ret.err);
                }
            });
        }
    },
    destroy: function() {
        listener = null;
        RNUhf.pause();
    },
    reset: function () {
        RNUhf.reset();
    },
    getFirmware: function () {
        RNUhf.getFirmware();
    },
    getVersion: function () {
        RNUhf.getVersion();
    },
    getMakerInfo: function () {
        RNUhf.getMakerInfo();
    },
    getEpc: function () {
        return RNUhf.getEpc();
    },
    scan: function () {
        return RNUhf.scan();
    },
    stop: function () {
        return RNUhf.stop();
    },
    getPower: function () {
        RNUhf.getPower();
    },
    setPower: function (power) {
        RNUhf.setPower(power);
    }
}