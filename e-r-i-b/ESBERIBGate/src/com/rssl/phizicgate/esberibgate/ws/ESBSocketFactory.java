package com.rssl.phizicgate.esberibgate.ws;

import org.apache.axis.components.net.JSSESocketFactory;

import java.io.IOException;
import java.util.Hashtable;
import javax.net.ssl.SSLSocketFactory;

/**
 * Обертка для JSSESocketFactory, которая устанавливает таймаут для сокета.
 *
 * @author bogdanov
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ESBSocketFactory extends JSSESocketFactory
{
	/**
	 * Constructor JSSESocketFactory
	 *
	 * @param attributes
	 */
	public ESBSocketFactory(Hashtable attributes)
	{
		super(attributes);
	}

	@Override 
	protected void initFactory() throws IOException
	{
		sslFactory = new SSLSocketFactoryWrapper((SSLSocketFactory)SSLSocketFactory.getDefault());
	}
}
