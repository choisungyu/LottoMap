package com.csg.lottomap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1000;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 권한체크 승인 안된것
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 사용자가 권한 거부를 한 번 이상 요청이 있으면
                // sees the explanation,

                // 퍼미션 요청
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                ,Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
            } else {
                // 퍼미션 요청
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                ,Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // 권한 체크를 하고 켜줘야 함
//        mMap.setMyLocationEnabled(true);

        // 승인
        // 초기화 위치 위경도
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10.0f));


    }

    // 결과 코드 ( 권한이 수락을 하든 말든 호출 되는것 )
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.

                // 허락 된 것
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 허락 됏으면 켜주기
                   mMap.setMyLocationEnabled(true);


                   // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied,
                   // 거부했을때 , 재요청하는 다이얼로그 띄우기 try again to request the permission.
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setTitle("권한")
                           .setMessage("설정에서 언제든지 설정할 수 있음")
                           .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // Send the positive button event back to the host activity
                               }
                           })
                           .setNegativeButton("닫기", null)
                           .show();

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
