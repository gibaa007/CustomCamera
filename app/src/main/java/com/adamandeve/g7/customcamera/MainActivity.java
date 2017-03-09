package com.adamandeve.g7.customcamera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {

    boolean recording;
    TextView mTimer;
    private Camera camera;
    private CameraSurfaceView cameraSurfaceView;
    private MediaRecorder mediaRecorder;
    private File folder;
    private ImageView iv_rec, iv_swap;
    private CommonActions commonActions;
    private CountDownTimer countDownTimer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commonActions = new CommonActions(this);
        recording = false;

        setContentView(R.layout.activity_main);

        //Get Camera for preview
        camera = getCameraInstance();
        camera.setDisplayOrientation(90);
        if (camera == null) {
            commonActions.customAlertDialogFinish("Failed to open Camera", this);
        }
        cameraSurfaceView = new CameraSurfaceView(this, camera);
        FrameLayout surface = (FrameLayout) findViewById(R.id.surface);
        surface.addView(cameraSurfaceView);
        iv_rec = (ImageView) findViewById(R.id.iv_rec);
        iv_swap = (ImageView) findViewById(R.id.iv_swap);
        mTimer = (TextView) findViewById(R.id.timer);
        String state = Environment.getExternalStorageState();
        folder = null;
        if (state.contains(Environment.MEDIA_MOUNTED)) {
            folder = new File(Environment
                    .getExternalStorageDirectory() + "/VIPSnips");
        } else {
            folder = new File(Environment
                    .getExternalStorageDirectory() + "/VIPSnips");
        }

        if (!folder.exists()) {
            folder.mkdirs();
        }
        iv_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recording) {
                    // stop recording and release camera
                    countDownTimer.cancel();
                    mediaRecorder.stop();  // stop the recording
                    releaseMediaRecorder();
                    commonActions.showToast("Video Successfully Saved");
                    finish();// release the MediaRecorder object
                } else {
                    countDownTimer.start();
                    //Release Camera before MediaRecorder start
                    releaseCamera();
                    if (!prepareMediaRecorder()) {
                        commonActions.showFailureSnackToast(iv_rec,
                                "Fail in prepareMediaRecorder()!\n - Ended -");
                    }
                    mediaRecorder.start();
                    recording = true;
                }
            }
        });
        final int sec = 16000;
        countDownTimer = new CountDownTimer(sec, 1000) {

            public void onTick(long millisUntilFinished) {
                int minute = (int) (millisUntilFinished / 1000) / 60;
                int second = (int) (millisUntilFinished / 1000) % 60;
                mTimer.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
            }

            public void onFinish() {
                mediaRecorder.stop();  // stop the recording
                releaseMediaRecorder(); // release the MediaRecorder object
                commonActions.showToast("Video Successfully Saved");
                finish();
            }

        };
    }

    private Camera getCameraInstance() {
// TODO Auto-generated method stub
        Camera c = null;
        try {
            c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private boolean prepareMediaRecorder() {
        camera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
        camera.setDisplayOrientation(90);
        camera.unlock();
        mediaRecorder.setCamera(camera);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
//        mediaRecorder.setVideoSize(320, 240);
//        mediaRecorder.setVideoFrameRate(15);
        mediaRecorder.setOrientationHint(270);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
        mediaRecorder.setOutputFile(folder.getAbsolutePath()
                + File.separator + "video.mp4");
        mediaRecorder.setMaxDuration(15000); // Set max duration 60 sec.\
        mediaRecorder.setPreviewDisplay(cameraSurfaceView.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            releaseMediaRecorder();
            return false;
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            camera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraSurfaceView(Context context, Camera camera) {
            super(context);
            mCamera = camera;

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int weight,
                                   int height) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (mHolder.getSurface() == null) {
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                // ignore: tried to stop a non-existent preview
            }

            // make any resize, rotate or reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (Exception e) {
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // TODO Auto-generated method stub
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // TODO Auto-generated method stub

        }
    }
}