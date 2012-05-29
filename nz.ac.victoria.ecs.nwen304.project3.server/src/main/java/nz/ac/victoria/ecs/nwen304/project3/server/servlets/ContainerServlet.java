package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import flexjson.JSONSerializer;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.ContainerTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

@InjectObject
public final class ContainerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DataExchange data;

	/**
	 * Get a container and it's contents.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UUID containerID = UUID.fromString(req.getPathInfo().substring(1));
		
		Item i = this.data.getItemByID(containerID);
		
		if (i == null || (!(i instanceof Container)))
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else 
			resp.getWriter().write(new JSONSerializer()
					.transform(new ContainerTransformer(), Container.class)
					.transform(new NoteTransformer(), Note.class)
					.prettyPrint(true)
					.serialize(i));
	}

	/**
	 * Create a new container
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * Update an existsing container
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	/**
	 * Delete a container
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
