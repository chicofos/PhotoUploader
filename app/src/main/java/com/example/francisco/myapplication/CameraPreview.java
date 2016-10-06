package com.example.francisco.myapplication;

/**
 * Created by Francisco on 8/27/2016.
 */
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Display _display;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // create the surface and start camera preview
            if (mCamera == null) {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera, Display display) {
        _display = display;

        if(mCamera == null)
            mCamera = camera;

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera);
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        try{
        Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

            Camera.Size previewSize = previewSizes.get(0);


        if(_display.getRotation() == Surface.ROTATION_0)
        {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera.setDisplayOrientation(90);
        }
        if(_display.getRotation() == Surface.ROTATION_90)
        {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
        }

        if(_display.getRotation() == Surface.ROTATION_180)
        {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
        }

        if(_display.getRotation() == Surface.ROTATION_270)
        {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            mCamera.setDisplayOrientation(180);
        }

        mCamera.setParameters(parameters);

            refreshCamera(mCamera,_display);
        }
        catch (Exception ex) {
            String err = ex.getMessage();
        }

    }

    public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // mCamera.release();

    }
}