package com.wrabbit.notesclone;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wrabbit.notesclone.adapters.StaggeredRecyclerviewAdapter;
import com.wrabbit.notesclone.drawview.DrawActivity;

import java.util.ArrayList;
import java.util.Locale;

public class NotesDashboardActivity extends AppCompatActivity {
    private static final String TAG = "NotesDashboardActivity";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    private FloatingActionButton fab_add;
    private RecyclerView recyclerView;

    StaggeredRecyclerviewAdapter staggeredRecyclerViewAdapter;

    private CollapsingToolbarLayout collapsingToolbar;

    private static final int REQUEST_CODE_DRAWING = 1;
    private final int REQ_CODE = 100;

    private ImageView profile;

    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_dashboard);
        initWidgets();
        addDrawerItems();
        setupDrawer();
        initImageBitmaps();
        setUpBottomAppBar();
        setUpAppBar();

    }

    private void initWidgets() {
        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesDashboardActivity.this, LoginActivity.class));
            }
        });
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesDashboardActivity.this,
                        NotesViewEditActivity.class);
                startActivity(intent);
            }
        });
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            //            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
            }
        });
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mNames.add("Havasu Falls\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj");

        mNames.add("Trondheim");

        mNames.add("Portugal\naashdgaishdoahsidasihdakjsbk\nagadsugasiugda\nasdguagkcjabjvscjj");

        mNames.add("Rocky Mountain National Park");
        mNames.add("Mahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj");
        mNames.add("Frozen Lake\nagadsugasiugda\nasdguagkcjabjvscjj");

        mNames.add("White Mahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjSands Desert");
        mNames.add("Austrailia");

        mNames.add("WashingtonMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjjMahahual\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj\nagadsugasiugda\nasdguagkcjabjvscjj");

        initRecyclerView();

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        staggeredRecyclerViewAdapter =
                new StaggeredRecyclerviewAdapter(this, mNames);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom_appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * set up Bottom Bar
     */
    BottomAppBar bottomAppBar;

    private void setUpBottomAppBar() {
        bottomAppBar = findViewById(R.id.bar);
        //set bottom bar to Action bar as it is similar like Toolbar
        setSupportActionBar(bottomAppBar);
        //click event over Bottom bar menu item
        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.app_bar_todo) {
                    Toast.makeText(NotesDashboardActivity.this, "TODO", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.app_bar_record) {
                    Toast.makeText(NotesDashboardActivity.this, "Record", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotesDashboardActivity.this, RecordingActivity.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.app_bar_image) {
                    Toast.makeText(NotesDashboardActivity.this, "Capture", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.app_bar_draw) {
                    Toast.makeText(NotesDashboardActivity.this, "Draw", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotesDashboardActivity.this, DrawActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_DRAWING);
                }
                return false;
            }
        });

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open bottom sheet
                startActivity(new Intent(NotesDashboardActivity.this, TakePicture.class));
            }
        });
    }

    ImageView nav_menu;

    private void setUpAppBar() {
        nav_menu = findViewById(R.id.nav_menu);
//        checkPermission();

        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nav menu

            }
        });
    }


    private void addDrawerItems() {
        String[] menuItem = {"Profile", "Setting", "Contact us"};
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menuItem);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mDrawerLayout.closeDrawers();
                if (position == 0) {
//                            Intent intent = new Intent(getApplicationContext(),
//                                    .class);
//                            startActivity(intent);

                } else if (position == 1) {
//                            Intent intent = new Intent(getApplicationContext(),
//                                    .class);
//                            startActivity(intent);
                } else if (position == 2) {
//                            Intent intent = new Intent(ctx,
//                                    .class);
//                            startActivity(intent);
                } /*else if (position == 3) {
//                            Intent intent = new Intent(ctx,
//                                    .class);
//                            startActivity(intent);
                        }*/
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Notes Clone");
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("Notes Clone");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                        Uri.parse("package:" + getPackageName()));
//                startActivity(intent);
//                finish();
//            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_DRAWING: {
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = data.getStringExtra(DrawActivity.KEY_DATA);
                    if (filePath != null) {
                        Toast.makeText(NotesDashboardActivity.this, "done", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Opp.. there is no image !!!", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mNames.add(result.get(0).toString());
                    staggeredRecyclerViewAdapter.notifyDataSetChanged();
                }
                break;
            }
        }
    }
}