package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.AllMsgActivity;
import com.uautogo.qidian.data.GsonHelper;
import com.uautogo.qidian.model.BankCardInfo;
import com.uautogo.qidian.model.IDCardFan;
import com.uautogo.qidian.model.IDCardZheng;
import com.uautogo.qidian.model.UploadFileResponse;
import com.uautogo.qidian.utils.CommonUtil;
import com.uautogo.qidian.utils.CompressUtils;
import com.uautogo.qidian.utils.NavigationBarUtil;
import com.uautogo.qidian.utils.OCRHelper;
import com.uautogo.qidian.utils.ToastUtil;
import com.uautogo.qidian.view.LoadingDialog;
import com.uautogo.qidian.widget.OCRDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;



/**
 * Created by linjing on 2018/4/22.
 * 车主信息
 */

public class CarOwnerSmgFragment extends Fragment implements View.OnClickListener {
    RelativeLayout shenfenzheng_zheng_rl, shenfenzheng_fan_rl, yinhangka_copy_rl;
    ImageView upload1_iv, upload2_iv, upload6_iv;
    EditText bank_phone_et, certification_code_et;
    TextView get_certification_code_tv, bank_card_phone_tv;
    String mCurrentPhotoPath;

    OCRHelper helper = new OCRHelper();

    private int currentChoose = 0;

    BankCardInfo bankCardInfo;
    IDCardZheng idCardZheng;
    IDCardFan idCardFan;

    private String [] imagesArray = new String[3];

    private static final int shenfenzheng_zheng = 1;
    private static final int shenfenzheng_fan = 2;
    private static final int yinhangka_copy = 3;


    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 2;

    private OCRDialog mDialog;
    private Button copy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_owner_msg, container, false);
        initView(view);
        mDialog = new OCRDialog(getActivity(), 1,getActivity().getApplication());
        return view;
    }

    private void initView(View view) {
        bank_card_phone_tv = (TextView) view.findViewById(R.id.bank_card_phone_tv);
        shenfenzheng_zheng_rl = (RelativeLayout) view.findViewById(R.id.shenfenzheng_zheng_rl);
        shenfenzheng_fan_rl = (RelativeLayout) view.findViewById(R.id.shenfenzheng_fan_rl);
        yinhangka_copy_rl = (RelativeLayout) view.findViewById(R.id.yinhangka_copy_rl);
        upload1_iv = (ImageView) view.findViewById(R.id.upload1_iv);
        upload2_iv = (ImageView) view.findViewById(R.id.upload2_iv);
        upload6_iv = (ImageView) view.findViewById(R.id.upload6_iv);
        bank_phone_et = (EditText) view.findViewById(R.id.bank_phone_et);
        certification_code_et = (EditText) view.findViewById(R.id.certification_code_et);
        get_certification_code_tv = (TextView) view.findViewById(R.id.get_certification_code_tv);
        copy = view.findViewById(R.id.copy_bt);
        shenfenzheng_zheng_rl.setOnClickListener(this);
        shenfenzheng_fan_rl.setOnClickListener(this);
        yinhangka_copy_rl.setOnClickListener(this);
        get_certification_code_tv.setOnClickListener(this);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText("1192842554");
                Toast.makeText(getActivity(), "复制成功!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_certification_code_tv:
                getVcode();
                break;
            case R.id.shenfenzheng_zheng_rl:
                currentChoose = shenfenzheng_zheng;
                showPopupWindow();
                break;
            case R.id.shenfenzheng_fan_rl:
                currentChoose = shenfenzheng_fan;
                showPopupWindow();
                break;
            case R.id.yinhangka_copy_rl:
                currentChoose = yinhangka_copy;
                showPopupWindow();
                break;

        }
    }

    private void showPopupWindow() {
        View popView = View.inflate(getActivity(), R.layout.popupwindow_camera_need, null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 1 / 3;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popupWindow消失
        popupWindow.setOutsideTouchable(true);

        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                popupWindow.dismiss();
            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeCamera(RESULT_CAMERA_IMAGE);
                popupWindow.dismiss();
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, NavigationBarUtil.getNavigationBarHeight(getActivity()));
    }

    private void takeCamera(int num) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            }
        }

        startActivityForResult(takePictureIntent, num);//跳转界面传回拍照所得数据
    }

    private File createImageFile() {
        File storageDir = new File("/storage/emulated/0/qidian/data/file/img/");
        if (!storageDir.exists())
            storageDir.mkdirs();
        File image = null;
        try {
            image = File.createTempFile(
                    generateFileName(),  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return imageFileName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mCurrentPhotoPath = cursor.getString(columnIndex);
                cursor.close();
            } else if (requestCode == RESULT_CAMERA_IMAGE) {

            }
            final Bitmap photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
            String photoName = mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("/") + 1);
            mCurrentPhotoPath = CompressUtils.compressImage(mCurrentPhotoPath, CommonUtil.getDiskCachePath(getActivity()) + File.separator + photoName, 30);
            switch (currentChoose) {
                case shenfenzheng_zheng:
                    showShiBieDialog();
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload1_iv);
                    helper.getIDCardInfo(photo, true, new OCRHelper.ResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            //Log.e("===身份证-正============", "=" + result);
                            idCardZheng = GsonHelper.getInstance().fromJson(result, IDCardZheng.class);
                           // Log.e("=========sss", "=" + idCardZheng);
                            dialog.dismiss();
                            uploadEtcPhotos(mCurrentPhotoPath,0);
                        }

                        @Override
                        public void onFailure() {
                            Log.e("===========", "=====说说说========");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "识别失败，请重新上传照片", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    break;
                case shenfenzheng_fan:
                    showShiBieDialog();
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload2_iv);
                    helper.getIDCardInfo(photo, false, new OCRHelper.ResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("===身份证-反============", "=" + result);
                            idCardFan = GsonHelper.getInstance().fromJson(result, IDCardFan.class);
                            dialog.dismiss();
                            uploadEtcPhotos(mCurrentPhotoPath,1);
                        }

                        @Override
                        public void onFailure() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "识别失败，请重新上传照片", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    break;
                case yinhangka_copy:
                    showShiBieDialog();
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload6_iv);
                    helper.getBankCardInfo(photo, true, new OCRHelper.ResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("===银行卡============", "=" + result);
                            bankCardInfo = GsonHelper.getInstance().fromJson(result, BankCardInfo.class);
                            dialog.dismiss();
                            uploadEtcPhotos(mCurrentPhotoPath,2);
                        }

                        @Override
                        public void onFailure() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "识别失败，请重新上传照片", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        }
    }

    private void getVcode() {
        String bank_phone = bank_phone_et.getText().toString().trim();
        if (TextUtils.isEmpty(bank_phone)) {
            ToastUtil.show(getActivity(), "请输入银行预留手机号");
            return;
        }
        WangZKRequestApi.getInstance().getCertificationCode(bank_phone);
        startTimer();
    }

    @Override
    public void onDestroyView() {
        if (timer != null)
            timer.cancel();
        super.onDestroyView();
    }

    private CountDownTimer timer;

    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    get_certification_code_tv.setClickable(false);
                    get_certification_code_tv.setText("" + millisUntilFinished / 1000L + "" + getString(R.string.login_tips_resend_code));
                }

                @Override
                public void onFinish() {
                    get_certification_code_tv.setClickable(true);
                    get_certification_code_tv.setText(getString(R.string.login_tips_send_code));
                }
            };
        }

        timer.start();
    }

    LoadingDialog dialog;

    private void showShiBieDialog() {
        if (dialog == null) {
            LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(getActivity())
                    .setMessage("识别中...")
                    .setCancelable(false)
                    .setCancelOutside(false);
            dialog = loadBuilder.create();
        }
        dialog.show();
    }

    private void uploadEtcPhotos(String photo, final int index) {
        File file = new File(photo);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody action = RequestBody.create(MediaType.parse("multipart/form-data"), "etcImage");
        WangZKRequestApi.getInstance().uploadEtcPhotos(action, part, new Callback<UploadFileResponse>() {
            @Override
            public void onResponse(Call<UploadFileResponse> call, Response<UploadFileResponse> response) {
                //上传成功
                Log.e("上传成功========", "====");
                UploadFileResponse body = response.body();
                if (body.getCode() == 0) {
                    imagesArray[index]=body.getData().getUrl();
                }
            }

            @Override
            public void onFailure(Call<UploadFileResponse> call, Throwable t) {
                //上传失败
                Log.e("上传失败========", "====" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void showDialog() {
        if (idCardZheng == null) {
            Toast.makeText(getActivity(), "请上传身份证正面", Toast.LENGTH_SHORT).show();
            return;
        }
        if (idCardFan == null) {
            Toast.makeText(getActivity(), "请上传身份证反面", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bankCardInfo == null) {
            Toast.makeText(getActivity(), "请上传银行卡", Toast.LENGTH_SHORT).show();
            return;
        }
//        String vcode = certification_code_et.getText().toString().trim();
        String bphone = bank_phone_et.getText().toString().trim();
        if (TextUtils.isEmpty(bphone)) {
            Toast.makeText(getActivity(), "请输入银行预留手机号", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(vcode)) {
//            Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
//            return;
//        }
        mDialog.setName(idCardZheng.getName());
        mDialog.setIdCardNumber(idCardZheng.getNum());
        mDialog.setIdCardAddress(idCardZheng.getAddress());

        String start = idCardFan.getStart_date();
        String end = idCardFan.getEnd_date();
        String date = start.substring(0, 4) + "." + start.substring(4, 6) + "." + start.substring(6) + "-"
                + end.substring(0, 4) + "." + end.substring(4, 6) + "." + end.substring(6);
        mDialog.setIdCardDate(date);

        mDialog.setBankCardName(bankCardInfo.getBank());
        mDialog.setBankCardNumber(bankCardInfo.getNumber());
        mDialog.setBankCardType(bankCardInfo.getType());
        mDialog.setBankCardPhone(bphone);
//        mDialog.setVcode(vcode);
        mDialog.setImageUrls(imagesArray[0]+"|"+imagesArray[1]+"|"+imagesArray[2]);
        mDialog.show();

        AllMsgActivity.idCardZheng = idCardZheng;
        AllMsgActivity.idCardFan = idCardFan;
        AllMsgActivity.bankCardInfo = bankCardInfo;
        AllMsgActivity.phone_number = bphone;
    }
}
