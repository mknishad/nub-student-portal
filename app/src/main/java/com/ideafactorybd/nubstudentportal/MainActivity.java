package com.ideafactorybd.nubstudentportal;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  StudentPortalFragment studentPortalFragment;
  NUBWebsiteFragment nubWebsiteFragment;
  HelpFragment helpFragment;
  AboutFragment aboutFragment;
  private int fragmentNumber = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    // Load about fragment on startup
    StudentPortalFragment studentPortalFragment = new StudentPortalFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.main_content, studentPortalFragment, studentPortalFragment.getTag()).commit();
    setTitle("Student Portal");

    Snackbar snackbar = Snackbar.make(
        findViewById(R.id.main_content),
        "Please go to Help section to know about login info",
        Snackbar.LENGTH_INDEFINITE
    ).setAction("OK", v -> {});
    snackbar.setActionTextColor(Color.WHITE);
    snackbar.show();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      switch (fragmentNumber) {
        case 1:
          if (StudentPortalFragment.canGoBack()) {
            StudentPortalFragment.goBack();
          } else {
            super.onBackPressed();
          }
          break;
        case 2:
          if (NUBWebsiteFragment.canGoBack()) {
            NUBWebsiteFragment.goBack();
          } else {
            super.onBackPressed();
          }
          break;
        default:
          super.onBackPressed();
      }
    }
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_student_portal) {
      fragmentNumber = 1;
      studentPortalFragment = new StudentPortalFragment();
      getSupportFragmentManager().beginTransaction().replace(R.id.main_content, studentPortalFragment,
          studentPortalFragment.getTag()).commit();
      setTitle("Student Portal");
      Toast.makeText(this, "Please go to Help section to know about login info", Toast.LENGTH_LONG).show();
    } else if (id == R.id.nav_nub_website) {
      fragmentNumber = 2;
      nubWebsiteFragment = new NUBWebsiteFragment();
      getSupportFragmentManager().beginTransaction().replace(R.id.main_content, nubWebsiteFragment,
          nubWebsiteFragment.getTag()).commit();
      setTitle("NUB Website");
    } else if (id == R.id.nav_help) {
      fragmentNumber = 3;
      helpFragment = new HelpFragment();
      getSupportFragmentManager().beginTransaction().replace(R.id.main_content, helpFragment,
          helpFragment.getTag()).commit();
      setTitle("Help");
    } else if (id == R.id.nav_about) {
      fragmentNumber = 4;
      aboutFragment = new AboutFragment();
      getSupportFragmentManager().beginTransaction().replace(R.id.main_content, aboutFragment,
          aboutFragment.getTag()).commit();
      setTitle("About");
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
