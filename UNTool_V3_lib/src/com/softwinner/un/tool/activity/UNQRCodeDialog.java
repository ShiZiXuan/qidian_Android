package com.softwinner.un.tool.activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.softwinner.un.tool.R;

public class UNQRCodeDialog extends Dialog{

	private Context    context;
	private Button     cancel;
	private Button     confirm;
	private ImageView  iv_qrcode;
	
	public UNQRCodeDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}
	
	private void initView(){
		
		this.setContentView(R.layout.dialog_qrcode);
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UNQRCodeDialog.this.cancel();
			}
		});
		
		confirm = (Button) findViewById(R.id.confirm);
		iv_qrcode = (ImageView) findViewById(R.id.qrcode);
		
		Window mWindow = this.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();    
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		lp.width  = width;
//		lp.height = DIALOG_HEIGHT;
//		mWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
		mWindow.setGravity(Gravity.CENTER_HORIZONTAL);
//		mWindow.setWindowAnimations(R.style.video_lanopt_dialog_Anim);
		this.onWindowAttributesChanged(lp);
		
	}
	
	public void setConfirmListener(View.OnClickListener onClickListener){
		
		confirm.setOnClickListener(onClickListener);
		
	}
	
	public void setCancelListener(View.OnClickListener onClickListener){
		cancel.setOnClickListener(onClickListener);
	}
	
	public void setQRCode(Bitmap bitmap){
		iv_qrcode.setImageBitmap(bitmap);
	}
	
}
