package com.app.zblol.changermoneyapps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.jar.Pack200;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static androidx.core.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScanPrice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScanPrice extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ScanPrice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScanPrice.
     */
    // TODO: Rename and change types and number of parameters
    public static ScanPrice newInstance(String param1, String param2) {
        ScanPrice fragment = new ScanPrice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch(requestCode){
                case RequestCameraPermission:{
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                        if(checkSelfPermission(getContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                        try{
                            cameraSource.start(cameraView.getHolder());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }



        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    int camViewX = 1280;
    int camViewY = 1240;

    final int RequestCameraPermission = 101;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View SCANER = inflater.inflate(R.layout.fragment_scan_price, container, false);

        cameraView = (SurfaceView) SCANER.findViewById(R.id.surfaceView);
        textView = (TextView) SCANER.findViewById(R.id.textView9);

        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
            if (!textRecognizer.isOperational()) {
                Log.i("ScanPrice","Detected dependes are not found");
            }

            cameraSource = new CameraSource.Builder(getContext(),textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(camViewX,camViewY)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions( new String[] {Manifest.permission.CAMERA},
                                    RequestCameraPermission);


                        }
                        cameraSource.start(cameraView.getHolder());

                    }catch (Exception e){
                            e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0 ) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++ ) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                textView.setText(stringBuilder.toString());
                            }
                        });
                    }

                }
            });


        return  SCANER;
    }
}