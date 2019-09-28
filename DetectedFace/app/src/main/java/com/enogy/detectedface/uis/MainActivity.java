package com.enogy.detectedface.uis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enogy.detectedface.R;
import com.enogy.detectedface.adapter.AdapterViewPager;
import com.enogy.detectedface.fragment.FragmentGuestManager;
import com.enogy.detectedface.fragment.FragmentHistory;
import com.enogy.detectedface.fragment.FragmentPendingScreen;
import com.enogy.detectedface.uis.activities.AddNewEmployeeActivity;
import com.enogy.detectedface.utils.Config;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private BroadCast broadCast;
    private ImageView imgMenu;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private FragmentPendingScreen fragmentPendingScreen;
    private FragmentHistory fragmentHistory;
    private FragmentGuestManager fragmentGuestManager;
    private Toolbar toolbar;
    private TextView txtTitleToolbar;

    private AdapterViewPager adapterViewPager;
    private TabLayout tabLayout;
    private NavigationView navigationView;
    private ImageView imgViewAddNew;
    private SharedPreferences sharedPreferences;

//    private Broa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        toolbar = findViewById(R.id.toolbarMain);
        txtTitleToolbar = findViewById(R.id.txtTitleToolbar);
        navigationView = findViewById(R.id.navigationView);
        imgMenu = findViewById(R.id.imgViewMenu);
        imgViewAddNew = findViewById(R.id.imgViewAddNewEmployee);

        sharedPreferences = getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);

        setupViewPager();
        setSupportActionBar(toolbar);

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imgViewAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNewEmployeeActivity.class));
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itemLogout: {
                        sharedPreferences.edit().putInt(Config.STATE_LOGIN, Config.STATE_LOGIN_FALSE).commit();
                        finish();
                    }
                    break;
                }
                return false;
            }
        });
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.ACTION)) {
                    Toast.makeText(context, "new employee", Toast.LENGTH_SHORT).show();
                }
            }
        };

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                txtTitleToolbar.setText(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        IntentFilter intentFilter = new IntentFilter(Config.ACTION);
//        registerReceiver(broadcastReceiver, intentFilter);
//        startService(new Intent(this, ServiceNotification.class));


    }


    private void setupViewPager() {
        fragmentPendingScreen = new FragmentPendingScreen();
        fragmentHistory = new FragmentHistory();
        fragmentGuestManager = new FragmentGuestManager();
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.addFragment(fragmentPendingScreen, "Pending screen");
        adapterViewPager.addFragment(fragmentHistory, "History");
        adapterViewPager.addFragment(fragmentGuestManager, "Guest Manager");
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabLayout.setTabTextColors(Color.parseColor("#000000"),
                Color.parseColor("#F19305"));
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    class BroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TAG", "Reciver ");
//            if (intent.getAction().equals(Config.ACTION)){
//                Log.e("TAG", "url " + intent.getStringExtra(Config.KEY));
//            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
