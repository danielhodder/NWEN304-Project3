package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

import com.google.inject.Inject;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Handles requests for information about notes
 * 
 * @author danielh
 *
 */
@InjectObject
public final class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 3057183228214176466L;

	@Inject
	private DataExchange data;
	
	/**
	 * Delete a note from the system
	 */
	@Override
	protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		UUID noteID = UUID.fromString(req.getPathInfo().substring(1));
		
		this.data.delete(this.data.getItemByID(noteID));
		
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
	/**
	 * Get a note by it's ID
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		UUID noteID = UUID.fromString(req.getPathInfo().substring(1));
		
		Item i = this.data.getItemByID(noteID);
		
		if (i == null || (!(i instanceof Note)))
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else 
			resp.getWriter().write(new JSONSerializer()
					.transform(new NoteTransformer(), Note.class)
					.prettyPrint(true)
					.serialize(i));
	}

	/**
	 * Update a note. The ID in the request must match the ID in the note itself
	 */
	@Override
	protected void doPut(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		Note n = new JSONDeserializer<Note>()
				.use(Note.class, new NoteTransformer())
				.deserialize(new InputStreamReader(req.getInputStream()), Note.class);
		UUID noteID = UUID.fromString(req.getPathInfo().substring(1));
		
		if (!noteID.equals(n.getUuid()))
			throw new IllegalArgumentException("The UUID of the note is not what was expected");
		
		this.data.save(n);
		
		resp.getWriter().write(new JSONSerializer()
				.transform(new NoteTransformer(), Note.class)
				.prettyPrint(true)
				.serialize(n));
	}
	
	
}
