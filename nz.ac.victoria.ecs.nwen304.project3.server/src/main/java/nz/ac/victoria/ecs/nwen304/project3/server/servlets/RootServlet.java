package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.ac.victoria.ecs.nwen304.project3.data.DataExchange;
import nz.ac.victoria.ecs.nwen304.project3.entities.Container;
import nz.ac.victoria.ecs.nwen304.project3.entities.ContainerTransformer;
import nz.ac.victoria.ecs.nwen304.project3.entities.Note;
import nz.ac.victoria.ecs.nwen304.project3.entities.NoteTransformer;
import nz.ac.victoria.ecs.nwen304.project3.guice.InjectObject;

import com.google.inject.Inject;

import flexjson.JSONSerializer;

@InjectObject
public final class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 5317525436478295495L;
	
	@Inject
	private DataExchange exchange;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Container root = this.exchange.getRootContainer();
		
		resp.getWriter().write(new JSONSerializer()
				.transform(new ContainerTransformer(), Container.class)
				.transform(new NoteTransformer(), Note.class)
				.prettyPrint(true)
				.serialize(root));
	}
}
