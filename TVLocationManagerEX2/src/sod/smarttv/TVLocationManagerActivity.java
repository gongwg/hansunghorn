package sod.smarttv;

import java.util.List;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import sod.test.smarttv.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TVLocationManagerActivity extends MapActivity {
    /** Called when the activity is first created. */
	List<Overlay> listOfOverlays;
	PinOverlay pinOverlay;
	
	Button sendGeoPointButton;
	MapView map;
	
	TVLocationManager tvLocationManager;
	TVLocation tvLocation;
	
	GeoPoint currentGp;
	
	class MapOverlay extends com.google.android.maps.Overlay{

		//맵에서 터치를 했을 때...
		@Override
		public boolean onTouchEvent(MotionEvent e, MapView mapView) {
			// TODO Auto-generated method stub
			if(e.getAction() == 1){
				GeoPoint selectedGp = 
						mapView.getProjection().fromPixels((int)e.getX(), (int)e.getY());
				
				//찍혀진 장소를 표시
				Toast.makeText(getBaseContext(), selectedGp.getLatitudeE6() / 1E6 + "," + selectedGp.getLongitudeE6() , Toast.LENGTH_SHORT).show();
				
				OverlayItem overlayItem = new OverlayItem(selectedGp, "선택된 장소", "선택된 장소입니다.");
				
				pinOverlay.clearOverlay();
				pinOverlay.addOverlay(overlayItem);
			}
			
			return false;
		}
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        ///////// TVLocationManager init.../////////////////////////////////////////////////////////
        tvLocationManager = new TVLocationManager();
        
        
        
        
        //////// end TVLcationManager Setting/////////////////////////////////////////////////////
        
        
        
        ///////// TVLocation init.../////////////////////////////////////////////////////////////////
        tvLocation = new TVLocation();
        
        ////////end TVLocation Setting///////////////////////////////////////////////////////////////
        
        
        
        /////////  MapView  Setting////////////////////////////////////////////////////////////////////
        map = (MapView)findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
        MapController mapController = map.getController();
        mapController.setZoom(20);
        
        listOfOverlays = map.getOverlays();
        
        Drawable drawable = getResources().getDrawable(R.drawable.pin);
        pinOverlay = new PinOverlay(drawable, this);
        
        //pin.. init.. 한성대학교로 고쳐야함
        /*
        OverlayItem overlayItem=
        		new OverlayItem(new GeoPoint(37582694, 127010823),
        				"한성대학교", "한성대학교입니다.");
        pinOverlay.addOverlay(overlayItem);
        */
        
        /*
        MapOverlay mapOverlay = new MapOverlay();
        
        listOfOverlays.add(mapOverlay);
        map.postInvalidate();
        listOfOverlays.add(pinOverlay);
        */
        LocationManager locationManager = 
        		(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location =
        		locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        
        double latitude,longitude;
        
        if(location != null){
        	latitude = location.getLatitude();
        	longitude = location.getLongitude();
        }else{//디폴트 위치, 한성대학교
        	latitude = 37.582694;
        	longitude = 127.010823;
        }
        
        currentGp = new GeoPoint( (int)(latitude*1E6), (int)(longitude*1E6));
        mapController.animateTo(currentGp);
        
        
        MapOverlay mapOverlay = new MapOverlay();
        
        OverlayItem overlayItem=
        		new OverlayItem(new GeoPoint((int)(latitude * 1E6), (int)(longitude * 1E6)),
        				"한성대학교", "한성대학교입니다.");
        pinOverlay.addOverlay(overlayItem);
        
        listOfOverlays.add(mapOverlay);
        map.postInvalidate();
        listOfOverlays.add(pinOverlay);
        /////end... MapView Setting////////////////////////////////////////////////////////////////////////
        
        //////   sendGeoPointButton Setting...//////////////////////////////////////////////////////////
        
        Button sendGeoPointButton = (Button)findViewById(R.id.sendGeoPointButton);
        
        sendGeoPointButton.setOnClickListener(sendGeoPointButtonListener);
        
        
        ///// end.. sendGeoPointButton Setting//////////////////////////////////////////
        
    }
	
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected OnClickListener sendGeoPointButtonListener = new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			tvLocation.setLatitude(currentGp.getLatitudeE6() / 1E6 );
			tvLocation.setLogitude(currentGp.getLongitudeE6() / 1E6);
			
			tvLocationManager.setTVLocation(tvLocation);
			
		}
		 

	};
    
    
}