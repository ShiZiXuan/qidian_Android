package tw.com.a_i_t.IPCamViewer;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//import org.videolan.vlc.VLCApplication ;

import tw.com.a_i_t.IPCamViewer.Property.IPCamProperty;
import tw.com.a_i_t.IPCamViewer.Viewer.MjpegPlayerFragment;
import tw.com.a_i_t.IPCamViewer.Viewer.StreamPlayerFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.Window;

public class MainWiFiActivity extends Activity {
    //public StreamPlayerFragment sp;
    private static Context mContext;

    public static boolean isVideo;

    public static String intToIp(int addr) {

        return ((addr & 0xFF) + "." + ((addr >>>= 8) & 0xFF) + "." + ((addr >>>= 8) & 0xFF) + "." + ((addr >>>= 8) & 0xFF));
    }

    public static String getSnapshotFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime + ".jpg";
    }

    public static String getMJpegFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }

    public static String sAppName = "";
    public static String sAppDir = "";

    public static File getAppDir() {
        File appDir = new File(sAppDir);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir;
    }

    public static Uri addImageAsApplication(ContentResolver contentResolver, String name, long dateTaken,
                                            String directory, String filename) {

        String filePath = directory + File.separator + filename;

        String[] imageProjection = new String[]{"DISTINCT " + BaseColumns._ID, MediaColumns.DATA,
                MediaColumns.DISPLAY_NAME};

        String imageSelection = new String(Images.Media.TITLE + "=? AND " + Images.Media.DISPLAY_NAME + "=?");

        String[] imageSelectionArgs = new String[]{name, filename};

        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageProjection,
                imageSelection, imageSelectionArgs, null);

        if (cursor == null || cursor.getCount() == 0) {

            ContentValues values = new ContentValues(7);
            values.put(Images.Media.TITLE, name);
            values.put(Images.Media.DISPLAY_NAME, filename);
            values.put(Images.Media.DATE_TAKEN, dateTaken);
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            values.put(Images.Media.DATA, filePath);

            return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {

            int idColumn = cursor.getColumnIndex(MediaColumns._ID);

            if (idColumn == -1)
                return null;

            cursor.moveToFirst();

            Long id = cursor.getLong(idColumn);

            return Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/"
                    + String.valueOf(id));
        }
    }

    public static void addFragment(Fragment originalFragment, Fragment newFragment) {

        FragmentManager fragmentManager = originalFragment.getActivity().getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {

            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager
                    .getBackStackEntryCount() - 1);

            if (backEntry != null && backEntry.getName().equals(newFragment.getClass().getName()))
                return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

//		fragmentTransaction.setCustomAnimations(R.animator.left_in, R.animator.right_out, R.animator.left_in, R.animator.left_out);
        fragmentTransaction.addToBackStack(newFragment.getClass().getName());
        fragmentTransaction.add(R.id.mainMainFragmentLayout, newFragment);
        fragmentTransaction.commit();
//        fragmentManager.executePendingTransactions();
    }

    public static void backToFristFragment(Activity activity) {

        FragmentManager fragmentManager = activity.getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() == 0)
            return;

        BackStackEntry rootEntry = fragmentManager.getBackStackEntryAt(0);

        if (rootEntry == null)
            return;

        int rootFragment = rootEntry.getId();
        fragmentManager.popBackStack(rootFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

    public boolean mEngineerMode = false;

    private static Locale sDefaultLocale;
    private static Locale sSelectedLocale;

    static {
        sDefaultLocale = Locale.getDefault();
    }

    public static Locale getDefaultLocale() {

        return sDefaultLocale;
    }

    public static void setAppLocale(Locale locale) {

        Locale.setDefault(locale);
        sSelectedLocale = locale;
    }

    public static Locale getAppLocale() {

        return sSelectedLocale == null ? sDefaultLocale : sSelectedLocale;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.setProperty("http.keepAlive", "false");
        super.onCreate(savedInstanceState);
        if (sSelectedLocale == null) {
            sSelectedLocale = sDefaultLocale;
        }
        sAppName = getResources().getString(R.string.app_name);

        Locale.setDefault(sSelectedLocale);
        Configuration config = new Configuration();
        config.locale = sSelectedLocale;
        getResources().updateConfiguration(config, null);

        sAppDir = Environment.getExternalStorageDirectory().getPath() + File.separator + sAppName;
        Log.i("Fragment Activity", sAppDir);
        File appDir = new File(sAppDir);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_wifi);
        IPCamProperty.init();
        mContext = this;
        if (savedInstanceState == null) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(
                    Context.WIFI_SERVICE);

            Log.i("Wifi Info", wifiManager.getConnectionInfo().toString());
            if ((wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo().getNetworkId() != -1) || CameraCommand.isAPEnable()) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = new FunctionListFragment();
                fragmentTransaction.add(R.id.mainMainFragmentLayout, fragment);
                fragmentTransaction.hide(fragment);
                fragmentTransaction.commit();
//                new GetRTPS_AV1().execute();
            } else {
                String title = getResources().getString(R.string.dialog_no_connection_title);
                String message = getResources().getString(R.string.dialog_no_connection_message);

                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton(getResources().getString(R.string.label_ok),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        // MainWiFiActivity.this.finish() ;
                                        dialog.dismiss();

                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                                        Fragment fragment = new FunctionListFragment();
                                        fragmentTransaction.add(R.id.mainMainFragmentLayout, fragment);
                                        fragmentTransaction.hide(fragment);
                                        fragmentTransaction.commit();
//                                        new GetRTPS_AV1().execute();
                                    }
                                }).show();
            }
        }
    }

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
                AlertDialog alertDialog = new AlertDialog.Builder(MainWiFiActivity.this).create();
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
//            MainWiFiActivity.addFragment(FunctionListFragment.this, fragment);
            getFragmentManager().beginTransaction().replace(R.id.mainMainFragmentLayout, fragment).commit();
            super.onPostExecute(result);
        }
    }

    SubMenu mLanguageSubMenu;
    String[] mLanguageNames;
    Locale[] mLocales;

    SubMenu mNetworkCacheSizeMenu;
    int[] mCacheSize;

    SubMenu mConnectionDelayMenu;
    int[] mConnectionDelay;
    public static int sConnectionDelay = 2000;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        mLanguageNames = new String[]{getResources().getString(R.string.label_default), "English", getResources().getString(R.string.label_language_TChinese),
                getResources().getString(R.string.label_language_SChinese), getResources().getString(R.string.label_language_Russian),
                getResources().getString(R.string.label_language_Spanish), getResources().getString(R.string.label_language_Portuguese),
                getResources().getString(R.string.label_language_French), getResources().getString(R.string.label_language_Italian),
                getResources().getString(R.string.label_language_Germany), getResources().getString(R.string.label_language_Czech),
                getResources().getString(R.string.label_language_Japanese), getResources().getString(R.string.label_language_Korean),
                getResources().getString(R.string.label_language_Latvian), getResources().getString(R.string.label_language_Polish),
                getResources().getString(R.string.label_language_Romanian), getResources().getString(R.string.label_language_Slovak),
                getResources().getString(R.string.label_language_Ukrainian)};

        mLocales = new Locale[]{MainWiFiActivity.getDefaultLocale(), Locale.ENGLISH,
                Locale.TRADITIONAL_CHINESE, Locale.SIMPLIFIED_CHINESE, new Locale("ru", "RU"), new Locale("es", "ES"),
                new Locale("pt", "PT"), Locale.FRANCE, Locale.ITALY, Locale.GERMANY, new Locale("cs", "CZ"), Locale.JAPAN, Locale.KOREA,
                new Locale("lv", "LV"), new Locale("pl", "PL"), new Locale("ro", "RO"), new Locale("sk", "SK"), new Locale("uk", "UA")};

        //getMenuInflater().inflate(R.menu.main, menu) ;

        mLanguageSubMenu = menu.addSubMenu(0, 0, 0, getResources().getString(R.string.label_language));

        int i = 0;
        for (String language : mLanguageNames) {

            MenuItem item = mLanguageSubMenu.add(0, i++, 0, language);
            item.setCheckable(true);

        }
/*		
        mNetworkCacheSizeMenu = menu.addSubMenu(0, 0, 0, "Cache Size") ;
		mCacheSize = new int[] {100, 1000, 1500, 2000, 2500} ;
*/
//		int cacheSize = 100 ;
//        Context context = VLCApplication.getAppContext();
//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        //set value of cache size to VLC settings.
//        SharedPreferences.Editor editor = pref.edit() ;
//        editor.putInt("network_caching_value", 2000) ;//set default value of cache size with 100ms.
//        editor.commit() ;

        /*
         * i = 0 ;
		for (int cacheSize : mCacheSize) {

			MenuItem item = mNetworkCacheSizeMenu.add(0, i++, 0, String.valueOf(cacheSize)) ;
			item.setCheckable(true) ;
		}
		*/
        /*		
        mConnectionDelayMenu = menu.addSubMenu(0, 0, 0, "Connection Delay") ;
		mConnectionDelay = new int[] {500, 1000, 1500, 2000, 2500, 3000, 3500, 4000} ;
	
		i = 0 ;
		for (int connectionDelay : mConnectionDelay) {

			MenuItem item = mConnectionDelayMenu.add(0, i++, 0, String.valueOf(connectionDelay)) ;
			item.setCheckable(true) ;
		}
*/

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (mLanguageSubMenu != null) {

            int size = mLanguageSubMenu.size();

            for (int i = 0; i < size; i++) {
                MenuItem item = mLanguageSubMenu.getItem(i);

                if (i > 0 && getAppLocale().equals(mLocales[i])) {
                    item.setChecked(true);
                } else {
                    item.setChecked(false);
                }

                item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem checkedItem) {

                        int size = mLanguageSubMenu.size();

                        for (int i = 0; i < size; i++) {
                            MenuItem item = mLanguageSubMenu.getItem(i);
                            if (checkedItem == item && item.isChecked() == false) {
                                item.setChecked(true);

                                setAppLocale(mLocales[i]);

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            } else {
                                item.setChecked(false);
                            }
                        }
                        return true;
                    }
                });
            }
        }
/*		
        if (mNetworkCacheSizeMenu != null) {

			int size = mNetworkCacheSizeMenu.size() ;
			//for JWD request: no delay so set cache size with 100ms.It didnot work with 0ms.
			int cacheSize = 100 ;
            Context context = VLCApplication.getAppContext();
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            
            //set value of cache size to VLC settings.
            SharedPreferences.Editor editor = pref.edit() ;
            editor.putInt("network_caching_value", 100) ;//set default value of cache size with 100ms.
            editor.commit() ;

			for (int i = 0 ; i < size ; i++) {
				MenuItem item = mNetworkCacheSizeMenu.getItem(i) ;

				if (mCacheSize[i] == cacheSize) {
					item.setChecked(true) ;
				} else {
					item.setChecked(false) ;
				}

				item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem checkedItem) {

						int size = mNetworkCacheSizeMenu.size() ;

						for (int i = 0 ; i < size ; i++) {
							MenuItem item = mNetworkCacheSizeMenu.getItem(i) ;
							if (checkedItem == item && item.isChecked() == false) {
								item.setChecked(true) ;

					            Context context = VLCApplication.getAppContext();
					            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

					            SharedPreferences.Editor editor = pref.edit() ;
					            editor.putInt("network_caching_value", mCacheSize[i]) ;
					            editor.commit() ;

							} else {
								item.setChecked(false) ;
							}
						}
						return true ;
					}
				}) ;
			}
		}
*/		
/*		
        if (mConnectionDelayMenu != null) {

			int size = mConnectionDelayMenu.size() ;

			int connectionDelay = sConnectionDelay ;

			for (int i = 0 ; i < size ; i++) {
				MenuItem item = mConnectionDelayMenu.getItem(i) ;

				if (mConnectionDelay[i] == connectionDelay) {
					item.setChecked(true) ;
				} else {
					item.setChecked(false) ;
				}

				item.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem checkedItem) {

						int size = mConnectionDelayMenu.size() ;

						for (int i = 0 ; i < size ; i++) {
							MenuItem item = mConnectionDelayMenu.getItem(i) ;
							if (checkedItem == item && item.isChecked() == false) {
								item.setChecked(true) ;
								sConnectionDelay = mConnectionDelay[i] ;

							} else {
								item.setChecked(false) ;
							}
						}
						return true ;
					}
				}) ;
			}
		}
*/

        //menu.findItem(R.id.engineerMode).setChecked(mEngineerMode) ;
        //menu.findItem(R.id.engineerMode).setOnMenuItemClickListener(new OnMenuItemClickListener() {

        //@Override
        //public boolean onMenuItemClick(MenuItem item) {
        //item.setChecked(!item.isChecked()) ;
        //mEngineerMode = item.isChecked() ;
        //return true ;
        //}
        //}) ;

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //

                // NavUtils.navigateUpFromSameTask(this) ;

                backToFristFragment(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public static Context getAppContext() {
        return mContext;
    }

    @Override
    public void onDestroy() {
        //mVideoView.releasePlayer();
        super.onDestroy();
    }
}
