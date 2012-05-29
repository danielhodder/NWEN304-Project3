package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import net.sourceforge.stripes.mock.MockServletContext;
import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.RawDeserilizer;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectOnCall;
import nz.ac.victoria.ecs.nwen304.project3.guice.UnInjectAfterCall;
import nz.ac.victoria.ecs.nwen304.project3.server.guice.GuiceServletContextListner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;

import flexjson.JSONDeserializer;

public class NoteServletTest {
	@Before public void setupGuice() { new GuiceServletContextListner().contextInitialized(null); }
	@After public void teardownGuice() { new GuiceServletContextListner().contextDestroyed(null); }
	
	@Inject
	private DataExchange data;
	
	@Test
	public void testGettingANoteWhenItDoesntExist() throws Exception {
		final MockServletContext context = new MockServletContext("nwen304");
		context.setServlet(NoteServlet.class, "Note", null);
		
		final MockHttpServletRequest request = new MockHttpServletRequest("/nwen304", "/Note");
		request.setMethod("GET");
		request.setPathInfo("/00000000-0000-0000-0000-000000000000");
		
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		context.acceptRequest(request, response);
		
		assertEquals(404, response.getStatus());
	}
	
	@Test
	@InjectOnCall
	@UnInjectAfterCall
	public void testGettingANote() throws Exception {
		final UUID uuid = new UUID(0, 0);
		final Note n = new Note("test");
		n.setUuid(uuid);
		this.data.save(n);
		
		final MockServletContext context = new MockServletContext("nwen304");
		context.setServlet(NoteServlet.class, "Note", null);
		
		final MockHttpServletRequest request = new MockHttpServletRequest("/nwen304", "/Note");
		request.setMethod("GET");
		request.setPathInfo("/00000000-0000-0000-0000-000000000000");
		
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		context.acceptRequest(request, response);
		
		assertEquals(200, response.getStatus());
		assertEquals(n, new JSONDeserializer<Note>()
				.deserialize(response.getOutputString(), new RawDeserilizer()));
	}
}
