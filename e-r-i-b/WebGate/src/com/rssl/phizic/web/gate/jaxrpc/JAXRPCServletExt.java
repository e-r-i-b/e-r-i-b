package com.rssl.phizic.web.gate.jaxrpc;

import com.sun.xml.rpc.server.http.JAXRPCServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author egorova
 * @ created 19.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class JAXRPCServletExt extends JAXRPCServlet
{
	public void init(ServletConfig cfg) throws ServletException
	{
		ClassLoader old = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
		try
		{
			super.init(cfg);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader( old );
		}
	}
}
