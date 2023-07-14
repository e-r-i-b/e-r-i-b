package com.rssl.phizic.utils.http;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.io.*;
import java.net.*;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.List;
import java.util.Map;
import javax.net.ssl.*;

/**
 * @author Erkin
 * @ created 03.07.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для отправки запросов на HTTP-сервера
 */
public class HttpConnector
{
	private static final int DEFAULT_TIMEOUT = 5 * 60 * 1000; // в мс

	/**
	 * Максимальное количество редиректов
	 */
	private static final int REDIRECT_MAX_TRIES = 99;

	private String cookie  = "";

	private String userAgent;

	private int timeout = DEFAULT_TIMEOUT;

	private String contentType;

	private static boolean sslInstalled = false;

	private String accept= "text/html,application/xhtml+xml,application/xml";

	///////////////////////////////////////////////////////////////////////////

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public void setAccept(String accept)
	{
		this.accept = accept;
	}

	/**
	 * Делает запрос к указанному интернет-ресурсу
	 * @param address - адрес интернет-ресурса
	 * @return результат работы обработчика
	 */
	public URLConnection connect(String address) throws IOException, GeneralSecurityException
	{
		// Обеспечиваем переходы по редиректам с сохранением User-Agent
		URLConnection urlConn = null;

		String location = address;
		int count = 0;
		do
		{
			count++;
			URL url = new URL(location);
			urlConn = openConnect(url);
			urlConn.connect();

			Map<String,List<String>> headers  =  urlConn.getHeaderFields();
			for(int i = 0; i < headers.size(); i++)
			{
				String headerKey = urlConn.getHeaderFieldKey(i);

				if ("SET-COOKIE".equalsIgnoreCase(headerKey))
					for (String cook : urlConn.getHeaderFields().get(headerKey))
					{
						cookie = cookie + cook + ";";
					}
			}

			String redirect = urlConn.getHeaderField("Location");
			if (StringHelper.isEmpty(redirect))
				break;
			location = redirect;
		}
		while (count < REDIRECT_MAX_TRIES);


		// reads the CGI response
		if (urlConn != null)
			contentType = urlConn.getHeaderField("Content-Type");

		return urlConn;
	}

	public void sendRequestParams(URLConnection urlConn, String params) throws IOException
	{
		DataOutputStream cgiInput = new DataOutputStream(urlConn.getOutputStream());
		cgiInput.writeBytes(params);
		cgiInput.flush();
	}

	/**
	 * Загружает контент с указанного интернет-ресурса в виде строки
	 * @param address - адрес интернет-ресурса
	 * @return загруженый контент в виде строки
	 */
	public String fetchString(String address) throws IOException, GeneralSecurityException
	{
		URLConnection urlConn = connect(address);

		// reads the CGI response
		if (urlConn != null)
		{
			InputStream inputStream = urlConn.getInputStream();
			try
			{
				return streamToString(inputStream);
			}
			finally
			{
				inputStream.close();
			}
		}

		return null;
	}

	private String streamToString(InputStream inputStream) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try
		{
			while (true)
			{
				String line = reader.readLine();
				if (line == null)
					break;
				sb.append(line);
			}

			return sb.toString();
		}
		finally
		{
			try { reader.close(); } catch (IOException ignored) {}
		}
	}

	public URLConnection openConnect(URL url) throws IOException, GeneralSecurityException, SecurityException
	{
		if (url.getProtocol().equalsIgnoreCase("https"))
			setupSSL();

		Proxy proxy = getProxy();

		HttpURLConnection urlConn;
		if (proxy != null)
			urlConn = (HttpURLConnection) url.openConnection(proxy);
		else urlConn = (HttpURLConnection) url.openConnection();

		// Отключаем автоматический переход по редиректам. Так как не сохраняется User-Agent
		urlConn.setInstanceFollowRedirects(false);
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setReadTimeout(timeout);
		urlConn.setConnectTimeout(timeout);
		if (StringHelper.isNotEmpty(accept))
			urlConn.setRequestProperty("Accept", accept);
		// Устанавливаем User-Agent
		if (StringHelper.isNotEmpty(userAgent))
			urlConn.setRequestProperty("User-Agent", userAgent);
		if (StringHelper.isNotEmpty(contentType))
			urlConn.setRequestProperty("Content-Type", contentType);
		if (!StringHelper.isEmpty(cookie))
			urlConn.setRequestProperty("Cookie", cookie);
		return urlConn;
	}

	private Proxy getProxy() throws IOException
	{
		Properties iccs = readProperties("iccs.properties");

		String proxyUrl = null;
		int proxyPort = -1;
		try
		{
			proxyUrl = iccs.getProperty("com.rssl.proxy.url");
			proxyPort = Integer.valueOf(iccs.getProperty("com.rssl.proxy.port"));
		}
		catch (NumberFormatException ignored) {}

		if (StringHelper.isNotEmpty(proxyUrl) && proxyPort > 0) {
			SocketAddress socketAddress = new InetSocketAddress(proxyUrl, proxyPort);
			return new Proxy(Proxy.Type.HTTP, socketAddress);
		}

		return null;
	}

	private Properties readProperties(String resource) throws IOException
	{
		InputStream inputStream = ClassHelper.getInputStreamFromClassPath(resource);
		if (inputStream == null)
			throw new IllegalArgumentException("Ресурс не найден: " + resource);

		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

	@SuppressWarnings({"AnonymousInnerClassWithTooManyMethods"})
	private void setupSSL() throws GeneralSecurityException
	{
		if (sslInstalled)
			return;

		synchronized (this.getClass())
		{
			if (sslInstalled)
				return;

			// Создаем новый trust менеджер который доверяет всем сертификатам
			TrustManager[] trustManagers = { new X509TrustManager()
			{
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

				public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}
			}};
			// Верификатор имен хостов
			HostnameVerifier verifier = new HostnameVerifier()
			{
				public boolean verify(String s, SSLSession sslSession)
				{
					return true;
				}
			};

			// Устанавливаем новый trust менеджер и верификатор хостов
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustManagers, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(verifier);

			sslInstalled = true;
		}
	}
}
