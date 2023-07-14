package com.rssl.phizicgate.esberibgate.ws;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.gate.config.ESBEribConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

/**
 * Обертка для SSLSocketFactory, с возможностью выставления таймаута.
 *
 * @author bogdanov
 * @ created 06.09.13
 * @ $Author$
 * @ $Revision$
 */

public class SSLSocketFactoryWrapper extends SSLSocketFactory
{
	private final SSLSocketFactory socketFactory;

	public SSLSocketFactoryWrapper(SSLSocketFactory socketFactory)
	{
		this.socketFactory = socketFactory;
	}

	@Override
	public String[] getDefaultCipherSuites()
	{
		return socketFactory.getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites()
	{
		return socketFactory.getSupportedCipherSuites();
	}

	/**
	 * Возвращает сокет с установленным таймаутом.
	 *
	 * @param socket сокет.
	 * @return сокет с установленным таймаутом.
	 * @throws SocketException
	 */
	private Socket getSocketWithTimeout(Socket socket) throws SocketException
	{
		ESBEribConfig config = ConfigFactory.getConfig(ESBEribConfig.class);
		String timeout = config.getTimeout();
		socket.setSoTimeout(StringHelper.isEmpty(timeout) ? 0 : Integer.parseInt(timeout));

		return socket;
	}

	@Override
	public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException
	{
		return getSocketWithTimeout(socketFactory.createSocket(socket, s, i, b));
	}

	@Override
	public Socket createSocket(String s, int i) throws IOException, UnknownHostException
	{
		return getSocketWithTimeout(socketFactory.createSocket(s, i));
	}

	@Override
	public Socket createSocket(String s, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException
	{
		return getSocketWithTimeout(socketFactory.createSocket(s, i, inetAddress, i2));
	}

	@Override
	public Socket createSocket(InetAddress inetAddress, int i) throws IOException
	{
		return getSocketWithTimeout(socketFactory.createSocket(inetAddress, i));
	}

	@Override
	public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException
	{
		return getSocketWithTimeout(socketFactory.createSocket(inetAddress, i, inetAddress2, i2));
	}
}
