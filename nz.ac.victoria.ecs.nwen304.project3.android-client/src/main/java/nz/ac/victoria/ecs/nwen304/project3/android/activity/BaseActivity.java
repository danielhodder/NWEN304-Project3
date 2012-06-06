package nz.ac.victoria.ecs.nwen304.project3.android.activity;

import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import roboguice.activity.RoboActivity;

public class BaseActivity extends RoboActivity {

	public BaseActivity() {
		super();
	}

	protected Intent openContainer(UUID idOfContainer) {
		Intent intent = new Intent(this, ContainerActivitiy.class);
		Bundle b = new Bundle();
		b.putString(Intents.Container.ContainerID.toString(), idOfContainer.toString());
		intent.putExtras(b);
		
		return intent;
	}
	
	protected Intent openNote(UUID idOfNote, UUID parentContainer) {
		Intent intent = new Intent(this, NoteActivity.class);
		Bundle b = new Bundle();
		b.putString(Intents.Note.NoteID.toString(), (idOfNote == null ? null : idOfNote.toString()));
		b.putString(Intents.Note.ParentContainerID.toString(), parentContainer.toString());
		intent.putExtras(b);
		
		return intent;
	}

}