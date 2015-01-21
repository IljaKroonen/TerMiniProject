package com.m2dl.nater.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.m2dl.nater.R;
import com.m2dl.nater.utils.PreviewCamera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends Activity{

    private static final String TAG = "CallCamera";

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    int cameraId = -1;

    private Camera mCamera;
    private PreviewCamera mPreview;
    private Button buttonCapture;
    private boolean isCaptured;
    private Button buttonFlash;
    private Button buttonSwitch;
    private Button buttonSelection;
    private Button buttonComment;
    private FrameLayout preview;
    private FrameLayout informationsLayout;
    private Context context;

    private EditText comment;
    private ImageView selection;
    private boolean isCommented;
    private boolean isSelected;

    private boolean commentSelected;
    private boolean selectionSelected;
    private boolean isLighOn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        context = this;
        isCaptured = false;
        isCommented = false;
        isSelected = false;
        commentSelected = false;
        selectionSelected = false;
//        mCamera = getCameraInstance();
//
//        initView();
//        setListener();
    }

    @Override
    public void onResume() {
        mCamera = getCameraInstance();
        initView();
        setListener();
        super.onResume();
    }

    @Override
    public void onPause() {
        mCamera.stopPreview();
        mCamera.setPreviewCallback(null);
        isCaptured = false;
        showElements();
        super.onPause();
    }

    private void initView() {
        buttonCapture = (Button)findViewById(R.id.button_capture);
        buttonFlash = (Button)findViewById(R.id.button_flash);
        buttonSwitch = (Button)findViewById(R.id.button_rotate);
        buttonSelection = (Button)findViewById(R.id.button_select);
        buttonComment = (Button)findViewById(R.id.button_comment);

        mPreview = new PreviewCamera(this, mCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        informationsLayout = (FrameLayout) findViewById(R.id.informations);
        preview.addView(mPreview);
    }

    public void capture(View v){
        isCaptured = true;
        hideElements();
        mCamera.takePicture(null, null, mPicture);
    }


    private void setListener() {
        informationsLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   if (!isCommented && commentSelected) {
                       generateCommentaire(params, event);
                   }
                    else if (!isSelected && selectionSelected){
                        generateSelection(params,event);
                   }
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if (isCommented && commentSelected) {
                        moveCommentaire(params, event.getX(), event.getY());
                    }
                    else if (isSelected && selectionSelected) {
                        moveSelection(params, event.getX(), event.getY());
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                }
                return true;
            }
        });
    }

    private void generateCommentaire(FrameLayout.LayoutParams params, MotionEvent event) {
        isCommented = true;
        comment = new EditText(context);
        comment.setBackgroundColor(Color.TRANSPARENT);
        comment.setTextColor(Color.WHITE);
        comment.setTextSize(20);

        moveCommentaire(params, event.getX(), event.getY());

        informationsLayout.addView(comment);


        comment.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(comment, InputMethodManager.SHOW_IMPLICIT);
    }

    private void generateSelection(FrameLayout.LayoutParams params, MotionEvent event) {
        isSelected = true;
        selection = new ImageView(context);
        selection.setImageResource(R.drawable.selection);
        moveSelection(params, event.getX(), event.getY());

        informationsLayout.addView(selection);
    }




    private void moveCommentaire(FrameLayout.LayoutParams params, float x, float y) {
        params.leftMargin = (int) x - comment.getHeight();
        params.topMargin = (int) y - (comment.getWidth() / 2);
        comment.setLayoutParams(params);
        comment.invalidate();
    }

    private void moveSelection(FrameLayout.LayoutParams params, float x, float y) {
        params.leftMargin = (int) x - selection.getHeight();
        params.topMargin = (int) y - (selection.getWidth() / 2);
        selection.setLayoutParams(params);
        selection.invalidate();
    }

    private void showElements() {
        buttonCapture.setVisibility(View.VISIBLE);
        buttonFlash.setVisibility(View.VISIBLE);
        buttonSwitch.setVisibility(View.VISIBLE);
        buttonSelection.setVisibility(View.INVISIBLE);
        buttonComment.setVisibility(View.INVISIBLE);
        informationsLayout.removeAllViews();
        informationsLayout.setVisibility(View.GONE);
        isCommented = false;
        isSelected = false;
    }

    private void hideElements() {
        buttonCapture.setVisibility(View.GONE);
        buttonFlash.setVisibility(View.GONE);
        buttonSwitch.setVisibility(View.GONE);
        buttonSelection.setVisibility(View.VISIBLE);
        buttonComment.setVisibility(View.VISIBLE);
        informationsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (isCaptured) {
            showElements();
            isCaptured = false;
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.removeAllViews();
            preview.addView(mPreview);
        }
        else {
            super.onBackPressed();
        }
    }

    public void select(View v) {
        if (selectionSelected) {
            selectionSelected = false;
            buttonSelection.setBackgroundResource(R.drawable.select_button);
            return;
        }
        selectionSelected = true;
        commentSelected = false;
        buttonComment.setBackgroundResource(R.drawable.comment_button);
        buttonSelection.setBackgroundResource(R.drawable.select_button_press);
    }


    public void comment(View v) {
        if (commentSelected) {
            commentSelected = false;
            buttonComment.setBackgroundResource(R.drawable.comment_button);
            return;
        }
        selectionSelected = false;
        commentSelected = true;
        buttonComment.setBackgroundResource(R.drawable.comment_button_press);
        buttonSelection.setBackgroundResource(R.drawable.select_button);
    }

    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    public void flash(View v){

        Camera.Parameters p = mCamera.getParameters();
        if (isLighOn) {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(p);
            mCamera.startPreview();
            isLighOn = false;

        } else {
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(p);
            mCamera.startPreview();
            isLighOn = true;

        }
    }

    private int findFrontFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
        }
        return c;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: " );
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

}