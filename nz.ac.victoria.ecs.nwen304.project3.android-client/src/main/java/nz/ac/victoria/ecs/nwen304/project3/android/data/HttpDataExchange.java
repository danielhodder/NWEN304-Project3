package nz.ac.victoria.ecs.nwen304.project3.android.data;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nz.ac.victoria.ecs.nwen304.project3.android.Project3Application;
import nz.ac.victoria.ecs.nwen304.project3.android.R;
import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.ContainerTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.inject.AbstractModule;
import com.kingombo.slf5j.Logger;
import com.kingombo.slf5j.LoggerFactory;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class HttpDataExchange {
	private Logger logger = LoggerFactory.getLogger();
	
	public void updateContainer(Container item) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut putRequest = new HttpPut(
					Project3Application.getInstance().getString(R.string.server_path) + "/Container/" + item.getUuid().toString());
			putRequest.setEntity(new StringEntity(new JSONSerializer()
						.transform(new ContainerTransformer(), Container.class)
						.transform(new NoteTransformer(), Note.class)
						.serialize(item)));
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}

	public Container getContainerByID(UUID id) {
		HttpClient client = new DefaultHttpClient();
		
		// Try to find the container
		try {
			HttpResponse response = client.execute(new HttpGet(
					Project3Application.getInstance().getString(R.string.server_path) + "/Container/" + id.toString()));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return new JSONDeserializer<Container>()
						.use(Container.class, new ContainerTransformer())
						.use(Note.class, new NoteTransformer())
						.deserialize(new InputStreamReader(response.getEntity().getContent()), Container.class);
			}
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
		
		// We couldn't find it
		return null;
	}
	
	public Note getNoteByID(UUID id) {
		HttpClient client = new DefaultHttpClient();
		
		// Try to find the container
		try {
			HttpResponse response = client.execute(new HttpGet(
					Project3Application.getInstance().getString(R.string.server_path) + "/Note/" + id.toString()));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return new JSONDeserializer<Note>()
						.use(Note.class, new NoteTransformer())
						.deserialize(new InputStreamReader(response.getEntity().getContent()), Note.class);
			}
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
		
		// We couldn't find it
		return null;
	}
	
	public void delete(Note item) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpDelete putRequest = new HttpDelete(
					Project3Application.getInstance().getString(R.string.server_path) + "/Note/" + item.getUuid().toString());
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}
	
	public void delete(Container item) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpDelete putRequest = new HttpDelete(
					Project3Application.getInstance().getString(R.string.server_path) + "/Container/" + item.getUuid().toString());
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_NO_CONTENT)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}

	public Container getRootContainer() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(new HttpGet(
					Project3Application.getInstance().getString(R.string.server_path) + "/"));
			
			return new JSONDeserializer<Container>()
					.use(Container.class, new ContainerTransformer())
					.use(Note.class, new NoteTransformer())
					.deserialize(new InputStreamReader(response.getEntity().getContent()), Container.class);
		} catch (Exception e) {
			logger.error("Error getting root container", e);
			throw new RuntimeException(e);
		}
	}
	
	public static final class Module extends AbstractModule {
		@Override
		protected void configure() {
			bind(HttpDataExchange.class).to(HttpDataExchange.class);
		}
	}

	public void addToContainer(Note note, UUID containerID) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost putRequest = new HttpPost(
					Project3Application.getInstance().getString(R.string.server_path) + "/Container/" + containerID.toString());
			putRequest.setEntity(new StringEntity(new JSONSerializer()
						.transform(new ContainerTransformer(), Container.class)
						.transform(new NoteTransformer(), Note.class)
						.serialize(note)));
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}

	public void updateNote(Note item) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPut putRequest = new HttpPut(
					Project3Application.getInstance().getString(R.string.server_path) + "/Note/" + item.getUuid().toString());
			putRequest.setEntity(new StringEntity(new JSONSerializer()
						.transform(new NoteTransformer(), Note.class)
						.serialize(item)));
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}

	public void addToContainer(Container container, UUID uuid) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost putRequest = new HttpPost(
					Project3Application.getInstance().getString(R.string.server_path) + "/Container/" + uuid.toString());
			putRequest.setEntity(new StringEntity(new JSONSerializer()
						.transform(new ContainerTransformer(), Container.class)
						.transform(new NoteTransformer(), Note.class)
						.serialize(container)));
			
			HttpResponse response = client.execute(putRequest);
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
				throw new Exception("Unexpected status: "+response.getStatusLine());
		} catch (Exception e) {
			logger.error("Exception getting the item as a note", e);
			throw new RuntimeException(e);
		}
	}

	public void delete(Item item) {
		if (item instanceof Note)
			this.delete((Note) item);
		else
			this.delete((Container) item);
	}
}
