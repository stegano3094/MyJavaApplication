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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class GoogleMapActivity extends AppCompatActivity implements LocationListener {
    static final String TAG = "GoogleMapActivity";

    Button findLocationButton;

    LocationManager locationManager;  // 시스템에서 LocationManager를 받아올 객체
    GoogleMap map;  // 어디서든 사용하기 위해서 여기에 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        findLocationButton = findViewById(R.id.findLocationButton);
        findLocationButton.setOnClickListener( v -> {
            startLocationService();
            Toast.makeText(getApplicationContext(), "위치확인 요청중 입니다.", Toast.LENGTH_SHORT).show();
        });

        // 프래그먼트를 찾는 것은 프래그먼트 매니저를 이용해서 찾아야 한다.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // getMapAsync() 초기화해주는 코드
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                // 준비가 끝난 상태이고 이후부터 맵을 이용할 수 있음.
                map = googleMap;
            }
        });

        // 권한 리스터 설정 (테드퍼미션)
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {  // 권한 승인하면 onPermissionGranted()가 호출됨
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                startLocationService();  // 위치 서비스 시작
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {  // 권한 거절 시 호출됨
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        // 권한받기
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("필요한 서비스입니다.\n[Setting] > [Permission]에서 설정해주세요.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    public void startLocationService() {  // 위치 서비스 시작
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 30000;  // 1000 = 1초, 초마다 갱신
        float minDistance = 0;  // 1 = 1m, 미터마다 움직일 때 갱신 -> 리스너가 호출됨, (0 = 위치하고 상관없이 넘겨달라는 의미)

        // 권한이 있을 때
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 가장 마지막에 확인한 위치를 가져옴, (실내에서 호출해도 응답이 없음.)
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    this
            );
            // 실내에서는 Network 위치제공자에 의한 위치변화를 넣어주면 좋다. (Network 위치는 Gps에 비해 정확도가 많이 떨어짐)

        } else {  // 권한이 없을 때
        }
    }

    // 리스너 생성으로 위치값이 갱신되면 이 이벤트가 지속적으로 발생함 (변수로 위치 권한이 필요함)
    @Override
    public void onLocationChanged(@NonNull Location location) {
        // getLatitude() 경도, getLongitude() 위도, getAccuracy() 신뢰도 (GPS 수신은 오차범위가 좁다)
        String myLocationString = "내 위치 : " +
                location.getLatitude() + ", " +
                location.getLongitude() + ", " +
                location.getAccuracy();

        Toast.makeText(getApplicationContext(), myLocationString, Toast.LENGTH_SHORT).show();

        showCurrentLocation(location);
    }

    public void showCurrentLocation(Location location) {  //  받아온 위치에 구글맵을 이동시켜주는 메서드
        Log.d(TAG, "showCurrentLocation() 지도 이동");
        double latitude = location.getLatitude();
        double longtitude = location.getLongitude();

        // 화면의 위치와 지구상의 위치는 다르기 때문에 지구상의 좌표를 경위도로 표시할 수 있도록 만들어 놓은게 LatLng()이다.
        LatLng currentPoint = new LatLng(latitude, longtitude);

//        map.moveCamera(CameraUpdateFactory.newLatLng(currentPoint));
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPoint, 17));  // 줌에도 레벨이 있는데 숫자가 클수록 더 확대됨
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPoint, 18));  // animateCamera()는 애니메이션이 들어가면서 지도를 이동함
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);  // MAP_TYPE_HYBRID, MAP_TYPE_NORMAL 등이 있다.
        map.getUiSettings().setZoomControlsEnabled(true);

        showMyLocationMarker(currentPoint);
    }

    public void showMyLocationMarker(LatLng currentPoint) {  // 받아온 위치에 마커(핀)을 찍어주는 메서드
        Log.d(TAG, "showMyLocationMarker() maker 추가");

        MarkerOptions marker = new MarkerOptions();
        marker.position(currentPoint)
                .title("내 위치")
                .snippet("GPS로 확인한 위치")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));

        map.clear();  // 기존의 핀을 지움
        map.addMarker(marker);  // 새로 만든 marker를 맵에 표시함
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        if (locationManager != null) {
            // 사용하지 않을 때 좌표 수집을 멈춰야 함(안하면 백그라운드에서 계속 실행됨)
            locationManager.removeUpdates(this);  // 위치 서비스 중지
            Log.d(TAG, "onDestroy() removeUpdates()");
        }
    }
}