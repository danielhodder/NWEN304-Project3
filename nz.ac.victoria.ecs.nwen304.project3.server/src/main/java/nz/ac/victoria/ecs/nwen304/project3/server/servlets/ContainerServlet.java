package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.ContainerTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.RawDeserilizer;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

import com.google.inject.Inject;
import com.kingombo.slf5j.Logger;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@InjectObject
public final class ContainerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger logger;
	
	@Inject
	private DataExchange data;

	/**
	 * Get a container and it's contents.
	 */
	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		final UUID containerID = UUID.fromString(req.getPathInfo().substring(1));
		
		logger.trace("Requesting container with ID %s", containerID);
		
		final Item i = this.data.getItemByID(containerID);
		
		if (i == null || (!(i instanceof Container))) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			
			logger.info("Could not find container with id %s", containerID);
		} else 
			resp.getWriter().write(new JSONSerializer()
					.transform(new ContainerTransformer(), Container.class)
					.transform(new NoteTransformer(), Note.class)
					.prettyPrint(true)
					.serialize(i));
	}

	/**
	 * Create a new item in a container
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		final UUID containerID = UUID.fromString(req.getPathInfo().substring(1));
		final Item container = this.data.getItemByID(containerID);
		if (container == null || (!(container instanceof Container)))
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		final Item i = new JSONDeserializer<Item>()
					.deserialize(req.getReader(), new RawDeserilizer());
		if (i instanceof Container)
			((Container) i).setRoot(false);
		
		((Container) container).getItems().add(i);
		
		this.data.save(i);
		this.data.save(container);
		
		resp.getWriter().write(new JSONSerializer()
				.transform(new ContainerTransformer(), Container.class)
				.transform(new NoteTransformer(), Note.class)
				.prettyPrint(true)
				.serialize(i));
	}

	/**
	 * Update an existsing container
	 */
	@Override
	protected void doPut(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		final UUID containerID = UUID.fromString(req.getPathInfo().substring(1));
		final Item container = this.data.getItemByID(containerID);
		if ( container == null || (!(container instanceof Container)))
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		final Item i = new JSONDeserializer<Item>()
					.deserialize(req.getReader(), new RawDeserilizer());
		if (i.getUuid() == null)
			throw new IllegalArgumentException("Given item does not have an ID");
		
		final Item current = this.data.getItemByID(i.getUuid());
		
		((Container) container).getItems().remove(current);
		((Container) container).getItems().add(i);
		
		this.data.save(container);
		
		resp.getWriter().write(new JSONSerializer()
				.transform(new ContainerTransformer(), Container.class)
				.transform(new NoteTransformer(), Note.class)
				.prettyPrint(true)
				.serialize(i));
	}

	/**
	 * Delete a container
	 */
	@Override
	protected void doDelete(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		final UUID containerID = UUID.fromString(req.getPathInfo().substring(1));
		final Item container = this.data.getItemByID(containerID);
		if ( container == null || (!(container instanceof Container)))
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		final Item i = new JSONDeserializer<Item>()
					.deserialize(req.getReader(), new RawDeserilizer());
		if (i.getUuid() == null)
			throw new IllegalArgumentException("Given item does not have an ID");
		
		final Item current = this.data.getItemByID(i.getUuid());
		
		((Container) container).getItems().remove(current);
		
		this.data.save(container);
		
		resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
