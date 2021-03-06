package fi.lbd.mobile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayDeque;
import java.util.Locale;

import fi.lbd.mobile.authorization.AuthorizedEventHandler;
import fi.lbd.mobile.events.BusHandler;
import fi.lbd.mobile.fragments.OpenSettingsFragment;
import fi.lbd.mobile.mapobjects.MapObjectSelectionManager;
import fi.lbd.mobile.mapobjects.events.SelectMapObjectEvent;
import fi.lbd.mobile.fragments.GoogleMapFragment;
import fi.lbd.mobile.fragments.MessageFragment;
import fi.lbd.mobile.fragments.ObjectListFragment;
import fi.lbd.mobile.location.LocationHandler;
import fi.lbd.mobile.mapobjects.MapObject;

/**
 *
 * The "main" activity of the app. Uses ViewPager to provide tabs for messages, object list, map, and
 * settings.
 *
 * Created by Ossi.
 */
public class ListActivity extends Activity {
    // Keeps loaded fragments in memory
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private final Activity activity = this;
    private LocationHandler locationHandler;

    // Declare constants for tab UI
    private static final int START_TAB = 1;
    private static final int MSG_TAB = 0;
    private static final int OBJ_TAB = 1;
    private static final int MAP_TAB = 2;
    private static final int SET_TAB = 3;
    private static final int TAB_COUNT = 4;

    // Data structure to save previous tabs in order to restore them when back button is pressed.
    private ArrayDeque<Integer> pageStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getSimpleName(), " onCreate()");
        setContentView(R.layout.activity_list);

        pageStack = new ArrayDeque<Integer>();
        pageStack.push(START_TAB);

        this.locationHandler = new LocationHandler(this);
        this.locationHandler.start();

        this.sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),
                this.locationHandler);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPager.setAdapter(this.sectionsPagerAdapter);
        this.viewPager.setCurrentItem(START_TAB);
        this.viewPager.setOffscreenPageLimit(4);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                // Hide soft keyboard when user switches tab
                final InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                View view = activity.getCurrentFocus();
                // If no view has focus, create a new one
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                pageStack.push(i);
            }
            @Override
            public void onPageScrolled(int i, float v, int j) {}
            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private LocationHandler locationHandler;

        public SectionsPagerAdapter(FragmentManager fm, LocationHandler locationHandler) {
            super(fm);
            this.locationHandler = locationHandler;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == MSG_TAB) {
                Log.d(this.getClass().getSimpleName(), "getItem: "+ position + " locationHandler: "+ this.locationHandler);
                MessageFragment frag = MessageFragment.newInstance();
                return frag;
            } else if (position == OBJ_TAB) {
                Log.d(this.getClass().getSimpleName(), "getItem: "+ position + " locationHandler: "+ this.locationHandler);
                ObjectListFragment frag = ObjectListFragment.newInstance(this.locationHandler);
                return frag;
            } else if (position == MAP_TAB){
                Log.d(this.getClass().getSimpleName(), "getItem: "+ position + " locationHandler: "+ this.locationHandler);
                GoogleMapFragment frag = GoogleMapFragment.newInstance(this.locationHandler);
                return frag;
            } else if(position == SET_TAB){
                Log.d(this.getClass().getSimpleName(), "getItem: "+ position + " locationHandler: "+ this.locationHandler);
                OpenSettingsFragment frag = OpenSettingsFragment.newInstance();
                return frag;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case MSG_TAB:
                    return getString(R.string.title_tab_messages).toUpperCase(l);
                case OBJ_TAB:
                    return getString(R.string.title_tab_objects).toUpperCase(l);
                case MAP_TAB:
                    return getString(R.string.title_tab_map).toUpperCase(l);
                case SET_TAB:
                    return getString(R.string.title_tab_settings).toUpperCase(l);
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHandler.start();

        BusHandler.getBus().register(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        ServiceManager.startMessageService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHandler.stop();
        BusHandler.getBus().unregister(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onStart(){
        super.onStart();
        ActiveActivitiesTracker.activityStarted(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        ActiveActivitiesTracker.activityStopped(this);
    }

    public void onDetailsClick(View view){
        // Retrieve object for which the Details-button was pressed
        MapObject object = null;
        try {
            object = (MapObject)(((View)(view.getParent().getParent())).getTag());
        } catch (Exception e){
            e.printStackTrace();
            Log.d(getClass().toString(), " No object tag received on details click.");
        }
        if (object != null) {
            MapObjectSelectionManager.get().setSelectedMapObject(object);
            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);
        }
        else {
            Log.d(getClass().toString(), " Tagged object was null on details click.");
        }
    }
    public void onMapClick(View view){
        // Retrieve object for which the Map-button was pressed
        MapObject object = null;
        try {
            object = (MapObject)((View)(view.getParent().getParent())).getTag();
        } catch (Exception e){
            e.printStackTrace();
            Log.d(getClass().toString(), " No object tag received on map click.");
        }
        if (object != null){
            MapObjectSelectionManager.get().setSelectedMapObject(object);
            BusHandler.getBus().post(new SelectMapObjectEvent());
            viewPager.setCurrentItem(MAP_TAB);
        }
        else {
            Log.d(getClass().toString(), " Tagged object was null on map click.");
        }
    }

    /**
     * Handle back button presses.
     * Pressing the back button brings user to the previous tab.
     *
     */
    @Override
    public void onBackPressed() {
        if(pageStack.size() > 1) {
            pageStack.pop();
            viewPager.setCurrentItem(pageStack.peek(), true);
        } else {
            pageStack.clear();
            super.onBackPressed(); // This will pop the Activity from the stack.
        }
    }

    public LocationHandler getLocationHandler(){
        return this.locationHandler;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        AuthorizedEventHandler.processResults(requestCode, resultCode, data);
    }
}
