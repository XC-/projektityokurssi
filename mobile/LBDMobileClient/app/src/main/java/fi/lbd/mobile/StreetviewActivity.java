package fi.lbd.mobile;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.debug.hv.ViewServer;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import fi.lbd.mobile.mapobjects.MapObject;
import fi.lbd.mobile.mapobjects.MapObjectSelectionManager;

/**
 *  Activity to provide simple Streetview functionality.
 *
 * Created by Ossi.
 *
 */

public class StreetviewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streetview);
        ViewServer.get(this).addWindow(this);
    }

    // TODO: Check if Streetview exists for a given location
    @Override
    public void onStart(){
        super.onStart();
        ActiveActivitiesTracker.activityStarted(this);

        StreetViewPanoramaFragment streetview = (StreetViewPanoramaFragment)getFragmentManager()
                .findFragmentById(R.id.streetviewpanorama);
        StreetViewPanorama panorama = streetview.getStreetViewPanorama();
        MapObject object = MapObjectSelectionManager.get().getSelectedMapObject();
        if(panorama != null && object != null) {
            panorama.setPosition(new LatLng(object.getPointLocation().getLatitude(),
                    object.getPointLocation().getLongitude()));
        }
        else {
            onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        ActiveActivitiesTracker.activityStopped(this);
    }
}
