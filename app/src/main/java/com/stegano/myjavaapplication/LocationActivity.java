package com.stegano.myjavaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class LocationActivity extends AppCompatActivity {

    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Manifest.xml에 추가
//        <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
//        <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

        // 테드퍼미션으로 권한 요청
//        TedPermission.create()
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("필요한 서비스입니다.")
//                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//                .check();

        // 얀첸지퍼미션
        AndPermission.with(this)
                .runtime()
                .permission(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(getApplicationContext(), "허용된 권한 개수 : " + data.size(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(getApplicationContext(), "거부된 권한 개수 : " + data.size(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .start();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Log.d("Map", "구글지도 준비됨");
                map = googleMap;

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                map.setMyLocationEnabled(true);  // 내 위치 표시 활성화
            }
        });

        try {
            MapsInitializer.initialize(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });
    }

    // 권한 리스터 설정 (테드퍼미션)
//    PermissionListener permissionlistener = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() {  // 권한 승인하면 onPermissionGranted()가 호출됨
//            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onPermissionDenied(List<String> deniedPermissions) {  // 권한 거절 시 호출됨
//            Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            finish();
//        }
//    };

    // 위치 매니저 객체 참조 (1단계)
    public void startLocationService() {
        // LocationManager 객체 참조하기
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            // 권한이 없다면 return
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 latitude : " + latitude + ", longitude : " + longitude;
            }


            // 위치 정보 업데이트 요청 (3단계)
            GPSListener gpsListener = new GPSListener();  // 리스너 객체 생성
            long minTime = 10000;  // 1000 : 1초
            float minDistance = 0;  // 1 : 1미터
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);  // 위치 업데이트 요청
            Toast.makeText(getApplicationContext(), "내 위치 확인 요청함", Toast.LENGTH_SHORT).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // 위치 리스너 구현 (2단계)
    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String message = "최근 위치 latitude : " + latitude + ", longitude : " + longitude;

            showCurrentLocation(latitude, longitude);  // 내 위치
            showFriendsCurrentLocation(37.0, 127.0);  // 친구 위치는 DB에서 가져오는게 나을듯
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }

    // 지도에 현재 위치 보여주기
    private void showCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);

        // 받아온 위치를 지도 영역에 보여주기 (줌 사이즈는 보통 1~19 또는 21까지이며 getMaxZoomLevel로 확인 가능)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        showMyLocationMarker(curPoint);
    }

    // 지도에 현재 친구 위치 보여주기
    private void showFriendsCurrentLocation(Double latitude, Double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);

        showFriendsLocationMarker(curPoint);
    }

    // 지도에 마커찍기
    MarkerOptions myLocationMarker;
    private void showMyLocationMarker(LatLng curPoint) {
        if (myLocationMarker == null) {
            myLocationMarker = new MarkerOptions();  // 마커 객체 생성
            myLocationMarker.position(curPoint);
            myLocationMarker.title("내 위치\n");
            myLocationMarker.snippet("GPS로 확인한 위치");
            myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            map.addMarker(myLocationMarker);
        } else {
            myLocationMarker.position(curPoint);
        }
    }

    MarkerOptions myFriendsLocationMarker;
    private void showFriendsLocationMarker(LatLng curPoint) {
        if (myFriendsLocationMarker == null) {
            myFriendsLocationMarker = new MarkerOptions();  // 마커 객체 생성
            myFriendsLocationMarker.position(curPoint);
            myFriendsLocationMarker.title("친구 위치\n");
            myFriendsLocationMarker.snippet("GPS로 확인한 위치");
            myFriendsLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
            map.addMarker(myFriendsLocationMarker);
        } else {
            myFriendsLocationMarker.position(curPoint);
        }
    }


    // 액티비티 생명주기 ==============================================================================
    @Override
    protected void onResume() {
        super.onResume();

        if (map != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);  // 내 위치 표시 활성화
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (map != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(false);  // 내 위치 표시 비활성화
        }
    }
}