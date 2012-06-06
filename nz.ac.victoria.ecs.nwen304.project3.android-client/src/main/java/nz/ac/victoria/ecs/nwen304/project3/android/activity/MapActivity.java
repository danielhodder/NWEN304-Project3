package nz.ac.victoria.ecs.nwen304.project3.android.activity;

import nz.ac.victoria.ecs.nwen304.project3.android.R;
import roboguice.activity.RoboMapActivity;
import android.os.Bundle;

public final class MapActivity extends RoboMapActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
