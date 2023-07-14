package com.rssl.phizic.gate.impl;

import com.sun.xml.rpc.client.http.HttpClientTransport;
import com.sun.xml.rpc.soap.message.SOAPMessageContext;

import java.net.HttpURLConnection;
import java.io.IOException;

/**
 * Транспорт, используется в JAX-RPC для установки таймаута на соединение
 * @author gladishev
 * @ created 05.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class TimeoutHttpTransport extends HttpClientTransport
{
	public static final String GATE_WRAPPER_CONNECTION_TIMEOUT = "com.rssl.iccs.gate.stub.connection.timeout";
	public static final String SOCKET_TIMEOUT_EXCEPTION = "java.net.SocketTimeoutException";

	private int timeout; //время ожидания ответа от сервера

	public TimeoutHttpTransport(){
	  super();
   }

	/**
	 * @param timeout таймаут
	 */
	public TimeoutHttpTransport(int timeout){
		this.timeout = timeout;
	}

	@Override
	protected HttpURLConnection createHttpConnection(String endpoint, SOAPMessageContext context) throws IOException
	{
		HttpURLConnection httpConn = super.createHttpConnection(endpoint, context);
		if(timeout > 0){
			httpConn.setReadTimeout(timeout);
			httpConn.setConnectTimeout(timeout);
		}
		return httpConn;
	}
}
