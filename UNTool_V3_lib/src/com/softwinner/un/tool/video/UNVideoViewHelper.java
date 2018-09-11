package com.softwinner.un.tool.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.softwinner.un.tool.util.UNLog;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 文件名:UNVideoViewHelper.java V2.0 Feb 2,2015<br>
 * 描　述:视频显示画面工具类<br> 
 * 版　权:AW-BU3-PD2<br>
 * @author PD2-Roy
 */
public class UNVideoViewHelper {
	
	public static final String TAG = "UNVideoViewHelper";
	
	//main Object
	private Context             mContext;
	private GlViewDec           mRenderView; //GL View
	private RelativeLayout      mParentView; 
	private SurfaceHolder       mHolder;
	private Renderer            mRenderer;
	private UNVideoViewListener videoViewListener;

	//Listener for call back
	public void setVideoViewListener(UNVideoViewListener videoViewListener) {
		this.videoViewListener = videoViewListener;
	}
	private boolean isVideoViewShow = false; //for the first frame appears



	public boolean isVideoViewShow() {
		return isVideoViewShow;
	}
	
	//shotcut params
	private boolean isShotCut = false;
	
	private List<Bitmap> mShotCutList = null;
	
	//From JNI
	private static native void init(int ptr, int width, int height);
	
	//From JNI
	private static native void render(int ptr);

	public UNVideoViewHelper(Context context, RelativeLayout parentView){
		this.mContext = context;
		this.mParentView = parentView;
		this.init();
	}
	
	private void init() {
		//create renderView
		mRenderView = new GlViewDec(mContext);
		mParentView.addView(mRenderView);
		
		//init renderView layout
		LayoutParams params = mRenderView.getLayoutParams();
		params.height  = LayoutParams.MATCH_PARENT;
		params.width = LayoutParams.MATCH_PARENT;
		mRenderView.setLayoutParams(params);
		mRenderView.setZOrderOnTop(false);
		
		//init callback
		mRenderView.getHolder().addCallback(new Callback() {
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				UNLog.debug_print(UNLog.LV_DEBUG , TAG ," Video display surface is being changed. " + "width " + width + "height " + height);
			} 

			public void surfaceCreated(SurfaceHolder holder) {
				UNLog.debug_print(UNLog.LV_DEBUG , TAG, "Video display surface created");
			}
			public void surfaceDestroyed(SurfaceHolder holder) {
				UNLog.debug_print(UNLog.LV_DEBUG , TAG,"Video display surface destroyed");
			}
		});
		mRenderer = new Renderer();
		((GLSurfaceView) mRenderView).setRenderer(mRenderer); //设置渲染器
//		((GLSurfaceView) mRenderView).setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//render by jni call back
		((GLSurfaceView) mRenderView).setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//render continuously

	}
	
	/**
	 * setOpenGLESDisplay
	 * @param ptr  
	 */
	private void setOpenGLESDisplay(int ptr) {
		UNLog.debug_print(UNLog.LV_DEBUG , TAG,"setOpenGLESDisplay");
		mRenderer.setOpenGLESDisplay(ptr);
	}

	/**
	 * iFrame return， callback here
	 */
	private void showFrameCallBack() {
		if (videoViewListener == null){
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "showFrameCallBack  mListener = null");
			return;
		}
		if (!isVideoViewShow) {
			videoViewListener.videoViewShow();
			isVideoViewShow = true;
		}else{
			videoViewListener.videoViewShowing();
		}
	}
	
	/**
	 * when uninit frame ,callback here
	 */
	private void stopFrameCallBack(){
		UNLog.debug_print(UNLog.LV_DEBUG , TAG, "stopFrameCallBack");
		isVideoViewShow = false;
		if (videoViewListener == null){
			UNLog.debug_print(UNLog.LV_DEBUG , TAG, "stopFrameCallBack  mListener = null");
			return; 
		}
		videoViewListener.videoViewEnd();
	}

	
	private  class Renderer implements GLSurfaceView.Renderer {
		int ptr;
		boolean initPending;
		int width, height;
		
		public Renderer() {
			UNLog.debug_print(UNLog.LV_DEBUG , TAG, "Renderer Create");
			ptr = 0;
			initPending = false;
		}

		public void setOpenGLESDisplay(int ptr) { //设置ESDisplay把刷新的内容传送到surface
			synchronized (this) {
				if (this.ptr != 0 && ptr != this.ptr) {
					initPending = true;
				}
				this.ptr = ptr;
			}
		}

		public void onDrawFrame(GL10 gl) {
//			UNGlobal.debug_print(TAG,"onDrawFrame");
			synchronized (this) {
				if (ptr == 0) {
					gl.glClear(GL10.GL_COLOR_BUFFER_BIT
							| GL10.GL_DEPTH_BUFFER_BIT);
					return;
				}
				
				if (initPending) {
					init(ptr, width, height);
					initPending = false;
				}
				
				render(ptr);//更新画面
				
				if(isShotCut){
					isShotCut = false;
					SavePixels(gl);
				}
				
			}
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			UNLog.debug_print(UNLog.LV_DEBUG , TAG,"onSurfaceChanged width " + width + "height " + height);
			this.width = width;
			this.height = height;
			initPending = true;
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			UNLog.debug_print(UNLog.LV_DEBUG , TAG,"onSurfaceCreated");
		}
	}
	
	
	/**
	 * shotcut interface
	 */
	public void goShotCut(){
		this.isShotCut = true;
	}
	
	/**
	 * get bitmap after the 'goShotCut'
	 * @return
	 */
	public Bitmap getShotCut(){
		Bitmap bitmap = null;
		if (mShotCutList.size() > 0) {
			UNLog.debug_print(UNLog.LV_DEBUG , TAG,"mShotCutList size = " + mShotCutList.size());
			bitmap = mShotCutList.get(0);
			mShotCutList.remove(0);
		}
		return bitmap;
	}
	
	/**
	 * GL context to Bitmap
	 * @param gl
	 * @return
	 */
	private Bitmap SavePixels(GL10 gl) {
		UNLog.debug_print(UNLog.LV_DEBUG , TAG,"SavePixels");
		int x = 0;
		int y = 0;
		LayoutParams params = this.mParentView.getLayoutParams();
		int h = params.height;
		int w = params.width;
		UNLog.debug_print(UNLog.LV_DEBUG , TAG,"x = " + x + ";y = " + y + ";h = " + h + "; w = " + w );
		
		int b[] = new int[w * h];
		int bt[] = new int[w * h];
		IntBuffer ib = IntBuffer.wrap(b);
		ib.position(0);
		gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pix = b[i * w + j];
				int pb = (pix >> 16) & 0xff;
				int pr = (pix << 16) & 0x00ff0000;
				int pix1 = (pix & 0xff00ff00) | pr | pb;
				bt[(h - i - 1) * w + j] = pix1;
			}
		}
		Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
		if (mShotCutList == null) {
			mShotCutList = new ArrayList<Bitmap>();
		}
		mShotCutList.add(sb);
		
		if (videoViewListener != null) {
			videoViewListener.videoViewShotCutEnd();
		}
		UNLog.debug_print(UNLog.LV_DEBUG , TAG,"sb height = " + sb.getHeight());
		return sb;
	}
	
	/**
	 * Video View Listener
	 * @author AW-Roy
	 */
	public interface UNVideoViewListener{
		
		/**
		 * The First IFrame arrived, then call back here 
		 */
		public void videoViewShow();
		
		
		/**
		 * Every IFrame arrived, then call back here 
		 */
		public void videoViewShowing();
		
		
		/**
		 * Video end, then call back here 
		 */
		public void videoViewEnd();
		
		/**
		 * call 'goShotcut', Bitmap Saved in List then call back here 
		 */
		public void videoViewShotCutEnd();
		
	}
}
