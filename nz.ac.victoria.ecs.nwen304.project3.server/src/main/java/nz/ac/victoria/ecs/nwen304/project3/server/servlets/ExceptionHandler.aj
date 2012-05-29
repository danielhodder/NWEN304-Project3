package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kingombo.slf5j.LoggerFactory;

import flexjson.JSONSerializer;

final aspect ExceptionHandler {
	private pointcut eventMethodExecution() : 
		execution(void nz.ac.victoria.ecs.nwen304.project3.server.servlets.*.do*(HttpServletRequest, HttpServletResponse));
	
	void around(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException : 
			target(servlet) && eventMethodExecution() && args(request, response) {
		try {
			proceed(servlet, request, response);
		} catch (Exception e) {
			LoggerFactory.getLogger(thisJoinPoint.getTarget().getClass())
					.error("An error occoured during the execution of a servlet", e);
			
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(new JSONSerializer()
					.transform(new ExceptionTransformer(), "")
					.exclude("class")
					.serialize(e));
			response.flushBuffer();
		}
	}
}
