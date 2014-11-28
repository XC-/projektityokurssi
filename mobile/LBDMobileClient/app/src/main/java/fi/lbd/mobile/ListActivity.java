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

import fi.lbd.mobile.events.BusHandler;
import fi.lbd.mobile.events.SelectMapObjectEvent;
import fi.lbd.mobile.fragments.GoogleMapFragment;
import fi.lbd.mobile.fragments.MessageFragment;
import fi.lbd.mobile.fragments.ObjectListFragment;
import fi.lbd.mobile.location.LocationHandler;
import fi.lbd.mobile.mapobjects.MapObject;
import fi.lbd.mobile.backendhandler.BackendHandlerService;


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
    private static final int TAB_COUNT = 3;

    // Data structure to save previous tabs in order to restore them when back button is pressed.
    private ArrayDeque<Integer> pageStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusHandler.getBus().register(this);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_list);

        // Start the object repository service. // TODO: Missä käynnistys?
        startService(new Intent(this, BackendHandlerService.class));

        pageStack = new ArrayDeque<Integer>();
        pageStack.push(START_TAB);

        this.locationHandler = new LocationHandler(this);
        this.locationHandler.start();

        this.sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(),
                this.locationHandler);
        this.viewPager = (ViewPager) findViewById(R.id.pager);
        this.viewPager.setAdapter(this.sectionsPagerAdapter);
        viewPager.setCurrentItem(START_TAB);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                // Hide soft keyboard when user switches tab
                final InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                View view = activity.getCurrentFocus();
                // If no view has focus, create a new one
                if(view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // http://stackoverflow.com/questions/13185476/how-to-handle-back-button-using-view-pager
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                MessageFragment frag = MessageFragment.newInstance();
                return frag;
            } else if (position == OBJ_TAB) {
                ObjectListFragment frag = ObjectListFragment.newInstance(this.locationHandler);
                return frag;
            } else if (position == MAP_TAB){
                GoogleMapFragment frag = GoogleMapFragment.newInstance(this.locationHandler);
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
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, BackendHandlerService.class)); // TODO: Missä pysäytys?
        BusHandler.getBus().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationHandler.start();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationHandler.stop();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void onDetailsClick(View view){
        // Retrieve object for which the Details-button was pressed
        MapObject object = null;
        try {
            object = (MapObject)(((View)(view.getParent().getParent())).getTag());
        } catch (Exception e){
            e.printStackTrace();
            Log.d("NO OBJECT TAG RECEIVED", "-----ON DETAILS CLICK");
        }
        if (object != null) {
            SelectionManager.get().setSelection(object);
            Intent intent = new Intent(this, DetailsActivity.class);
            startActivity(intent);
        }
        else {
            Log.d("OBJECT WAS NULL", "-----ON DETAILS CLICK");
        }
    }
    public void onMapClick(View view){
        // Retrieve object for which the Map-button was pressed
        MapObject object = null;
        try {
            object = (MapObject)((View)(view.getParent().getParent())).getTag();
        } catch (Exception e){
            e.printStackTrace();
            Log.d("NO OBJECT TAG RECEIVED", "-----ON MAP CLICK");
        }
        if (object != null){
            SelectionManager.get().setSelection(object);
            BusHandler.getBus().post(new SelectMapObjectEvent());
            viewPager.setCurrentItem(MAP_TAB);
        }
    }

    // http://stackoverflow.com/questions/13185476/how-to-handle-back-button-using-view-pager
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
}
