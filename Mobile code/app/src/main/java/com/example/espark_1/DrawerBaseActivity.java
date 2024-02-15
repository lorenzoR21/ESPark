package com.example.espark_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.espark_1.databinding.ActivityFavoriteParkingBinding;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById((R.id.activityContainer));
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle leftToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(leftToggle);
        leftToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(this, HomepageActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_star:
                startActivity(new Intent(this, Favorite_parkingActivity.class).putExtra("section", "favourite"));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_account:
                startActivity(new Intent(this, AccountActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(this, LoginActivity.class));
                overridePendingTransition(0, 0);
                break;
        }
        return false;
    }

    protected void allocateActivityTitle(String titleString)
    {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(titleString);
        }
    }
}