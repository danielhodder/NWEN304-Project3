package nz.ac.victoria.ecs.nwen304.project3.server.data;


import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectOnCall;

import com.google.inject.Inject;

public final class InitialData {
	@Inject
	private DataExchange exchange;
	
	@InjectOnCall
	public void generateData() {
		Container rootContainer = new Container("");
		rootContainer.setRoot(true);
		rootContainer.setUuid(UUID.randomUUID());
		
		{
			Note note = new Note("Note1", "Text Content");
			note.setUuid(UUID.randomUUID());
			rootContainer.getItems().add(note);
			
			this.exchange.save(note);
		}
		
		this.exchange.save(rootContainer);
	}
}
