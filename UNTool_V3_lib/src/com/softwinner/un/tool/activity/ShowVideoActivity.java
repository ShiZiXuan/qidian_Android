package com.softwinner.un.tool.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.softwinner.un.tool.R;
import com.softwinner.un.tool.util.UNTool;
import com.softwinner.un.tool.video.UNVideoViewHelper;
import com.softwinner.un.tool.video.UNVideoViewHelper.UNVideoViewListener;

public class ShowVideoActivity extends QidianBaseActivity implements OnClickListener{

	private final String TAG = "ShowVideoActivity";
	public static final int VIDEO_BEGIN_SHOW = 100;
	public static final int VIDEO_END_SHOW   = 101;
	
	private RelativeLayout parentView; //RelativeLayout容器
	private LayoutParams landscapeParams;//横屏参数
	private LayoutParams portraitParams;//竖屏参数
	private UNVideoViewHelper videoHelper;//视频显示画面工具
	private MyUNVideoViewListener videoViewListener;
	private Button btnShow;
	private Button btnClose;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_video);
		
		btnShow  = (Button) findViewById(R.id.show);
		btnShow.setOnClickListener(this);
		btnClose = (Button) findViewById(R.id.close);
		btnClose.setOnClickListener(this);
		//获取屏幕的尺寸
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width  = dm.widthPixels;//宽度height = dm.heightPixels ;//高度
		int height = dm.heightPixels;
		
		/**
		 *1、初始化容器RelativeLayout，负责放置UNVideoViewHelper，并初始化layout横屏、竖屏params。
		 */
		parentView = (RelativeLayout) findViewById(R.id.parentview);
		landscapeParams = new LayoutParams(height, height*9/16);
		landscapeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		portraitParams = new LayoutParams(width, width *9 / 16);
		portraitParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		portraitParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		portraitParams.setMargins(0, 15, 0, 0);
		
		parentView.setLayoutParams(portraitParams);
		
		/**
		 *2、创建VideoViewHelper 
		 *显示视频显示画面工具类
		 */
		videoHelper = new UNVideoViewHelper(this, parentView);
		
		/**
		 *3、 重写&创建Listener，videoViewHelper设置Listener
		 */
		videoViewListener = new MyUNVideoViewListener();
		videoHelper.setVideoViewListener(videoViewListener);
		parentView.post(new Runnable() {
			@Override
			public void run() {
				showVideo();
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void onConfigurationChanged(android.content.res.Configuration newConfig) {
		
		super.onConfigurationChanged(newConfig);
		 // Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show();
			parentView.setLayoutParams(landscapeParams);
			
			WindowManager.LayoutParams attrs = getWindow().getAttributes(); 
			attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN; 
			getWindow().setAttributes(attrs); 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); 
	        
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
			Toast.makeText(this, "竖屏", Toast.LENGTH_SHORT).show();
			parentView.setLayoutParams(portraitParams);
			
			WindowManager.LayoutParams attrs = getWindow().getAttributes(); 
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN); 
			getWindow().setAttributes(attrs); 
			//取消全屏设置
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
		
	};
	
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case VIDEO_BEGIN_SHOW:
				//Toast.makeText(ShowVideoActivity.this, "Video appears", Toast.LENGTH_SHORT).show();
				break;
			case VIDEO_END_SHOW:
				//Toast.makeText(ShowVideoActivity.this, "Video end totally", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			
		};
	};
	
	/**
	 * 监听画面显示状态
	 * @author AW-Roy
	 *
	 */
	class MyUNVideoViewListener implements UNVideoViewListener{

		/**
		 * 第一帧显示（一般用户更新UI界面）
		 */
		@Override
		public void videoViewShow() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(VIDEO_BEGIN_SHOW);
		}

		/**
		 * I Frame返回（每个I Frame均回调此处，可做心跳监听）
		 */
		@Override
		public void videoViewShowing() {
			// TODO Auto-generated method stub
			
		}
		
		/**
		 * 视频结束回调（异常退出或正常退出，返回命令RTSP_STREAM_CLOSE比较准时）
		 */
		@Override
		public void videoViewEnd() {
			// TODO Auto-generated method stub
			handler.sendEmptyMessage(VIDEO_END_SHOW);
		}

		/**
		 * videoViewHelper.goShotCut()调用截图，截图完成后回调此处，可获取Bitmap
		 */
		@Override
		public void videoViewShotCutEnd() {
			// TODO Auto-generated method stub
			
		}
		
	}

	private void showVideo(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				UNTool.getInstance().sendStartVideoStream(videoHelper, StartActivity.unDevice.getSid());
			}

		}).start();
	}
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int i = arg0.getId();
		if (i == R.id.show) {
		 showVideo();
		} else if (i == R.id.close) {/**
		 *5、结束监控画面
		 */
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					UNTool.getInstance().sendStopVideoStream(StartActivity.unDevice.getSid());
					finish();
				}

			}).start();

		} else {
		}
	}
	
}
