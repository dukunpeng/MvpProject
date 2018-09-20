package com.example.newtest.permission;

/**
 * @author Mark
 * @create 2018/9/20
 * @Describe
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

import com.example.newtest.R;
import com.example.newtest.log.XLog;


/**
 * 权限检测
 *
 * @author 容联•云通讯
 * @since 2017/3/15
 */
public class PermissionActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0x10;
    public static final int PERMISSIONS_REQUEST_CAMERA = 0x11;
    public static final int PERMISSIONS_REQUEST_VOICE = 0x12;
    public static final int PERMISSIONS_REQUEST_CAMERA_EXTERNAL = 0x13;
    public static final int PERMISSIONS_REQUEST_VOICE_EXTERNAL = 0x14;

    public static final int PERMISSIONS_REQUEST_LOCATION = 0x15;
    public static final int PERMISSIONS_REQUEST_INIT = 0x16;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 0x17;
    public static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0x18;
    public static final int PERMISSIONS_REQUEST_JOIN = 0x19;

    public int RC_SETTINGS_SCREEN = 0x1001;
    public static final String[] needPermissionsVoice = new String[]{Manifest.permission.RECORD_AUDIO};
    public static final String[] needPermissionsCamera = new String[]{Manifest.permission.CAMERA};
    public static final String[] needPermissionsVoiceExternal = new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] needPermissionsCameraExternal = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    public static final String needPermissionsReadExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String needPermissionsReadContacts = Manifest.permission.READ_CONTACTS;

    public static final String needPermissionsCallPhone = Manifest.permission.CALL_PHONE;
    public static final String[] needPermissionsReadPhoneState = new String[]{Manifest.permission.READ_PHONE_STATE};

    public static final String[] needPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};

    public static final String[] needPermissionsInit = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, /*Manifest.permission.READ_CONTACTS,*/ Manifest.permission.READ_PHONE_STATE};

    public static final String[] needPermissionsJoin = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE};

    public static final String[] needPermissionsLocations = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 //   Toast.showMessage(grantResults.length + " permision.length");
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsReadExternalStorage)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleSDCard), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_VOICE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsVoice)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleVoice), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_VOICE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsVoiceExternal)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleVoiceExternal), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsCamera)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleCamera), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_CAMERA_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsCameraExternal)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleCameraExternal), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;

            case PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsLocations)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleLocation), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_INIT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsInit)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleInit), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsReadContacts)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleContacts), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
//                    if (RedPacketManager.getInstance().getOnRedPacketListener() != null) {
//                        RedPacketManager.getInstance().getOnRedPacketListener().onInitRedPacket(this);
//                    }
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsReadContacts)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleReadPhoneState), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            case PERMISSIONS_REQUEST_JOIN:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    XLog.d("onRequestPermissionsResult: Permission granted");
                } else {
                    XLog.d("onRequestPermissionsResult: Permission denied");
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, needPermissionsJoin)) {
                        EasyPermissionsEx.goSettings2Permissions(this, getString(R.string.goSettingsRationaleJoin), getString(R.string.set), RC_SETTINGS_SCREEN);
                    }
                }
                break;
            default:
                XLog.d("onRequestPermissionsResult: default error...");
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        XLog.e("activity","namer = "+this.getClass().getSimpleName());
    }

}
