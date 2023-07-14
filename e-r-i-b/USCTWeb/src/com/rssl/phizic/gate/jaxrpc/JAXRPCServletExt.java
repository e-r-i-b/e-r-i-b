package com.rssl.phizic.gate.jaxrpc;

import com.sun.xml.rpc.server.http.JAXRPCServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * @author khudyakov
 * @ created 02.03.14
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

