package com.uautogo.qidian.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uautogo.qidian.R;
import com.uautogo.qidian.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 我的头像
 */
public class HeadActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout myhead_linear, name_linear, address_linear;
    private ImageView back_iv, head_iv;
    private TextView name_tv;
    private PopupWindow popWindow;
    private LinearLayout parent;
    private EditText editText;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        parent = findViewById(R.id.activity_head);
        myhead_linear = (LinearLayout) findViewById(R.id.myhead_linear);
        name_linear = (LinearLayout) findViewById(R.id.name_linear);
        address_linear = (LinearLayout) findViewById(R.id.address_linear);
        back_iv = (ImageView) findViewById(R.id.center_top_bar_left_img);
        head_iv = (ImageView) findViewById(R.id.head_iv);
        name_tv = (TextView) findViewById(R.id.name_tv);
        myhead_linear.setOnClickListener(this);
        name_linear.setOnClickListener(this);
        address_linear.setOnClickListener(this);
        back_iv.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name", "");
        String img = bundle.getString("img", "");

        Glide.with(this).load(img).into(head_iv);

        name_tv.setText(name);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_top_bar_left_img:
                finish();
                break;
            case R.id.myhead_linear: {//跟换头像跳转到手机相册里
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
                break;
            }
            case R.id.name_linear://修改昵称
                showEditDialog();
                popupInputMethodWindow();
                break;
            case R.id.address_linear: {
                Intent intent = new Intent(HeadActivity.this, AddressListActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void popupInputMethodWindow() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri headUri = data.getData();
                head_iv.setImageURI(headUri);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(headUri);
                    File file = new File("/storage/emulated/0/qidian/data/file/img/");
                    if (!file.exists())
                        file.mkdirs();
                    FileOutputStream fos = new FileOutputStream("/storage/emulated/0/qidian/data/file/img/head.jpg");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                    Toast.makeText(HeadActivity.this,"更换成功!",Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.putString(HeadActivity.this,"isHeadReplace","ok");//存储用户是否更改头像
                    inputStream.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showEditDialog() {
        View view = null;
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.manage_username_dialog,null);

            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view,LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        editText = (EditText) view.findViewById(R.id.edit_dialog_input);
        Button yesBtn = (Button) view.findViewById(R.id.edit_dialog_username_ok);
        Button cancelBtn = (Button) view.findViewById(R.id.edit_dialog_username_cancel);
//popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        darkenBackground(0.2f);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        //监听菜单的关闭事件


        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().trim().equals("")) {
                    SharedPreferencesUtils.putString(HeadActivity.this, SharedPreferencesUtils.Key.KEY_NICKY, editText.getText().toString().trim());
                    name_tv.setText(editText.getText().toString().trim());
                    SharedPreferencesUtils.putString(HeadActivity.this, "isNameReplace","ok");
                }
                popWindow.dismiss();
            }
        });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });

    }
    private void darkenBackground(Float bgcolor){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }


}
