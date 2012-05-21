package nz.ac.victoria.ecs.nwen304.project3.server.actions.resolutions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ForwardResolution;
import nz.ac.victoria.ecs.nwen304.project3.entities.Item;

public class XMLEntityResolution extends ForwardResolution {
	private Item entityToExport;
	
	public XMLEntityResolution(Item toExport) {
		super("/xml_entity.jsp");
		
		this.entityToExport = toExport;
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		addParameter("entity", this.entityToExport);
		response.setContentType("application/vnd.notetaker+xml");
		
		super.execute(request, response);
	}
}
