package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.reflect.MethodSignature;

import com.kingombo.slf5j.LoggerFactory;

public final aspect ServletLogger {
	private pointcut eventMethodExecution() : execution(void nz.ac.victoria.ecs.nwen304.project3.server.servlets.*.do*(HttpServletRequest, HttpServletResponse));
	
	before(HttpServlet servlet) : target(servlet) && eventMethodExecution() {
		LoggerFactory.getLogger(servlet.getClass()).debug("Invoking %s on %s", 
				((MethodSignature) thisJoinPoint.getSignature()).getName(), servlet.getClass().getName());
	}
}
