package com.dusdhfkd.nightprotecterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int LOCATION_PERMISSION_CODE = 1;
    private GpsTracker gpsTracker;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button imageButton = (Button) findViewById(R.id.buttonsos);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                Toast.makeText( MainActivity.this, "SOS 기능이 실행됩니다.", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 119));
                startActivity(tt);
            }
        });
        Button imageButton1 = (Button) findViewById(R.id.buttonhome);
        imageButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "귀가 모니터링 기능이 실행됩니다.", Toast.LENGTH_SHORT).show();
                Intent openKAKAOMAP_Server = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com"));
                startActivity(openKAKAOMAP_Server);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                        Toast.makeText(MainActivity.this, "홈 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.timer:
                    {
                        Toast.makeText(MainActivity.this, "배탈 타이머 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.police:
                    {
                        Toast.makeText(MainActivity.this, "주변 기관 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Police.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.setting: {
                        Toast.makeText(MainActivity.this, "기능 구현중입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
                return false;
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                Toast.makeText( MainActivity.this, "SOS 기능이 실행됩니다.", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 119));
                startActivity(tt);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("684652468468", null, "현재위치 \n위도 " + latitude + "\n경도 " + longitude + "\n현재 발신자가 위험한 상황입니다.", null, null);
                Toast.makeText(MainActivity.this, "현재 위치가 119에 공유되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}