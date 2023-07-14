package com.rssl.phizic.operations.test.mobile.common;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

/**
 * @author Mescheryakova
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class SendAbstractRequestOperation extends OperationBase
{
	private static final int DEFAULT_TIMEOUT = 3 * 60 * 1000; // в мс



	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private boolean parseResponse = false;


	// Входные параметры ------------------------

	private String targetUrl;

	private String targetAddress;

	private String cookie;

	private String proxyUrl;

	private Integer proxyPort;

	private String params;

	private int timeout = DEFAULT_TIMEOUT;

	// Выходные параметры -----------------------

	private String status;

	private String contentType;

	private String result;

	private Object response;

	private int responseStatusCode;

	private URL lastUrl; //url последнего, отправленного в API запроса

	///////////////////////////////////////////////////////////////////////////

	public void initialize(String targetUrl, String targetAddress)
	{
		this.targetUrl = targetUrl;
		this.targetAddress = targetAddress;
	}

	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	public void setProxyUrl(String proxyUrl)
	{
		this.proxyUrl = proxyUrl;
	}

	public void setProxyPort(Integer proxyPort)
	{
		this.proxyPort = proxyPort;
	}

	public void setParseResponse(boolean parseResponse)
	{
		this.parseResponse = parseResponse;
	}

	public boolean isParseResponse()
	{
		return parseResponse;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

	public void setParams(MultiMap params)
	{
		try
		{
			List<String> pairs = new ArrayList<String>(params.size());
			for (Object oEntry : params.entrySet())
			{
				Map.Entry entry = (Map.Entry) oEntry;
				String key = (String) entry.getKey();
				Collection values = (Collection) entry.getValue();
				for (Object oValue : values)
				{
					pairs.add(URLEncoder.encode(key, "Windows-1251") + "=" + URLEncoder.encode("" + oValue, "Windows-1251"));
					updateVersion(key, oValue);
				}
			}
			setParams(StringUtils.join(pairs, "&"));
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(e.getMessage(), e);
		}
	}



	public void send() throws BusinessException
	{
		try
		{
			Proxy proxy = null;
			if (!StringHelper.isEmpty(proxyUrl) && proxyPort != null)
				proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));

			if (targetUrl.startsWith("https"))
				setupHttps();

			int count = 0;
			final String address = targetUrl + targetAddress;
			// Обеспечиваем переходы по редиректам с сохранением User-Agent
			URLConnection urlConn = null;
			status = null;
			while (status == null || urlConn.getHeaderField("Location") != null)
			{
				// URL of target page script.
				lastUrl = new URL((count > 0) ? urlConn.getHeaderField("Location") : address);
				urlConn = connectMobileApp(lastUrl, cookie, proxy, count == 0);

				if (count == 0)
					sendMobileAppRequest(urlConn, params);

				status = urlConn.getHeaderField(0);

				updateCookie(urlConn);

				// защита от бесконечного редиректа
				if (count == 99)
					throw new BusinessException("Бесконечное зацикливание");

				count++;
			}

			// reads the CGI response
			if (urlConn != null)
			{
				contentType =urlConn.getHeaderField("Content-Type");
				result = readMobileAppResponse(urlConn);
				if (parseResponse)
					response = parseResponse(result);
				if (response != null)
					responseStatusCode = extractStatusFromResponse();
			}
		}
		catch (MalformedURLException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (GeneralSecurityException e)
		{
			throw new BusinessException(e);
		}
	}

	private void updateCookie(URLConnection urlConn)
	{
		String cookieField = urlConn.getHeaderField("Set-Cookie");

		if (cookieField != null)
		{
			Pattern pattern = Pattern.compile("jsessionid", Pattern.CASE_INSENSITIVE);

			if (pattern.matcher(cookieField).find())
			{
				cookie = cookieField.split(";")[0];
			}
		}
	}

	private Object parseResponse(String result) throws BusinessException
	{
		try
		{
    		String contextPath = getJaxbContextPath();

			JAXBContext jaxbContext = JAXBContext.newInstance(contextPath, getClass().getClassLoader());
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Schema schema = getSchema();
            if (schema != null)
                jaxbUnmarshaller.setSchema(schema);
			return jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(StringUtils.trim(result))));
		}
		catch (JAXBException e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public String getCookie()
	{
		return cookie;
	}

	public String getStatus()
	{
		return status;
	}

	public String getContentType()
	{
		return contentType;
	}

	public String getResult()
	{
		return result;
	}

	public Object getResponse()
	{
		return response;
	}

	public int getResponseStatusCode()
	{
		return responseStatusCode;
	}

	public URL getLastUrl()
	{
		return lastUrl;
	}

	protected abstract String getJaxbContextPath() throws BusinessException;

	private int extractStatusFromResponse() throws BusinessException
	{
		try
		{
			Class responseClass = response.getClass();
			Method getStatus = responseClass.getMethod("getStatus");
			Object responseStatus = getStatus.invoke(response);
			Class statusClass = responseStatus.getClass();
			Method getCode = statusClass.getMethod("getCode");
			return (Integer) getCode.invoke(responseStatus);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException("Некорректный тип документа", e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Некорректный тип документа", e);
		}
		catch (InvocationTargetException e)
		{
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new RuntimeException("Ошибка выполнения метода", e.getTargetException());
		}
	}

	private static boolean sslInstalled = false;

	private static void setupHttps() throws GeneralSecurityException
	{
//		if (sslInstalled)
//			return;

		// Создаем новый trust менеджер который доверяет всем сертификатам
		TrustManager[] trustManagers = { new AllTrustManager() };
		// Ферификатор имен хостов
		HostnameVerifier verifier = new PositiveHostnameVerifier();

		// Устанавливаем новый trust менеджер и верификатор хостов
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustManagers, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(verifier);

//		sslInstalled = true;
	}

	private URLConnection connectMobileApp(URL url, String cookie, Proxy proxy, boolean specifyContentType) throws IOException
	{
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
		// Устанавливаем User-Agent
		urlConn.setRequestProperty("User-Agent", "Mobile Device");
		if (specifyContentType)
			urlConn.setRequestProperty("Content-Type", createContentType());
		if (!StringHelper.isEmpty(cookie))
			urlConn.setRequestProperty("Cookie", cookie);
		return urlConn;
	}

	protected void sendMobileAppRequest(URLConnection urlConn, String params) throws IOException
	{
		// Send POST output.
		DataOutputStream cgiInput = new DataOutputStream(urlConn.getOutputStream());
		try
		{
			cgiInput.writeBytes(params);
			cgiInput.flush();
		}
		finally
		{
//			cgiInput.close();
		}
	}

	private String readMobileAppResponse(URLConnection urlConn) throws IOException
	{
		StringBuilder result = new StringBuilder();
		BufferedReader cgiOutput = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		try
		{
			while (true)
			{
				String line = cgiOutput.readLine();
				if (line == null)
					break;
				result.append("\n").append(line);
			}
		}
		finally
		{
//			cgiOutput.close();
		}
		return result.toString();
	}

	protected abstract void updateVersion(String key, Object Value);

	protected String createContentType()
	{
		return "application/x-www-form-urlencoded";
	}

	/**
	 * Получить xsd-схему для валидации
	 * @return xsd-схема или null если валидация не нужна
	 * @throws BusinessException
	 */
    protected Schema getSchema() throws BusinessException
    {
        return null;
    }
}
