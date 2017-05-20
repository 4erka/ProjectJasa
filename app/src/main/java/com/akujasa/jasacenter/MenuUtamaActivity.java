package com.akujasa.jasacenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.akujasa.jasacenter.R;
import com.akujasa.jasacenter.CariJasaFragment;
import com.akujasa.jasacenter.MoviesFragment;
import com.akujasa.jasacenter.NotificationsFragment;
import com.akujasa.jasacenter.PhotosFragment;
import com.akujasa.jasacenter.SettingsFragment;
import com.akujasa.jasacenter.CircleTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MenuUtamaActivity extends AppCompatActivity {

    private String TAG = MenuUtamaActivity.class.getSimpleName();

    private ProgressDialog progressDialog;
    // JSON data url
    private static String Jsonurl = "http://192.168.0.187/admin-jasa/android_get_katalog.php";

    ArrayList<HashMap<String, String>> KatalogJsonList;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    //private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String urlProfileImg = "https://i.ytimg.com/vi/NbWqUAAkav0/maxresdefault.jpg";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_CARIJASA = "carijasa";
    private static final String TAG_STATUSPESANAN = "statuspesanan";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_CARIJASA;
    private static final String TAG_PROFIL = "profil";
    private static final String TAG_KATALOGJASA = "katalogjasa";
    private static final String TAG_PESANAN = "pesanan";

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    String idToko;
    String idKonsumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        idKonsumen = getIntent().getExtras().getString("pencari_id");
        Log.e(TAG, "idKonsumen: " + idKonsumen);
        idToko = getIntent().getExtras().getString("idToko");
        Log.e(TAG, "idToko: " + idToko);

        // load nav menu header data
        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_CARIJASA;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Rilo Kukuh");
        txtWebsite.setText("www.jasa.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // cari jasa
                CariJasaFragment cariJasaFragment = new CariJasaFragment();
                return cariJasaFragment;
            case 1:
                // status pesanan konsumen
                StatusPesananFragment statusPesananFragment = new StatusPesananFragment();
                return statusPesananFragment;
            case 2:
                // Status pesanan penyedia
                Bundle bundle = new Bundle();
                bundle.putString("idToko", idToko);
                StatusPesananTokoFragment statusPesananTokoFragment = new StatusPesananTokoFragment();
                statusPesananTokoFragment.setArguments(bundle);
                return statusPesananTokoFragment;
            case 3:
                // settings fragment
                //KatalogJasaFragment katalogJasaFragment = new KatalogJasaFragment();
                //KatalogJasaFragment katalogJasaFragment;
                //return katalogJasaFragment;
            case 4:
                // settings fragment
                ProfilFragment profilFragment = new ProfilFragment();
                return profilFragment;
            default:
                return new CariJasaFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_carijasa:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_CARIJASA;
                        break;
                    case R.id.nav_statuspesanan:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_STATUSPESANAN;
                        break;
                    case R.id.nav_pesanan:
                        if(idToko != null){
                            navItemIndex = 2;
                            CURRENT_TAG = TAG_PESANAN;
                            break;
                        }
                        else{
                            Intent register = new Intent(MenuUtamaActivity.this, RegisterTokoActivity.class);
                            register.putExtra("idKonsumen", idKonsumen);
                            startActivity(register);
                            //startActivity(new Intent(MenuUtamaActivity.this, KatalogJasaActivity.class));
                            drawer.closeDrawers();
                            return true;
                        }
                    case R.id.nav_katalog_jasa:
                        //navItemIndex = 3;
                        //CURRENT_TAG = TAG_KATALOGJASA;
                        //break;
                        if(idToko != null){
                            Intent katalog = new Intent(MenuUtamaActivity.this, KatalogJasaActivity.class);
                            katalog.putExtra("idToko", idToko);
                            startActivity(katalog);
                            //startActivity(new Intent(MenuUtamaActivity.this, KatalogJasaActivity.class));
                            drawer.closeDrawers();
                        }
                        else{
                            Intent register = new Intent(MenuUtamaActivity.this, RegisterTokoActivity.class);
                            register.putExtra("idKonsumen", idKonsumen);
                            startActivity(register);
                            //startActivity(new Intent(MenuUtamaActivity.this, KatalogJasaActivity.class));
                            drawer.closeDrawers();
                        }
                        return true;
                    case R.id.nav_profil:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MenuUtamaActivity.this, ProfilActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_keluar:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MenuUtamaActivity.this, LoginActivity.class));
                        drawer.closeDrawers();
                        return true;
                    /*case R.id.nav_profil:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PROFIL;
                        break;*/
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_CARIJASA;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        //if (navItemIndex == 3) {
        //    getMenuInflater().inflate(R.menu.notifications, menu);
        //}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
