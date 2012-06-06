package nz.ac.victoria.ecs.nwen304.project3.android.activity;

import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.android.R;
import nz.ac.victoria.ecs.nwen304.project3.android.data.HttpDataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kingombo.slf5j.Logger;

public final class NoteActivity extends BaseActivity {
	private Logger logger;
	private HttpDataExchange data = new HttpDataExchange();
	
	@InjectView(R.id.note_name) private EditText name;
	@InjectView(R.id.note_content) private EditText content;
	@InjectView(R.id.note_save) private Button saveButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.note);
		
		
		try {
			final boolean isNewNote = this.getIntent().getExtras().getString(Intents.Note.NoteID.toString()) == null;
			final Note thisNote = (isNewNote ?
					new Note("", "") : // We are creating a new note
					this.data.getNoteByID(UUID.fromString(
							this.getIntent().getExtras().getString(Intents.Note.NoteID.toString()))));
			
			if (thisNote == null)
				throw new NullPointerException();
			
			this.name.setText(thisNote.getName());
			this.content.setText(thisNote.getContents());
			this.saveButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					thisNote.setName(name.getText().toString());
					thisNote.setContents(content.getText().toString());
					
					// Set the location information
					String locationProvider = LocationManager.NETWORK_PROVIDER;
					Location location = ((LocationManager) getSystemService(Context.LOCATION_SERVICE))
							.getLastKnownLocation(locationProvider);
					thisNote.setLatatude(location.getLatitude());
					thisNote.setLongdtude(location.getLongitude());
					
					if (isNewNote)
						data.addToContainer(thisNote, UUID.fromString(
								getIntent().getExtras().getString(Intents.Note.ParentContainerID.toString())));
					else
						data.updateNote(thisNote);
					finish();
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
}
