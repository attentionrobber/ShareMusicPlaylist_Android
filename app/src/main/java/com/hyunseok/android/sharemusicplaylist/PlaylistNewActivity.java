package com.hyunseok.android.sharemusicplaylist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 *  PlaylistDetail Activity 에 비교하여 더 있어야 될것들
 *  대표이미지 설정 버튼, 음악 추가 버튼, TAG 입력부분(?)
 */

@EActivity(R.layout.activity_playlist_new)
public class PlaylistNewActivity extends AppCompatActivity {

    private final int REQ_CAMERA = 101; // 카메라 요청 코드
    private final int REQ_GALLERY = 102; // 갤러리 요청 코드

    @ViewById
    ImageView imageView_playlistNew;

    private Uri imageUri;

    @AfterViews
    protected void init() {

    }

    @Click({R.id.imageView_playlistNew})
    public void setImage() {

        AlertDialog.Builder alert_Imgbtn = new AlertDialog.Builder(PlaylistNewActivity.this);
        alert_Imgbtn.setTitle("Input Image");
        final CharSequence[] items_Imgbtn = {"Camera", "Gallery"};
        alert_Imgbtn.setItems(items_Imgbtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0 : // Camera
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 카메라 촬영 후 미디어 컨텐트 Uri를 생성해서 외부저장소에 저장한다.
                        // 마시멜로 이상 버전은 아래 코드를 반영해야함.
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            ContentValues values = new ContentValues(1);
                            values.put(MediaStore.Images.Media.MIME_TYPE, "ShareMusic/jpg"); // 촬영한 사진을 저장한다.
                            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                            // 컨텐트 Uri강제 세팅
                        }
                        startActivityForResult(intent, REQ_CAMERA);
                        break;
                    case 1 : // Gallery
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*"); // 외부저장소에 있는 이미지만 가져오기위한 필터링.
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_GALLERY); // createChooser로 타이틀을 붙여줄 수 있다.
                        break;
                }
            }
        });
        alert_Imgbtn.show(); // 4. show함수로 팝업창을 띄운다.
    }

    //startActivityForResult() 후에 실행되는 콜백 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CAMERA:
                // 마시멜로버전 이상인 경우에만 getData()에 null이 넘어올것임.
                if (resultCode == RESULT_OK) { // resultCode OK이면 완료되었다는 뜻.
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        if (data != null && data.getData() != null) {
                            imageUri = data.getData();
                        }
                    }
                    if (imageUri != null) {
                        Glide.with(this).load(imageUri).into(imageView_playlistNew);
                    } else {

                    }
                } else { // reulstCode가 uri가 남아있는데 삭제처리해야함.

                }
                break;
            case REQ_GALLERY:
                //if(data != null && data.getData() != null) {
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                    Glide.with(this).load(imageUri).into(imageView_playlistNew);
                } else {

                }
                break;
        }
    }
}
