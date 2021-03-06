
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

    },
    onKeyDown:function (keyCode,keyEvent) {

    },
    onKeyUp:function (keyCode,keyEvent) {

    }
};

export default {
    init: function (options) {
        Object.assign(defaultOptions, options);
        if (!listener) {
            listener = DeviceEventEmitter.addListener('uhfEvent', (ret) => {
                if (ret.info) {
                    defaultOptions.info(ret.info);
                } else if (ret.scan) {
                    defaultOptions.scan(ret.scan);
                } else if (ret.err) {
                    defaultOptions.fail(ret.err);
                } else if (ret.keyEvent) {
                    if (ret.on === "onKeyDown") {
                        defaultOptions.onKeyDown(ret.keyCode, ret.keyEvent);
                    } else if (ret.on === "onKeyUp") {
                        defaultOptions.onKeyUp(ret.keyCode, ret.keyEvent);
                    }
                } else {
                    console.log(ret);
                }
            });
        }
        RNUhf.stop();
    },
    destroy: function () {
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
    /**
     * 最小值15，最大值26
     * @param power
     */
    setPower: function (power) {
        if (power > 14 && power < 27) {
            RNUhf.setPower(power);
        } else {
            throw new Error("power in 15~26");
        }
    },
    play: function () {
        RNUhf.play();
    }
}