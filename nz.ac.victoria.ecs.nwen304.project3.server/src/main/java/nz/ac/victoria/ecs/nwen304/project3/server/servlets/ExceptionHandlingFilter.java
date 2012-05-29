package nz.ac.victoria.ecs.nwen304.project3.server.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import nz.ac.victoria.ecs.nwen304.project3.entities.ExceptionTransformer;

import com.kingombo.slf5j.Logger;

import flexjson.JSONSerializer;

public class ExceptionHandlingFilter implements Filter {
	private Logger logger;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig filterconfig) throws ServletException { /* NOP */ }

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest servletrequest,
			final ServletResponse response, final FilterChain filterchain)
			throws IOException, ServletException {
		try {
			filterchain.doFilter(servletrequest, response);
		} catch (final Exception e) {
			this.logger.error("An error occoured during the execution of a servlet", e);
	
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(new JSONSerializer()
					.transform(new ExceptionTransformer(), "")
					.exclude("class")
					.serialize(e));
			response.flushBuffer();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
