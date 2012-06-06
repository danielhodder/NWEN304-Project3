package nz.ac.victoria.ecs.nwen304.project3.android.activity;

import java.util.List;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.android.R;
import nz.ac.victoria.ecs.nwen304.project3.android.data.HttpDataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kingombo.slf5j.Logger;

public class ContainerActivitiy extends BaseActivity {
	private Logger logger;
	
	private HttpDataExchange data = new HttpDataExchange();
	
	@InjectView(R.id.main_container_name) private TextView containerheader;
	@InjectView(R.id.main_new_button) private Button addButton;
	@InjectView(R.id.main_container_contents) private ListView containerContents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.main);
		
		try {
			final Container thisContaienr = this.data.getContainerByID(UUID.fromString(
					this.getIntent().getExtras().getString(Intents.Container.ContainerID.toString())));
			
			if (thisContaienr == null)
				throw new NullPointerException();
			
			this.containerheader.setText(thisContaienr.getName());
			this.containerContents.setAdapter(new ContainerListAdaptor(this, thisContaienr.getItems()));
			this.containerContents.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Item clickedItem = thisContaienr.getItems().get(position);
					logger.trace("Clicked on %s", clickedItem.getName());
					
					if (clickedItem instanceof Container) {
						logger.trace("The clicked item is a container");
						
						startActivity(openContainer(clickedItem.getUuid()));
					} else {
						logger.trace("The clicked item is a note");
						
						startActivity(openNote(clickedItem.getUuid(), thisContaienr.getUuid()));
					}
				}
			});
			this.containerContents.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
					final String[] options = { "Delete" };
					new AlertDialog.Builder(ContainerActivitiy.this)
							.setTitle("Select an option")
							.setItems(options, new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									String option = options[which];
									if ("Delete".equals(option))
										data.delete(thisContaienr.getItems().get(position));
									
									((ContainerListAdaptor) containerContents.getAdapter()).notifyDataSetChanged();
								}
							})
							.show();
					
					return true;
				}
			});
			
			this.addButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final String[] options = new String[] { "Note", "Container" };
					new AlertDialog.Builder(ContainerActivitiy.this)
							.setTitle("Select item type")
							.setItems(options, new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									String chosenOptions = options[which];
									
									if (chosenOptions.equals("Note"))
										startActivity(openNote(null, thisContaienr.getUuid()));
									else {
										
										AlertDialog.Builder name = new AlertDialog.Builder(ContainerActivitiy.this)
												.setTitle("Enter container name")
												.setMessage("Please enter the name of the container");
										final EditText containerName = new EditText(ContainerActivitiy.this);
										name.setView(containerName);
										name.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												data.addToContainer(new Container(containerName.getText().toString()), thisContaienr.getUuid());
												
												startActivity(openContainer(thisContaienr.getUuid()));
												finish();
											}
										});
										name.show();
									}
									
									((ContainerListAdaptor) containerContents.getAdapter()).notifyDataSetChanged();
								}
							})
							.show();
				}
			});
		} catch (Exception e) {
			logger.error("Could not find container", e);
			
			AlertDialog dialog = new AlertDialog.Builder(this).create();
			dialog.setTitle("Not Found");
			dialog.setMessage("The container was not found.");
			dialog.setIcon(android.R.drawable.ic_dialog_alert);
			dialog.show();
			dialog.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					finish();
				}
			});
				return;
		}
	}
	
	public static final class ContainerListAdaptor extends ArrayAdapter<Item> {
		private List<Item> items;
		private Activity context;
		
		public ContainerListAdaptor(Activity context, List<Item> items) {
			super(context, R.layout.main_list, items);
			
			this.context = context;
			this.items = items;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (convertView == null) {
				final LayoutInflater inflator = this.context.getLayoutInflater();
				view = inflator.inflate(R.layout.main_list, null);
				final ViewHolder viewHolder = new ViewHolder();
				viewHolder.name = (TextView) view.findViewById(R.id.main_list_name);
				view.setTag(viewHolder);
			} else {
				view = convertView;
			}
			
			final ViewHolder holder = (ViewHolder) view.getTag();
			holder.name.setText(this.items.get(position).getName());
			return view;
		}
		
		class ViewHolder {
			TextView name;
		}
	}
}
