package tw.com.a_i_t.IPCamViewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;

import tw.com.a_i_t.IPCamViewer.Property.IPCamProperty;
import tw.com.a_i_t.IPCamViewer.Viewer.MjpegPlayerFragment;
import tw.com.a_i_t.IPCamViewer.Viewer.StreamPlayerFragment;

public class VideoPreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        IPCamProperty.init();
        new GetRTPS_AV1().execute();
    }

    /* Query property of RTSP AV1 */
    public class GetRTPS_AV1 extends AsyncTask<URL, Integer, String> {
        String CamIp;

        protected void onPreExecute() {
            CamIp = CameraCommand.getCameraIp();
            Log.d("Get Camera IP", "IP=" + CamIp);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = CameraCommand.commandQueryAV1Url();
            if (url != null) {
                return CameraCommand.sendRequest(url);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String liveStreamUrl;
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();

            if ((dhcpInfo == null || dhcpInfo.gateway == 0) && (CamIp == null)) {
                AlertDialog alertDialog = new AlertDialog.Builder(VideoPreviewActivity.this).create();
                alertDialog.setTitle(getResources().getString(R.string.dialog_DHCP_error));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                        getResources().getString(R.string.label_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            }
            String gateway = MainWiFiActivity.intToIp(dhcpInfo.gateway);
            // set http push as default for streaming
            liveStreamUrl = "http://" + CamIp/*gateway*/ + MjpegPlayerFragment.DEFAULT_MJPEG_PUSH_URL;
            if (result != null) {
                String[] lines;
                try {
                    String[] lines_temp = result.split("Camera.Preview.RTSP.av=");
                    lines = lines_temp[1].split(System.getProperty("line.separator"));
                    int av = Integer.valueOf(lines[0]);
                    //LibVLC instance = LibVLC.getExistingInstance();
                    switch (av) {
                        case 1:    // liveRTSP/av1 for RTSP MJPEG+AAC
                            //instance.setNetworkCaching(mjpeg_cache);
                            liveStreamUrl = "rtsp://" + CamIp/*gateway*/ + MjpegPlayerFragment.DEFAULT_RTSP_MJPEG_AAC_URL;
                            break;
                        case 2: // liveRTSP/v1 for RTSP H.264
                            //instance.setNetworkCaching(h264_cache);
                            liveStreamUrl = "rtsp://" + CamIp/*gateway*/ + MjpegPlayerFragment.DEFAULT_RTSP_H264_URL;
                            break;
                        case 3: // liveRTSP/av2 for RTSP H.264+AAC
                            //instance.setNetworkCaching(h264_cache);
                            liveStreamUrl = "rtsp://" + CamIp/*gateway*/ + MjpegPlayerFragment.DEFAULT_RTSP_H264_AAC_URL;
                            break;
                        case 4: // liveRTSP/av4 for RTSP H.264+PCM
                            //instance.setNetworkCaching(h264_cache);
                            liveStreamUrl = "rtsp://" + CamIp/*gateway*/ + MjpegPlayerFragment.DEFAULT_RTSP_H264_PCM_URL;
                            break;
                    }
                } catch (Exception e) {/* not match, for firmware of MJPEG only */}
            }
            Fragment fragment = StreamPlayerFragment.newInstance(liveStreamUrl);
            getFragmentManager().beginTransaction().replace(R.id.fragment_fl,fragment).commit();
            super.onPostExecute(result);
        }
    }
}
