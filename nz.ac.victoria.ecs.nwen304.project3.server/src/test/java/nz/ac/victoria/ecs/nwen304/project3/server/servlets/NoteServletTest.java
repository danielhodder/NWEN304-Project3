package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import net.sourceforge.stripes.mock.MockServletContext;

import org.junit.Test;

public class NoteServletTest {
	@Test
	public void testGettingANote() throws Exception {
		final MockServletContext context = new MockServletContext("nwen304");
		context.setServlet(NoteServlet.class, "Note", null);
		
		final MockHttpServletRequest request = new MockHttpServletRequest("/nwen304", "/Note");
		request.setMethod("GET");
		request.setPathInfo("/blarg");
		
		final MockHttpServletResponse response = new MockHttpServletResponse();
		
		context.acceptRequest(request, response);
	}
}
