package com.uautogo.qidian.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uautogo.qidian.R;
import com.uautogo.qidian.ServiceApi.WangZKRequestApi;
import com.uautogo.qidian.activity.AllMsgActivity;
import com.uautogo.qidian.data.GsonHelper;
import com.uautogo.qidian.model.DriveCardInfo;
import com.uautogo.qidian.model.UploadFileResponse;
import com.uautogo.qidian.utils.CommonUtil;
import com.uautogo.qidian.utils.CompressUtils;
import com.uautogo.qidian.utils.NavigationBarUtil;
import com.uautogo.qidian.utils.OCRHelper;
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
 * 车辆信息
 */

public class CarMsgFragment extends Fragment implements View.OnClickListener {
    RelativeLayout xingshizheng_zheng_rl, xingshizheng_copy_rl, hezhao_rl;
    ImageView upload3_iv, upload4_iv, upload7_iv;
    DriveCardInfo driveCardInfo;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_CAMERA_IMAGE = 2;
    private String[] imagesArray = new String[3];

    private static final int xingshizheng_zheng = 4;
    private static final int xingshizheng_copy = 5;
    private static final int hezhao = 6;

    String mCurrentPhotoPath;
    private int currentChoose = 0;
    OCRHelper helper = new OCRHelper();
    OCRDialog mDialog;
//    String car_number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_msg, container, false);
        mDialog = new OCRDialog(getActivity(), 2,getActivity().getApplication());
        initView(view);

        return view;
    }
    private void initView(View view) {
        xingshizheng_zheng_rl = (RelativeLayout) view.findViewById(R.id.xingshizheng_zheng_rl);
        xingshizheng_copy_rl = (RelativeLayout) view.findViewById(R.id.xingshizheng_copy_rl);
        hezhao_rl = (RelativeLayout) view.findViewById(R.id.hezhao_rl);
        upload3_iv = (ImageView) view.findViewById(R.id.upload3_iv);
        upload4_iv = (ImageView) view.findViewById(R.id.upload4_iv);
        upload7_iv = (ImageView) view.findViewById(R.id.upload7_iv);
        xingshizheng_zheng_rl.setOnClickListener(this);
        xingshizheng_copy_rl.setOnClickListener(this);
        hezhao_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xingshizheng_zheng_rl:
                currentChoose = xingshizheng_zheng;
                showPopupWindow();
                break;
            case R.id.xingshizheng_copy_rl:
                currentChoose = xingshizheng_copy;
                showPopupWindow();
                break;
            case R.id.hezhao_rl:
                currentChoose = hezhao;
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
        int height = getResources().getDisplayMetrics().heightPixels / 3;

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
                case xingshizheng_zheng:
                    showShiBieDialog();
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload3_iv);
                    helper.getDriveCardInfo(photo, true, new OCRHelper.ResultCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("===行驶证============", "=" + result);
                            driveCardInfo = GsonHelper.getInstance().fromJson(result, DriveCardInfo.class);
                            dialog.dismiss();
                            uploadEtcPhotos(mCurrentPhotoPath, 0);
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
                case xingshizheng_copy:
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload4_iv);
                    uploadEtcPhotos(mCurrentPhotoPath, 1);
                    break;
                case hezhao:
//                    showShiBieDialog();
                    Glide.with(getActivity()).load(mCurrentPhotoPath).into(upload7_iv);
                    uploadEtcPhotos(mCurrentPhotoPath, 2);
//                    new OCRHelper().getCarNumber(photo, new OCRHelper.ResultCallback() {
//                        @Override
//                        public void onSuccess(String result) {
//                            try {
//                                JSONObject object = new JSONObject(result);
//                                String number = object.optString("number");
//                                Log.e("识别结果=========", "车牌号:" + number);
//                                dialog.dismiss();
//                                if (TextUtils.isEmpty(number)) {
//                                    onFailure();
//                                    return;
//                                }
//                                car_number = number;
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure() {
//                            Toast.makeText(getActivity(), "识别失败,请重新上传图片", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    break;
            }
        }
    }

    public static String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return imageFileName;
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
                    imagesArray[index] = body.getData().getUrl();
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
        if (driveCardInfo == null) {
            Toast.makeText(getActivity(), "请上传行驶证正面", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(car_number)){
//            Toast.makeText(getActivity(), "请上传人车合影", Toast.LENGTH_SHORT).show();
//        }
//        if (car_number.equals(driveCardInfo.getPlate_num())){
//            Toast.makeText(getActivity(), "驾驶证和车牌号不匹配", Toast.LENGTH_SHORT).show();
//        }
        mDialog.setCarCardNumber(driveCardInfo.getPlate_num());
        mDialog.setCarDistinguishCode(driveCardInfo.getVin());
        mDialog.setCarEngineNumber(driveCardInfo.getEngine_num());
        mDialog.setCarTravelCreateTime(driveCardInfo.getRegister_date());
        mDialog.setImageUrls(imagesArray[0] + "|" + imagesArray[1] + "|" + imagesArray[2]);
        mDialog.setCarCardAdd(driveCardInfo.getAddr());
        mDialog.setCarType(driveCardInfo.getVehicle_type());
        mDialog.setOwner(driveCardInfo.getOwner());
        mDialog.setUseChara(driveCardInfo.getUse_character());
        mDialog.setModel(driveCardInfo.getModel());
        mDialog.setDate(driveCardInfo.getRegister_date());
        mDialog.show();
        AllMsgActivity.driveCardInfo = driveCardInfo;
    }
}
