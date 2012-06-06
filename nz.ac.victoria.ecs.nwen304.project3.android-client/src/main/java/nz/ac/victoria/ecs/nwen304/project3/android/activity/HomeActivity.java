package nz.ac.victoria.ecs.nwen304.project3.android.activity;


import nz.ac.victoria.ecs.nwen304.project3.android.data.HttpDataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import android.app.Activity;
import android.os.Bundle;

import com.google.inject.Inject;
import com.kingombo.slf5j.Logger;

public final class HomeActivity extends BaseActivity {
	private Logger logger;
	
	@Inject
	private HttpDataExchange data = new HttpDataExchange();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Container root = this.data.getRootContainer();
		
		this.startActivity(openContainer(root.getUuid()));
		
		logger.trace("Redirecting to root container");
		
		finish();
	}
}
