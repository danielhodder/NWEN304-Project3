package nz.ac.victoria.ecs.nwen304.project3.android.activity;

import java.util.ArrayList;
import java.util.List;

import nz.ac.victoria.ecs.nwen304.project3.android.R;
import nz.ac.victoria.ecs.nwen304.project3.android.data.HttpDataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import static java.lang.Math.round;

public final class MapActivity extends RoboMapActivity {
	@InjectView(R.id.map_mapview) private MapView map;
	@InjectResource(R.drawable.marker) private Drawable mapPoint;
	private HttpDataExchange data = new HttpDataExchange();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.map);
		
		List<Overlay> overlays = this.map.getOverlays();
		MapItomizedOverlay itemizedOverlay = new MapItomizedOverlay(mapPoint);
		populateNotesOnMap(this.data.getRootContainer(), itemizedOverlay);
		overlays.add(itemizedOverlay);
	}
	
	private void populateNotesOnMap(Container in, MapItomizedOverlay overlay) {
		for (Item i : in.getItems()) {
			if (i instanceof Container) {
				populateNotesOnMap((Container) i, overlay);
				continue;
			}
			
			Note n = (Note) i;
			overlay.addOverlay(new OverlayItem(
					new GeoPoint(
							(int) round(n.getLatatude() * 1000000.0), 
							(int) round(n.getLongdtude() * 1000000.0)), 
					"",
					""));
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public static final class MapItomizedOverlay extends ItemizedOverlay {
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		
		public MapItomizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
		}
		
		public void addOverlay(OverlayItem overlay) {
		    mOverlays.add(overlay);
		    populate();
		}
		
		@Override
		protected OverlayItem createItem(int i) {
		  return mOverlays.get(i);
		}
		
		@Override
		public int size() {
		  return mOverlays.size();
		}
		
	}
}
