package tw.com.a_i_t.IPCamViewer;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Window;

import tw.com.a_i_t.IPCamViewer.DroneCam.DroneCamViewer;
import tw.com.a_i_t.IPCamViewer.FileBrowser.FileBrowserFragment;

public class MediaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_media);
        Fragment fragment = FileBrowserFragment.newInstance(null, null, null);
        fragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction().replace(R.id.fragment_fl, fragment).commit();
    }
}
