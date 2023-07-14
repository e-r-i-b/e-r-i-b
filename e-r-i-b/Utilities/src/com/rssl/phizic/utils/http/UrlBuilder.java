package com.rssl.phizic.utils.http;

/**
 * @author Evgrafov
 * @ created 16.12.2005
 * @ $Author: erkin $
 * @ $Revision: 73843 $
 */

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import sun.security.action.GetPropertyAction;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Given a URL and a set of parameters, provide abilites to add and remove parameters (either directly, from a
 * Map, or from a Bean), and finally reconstruct the proper URL. For example:
 * <p/>
 * <code> UrlBuilder builder = new UrlBuilder() .setUrl( strUrl )) .addParameters( p_request.getParameterMap()
 * ); .removeParameter( "id" ) .addParameter( "session", strSession ); </code>
 * <p/>
 * Note that this class currently does not support URL rewriting of cookie strings (though it should).
 */
public class UrlBuilder
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static String defaultEncoding;

	static
	{
		defaultEncoding = (String) AccessController.doPrivileged(
				new GetPropertyAction("file.encoding")
		);
	}
	private String urlWithoutParameters;
	private Map<String,String>    parametersMap = new HashMap<String, String>();
	private String encoding      = defaultEncoding;


	public UrlBuilder() {}

	public UrlBuilder(String url)
	{
		setUrl(url);
	}

	public UrlBuilder setUrl(String url)
	{
		// Nothing to do?

		if (url == null || url.length() == 0)
		{
			urlWithoutParameters = "";
			return this;
		}

		// Split off the given URL from its query string

		StringTokenizer queryStringTokenizer = new StringTokenizer(url, "?");

		urlWithoutParameters = queryStringTokenizer.nextToken();

		// Parse the query string (if any) into name/value pairs

		if (queryStringTokenizer.hasMoreTokens())
		{
			String queryString = queryStringTokenizer.nextToken();

			if (queryString != null)
			{
				StringTokenizer nameValuePairTokenizer = new StringTokenizer(queryString, "&");

				while (nameValuePairTokenizer.hasMoreTokens())
				{
					try
					{
						String nameValuePair = nameValuePairTokenizer.nextToken();
						StringTokenizer valueTokenizer = new StringTokenizer(nameValuePair, "=");

						String name = valueTokenizer.nextToken();
						String value = valueTokenizer.nextToken();

						parametersMap.put(name, value);
					}
					catch (Throwable t)
					{
						// If we cannot parse a parameter, ignore it
					}
				}
			}
		}

		return this;
	}

	/** Construct the final query string */
	public String getUrl()
	{
		// Construct the final query string

		StringBuffer buf = new StringBuffer();

		boolean firstTime = true;

		for (Iterator<String> i = parametersMap.keySet().iterator(); i.hasNext();)
		{
			String name = i.next();
			String value = parametersMap.get(name);

			if (firstTime)
				firstTime = false;
			else
				buf.append("&");

			buf.append(name);
			buf.append("=");
			buf.append(value);
		}

		// Reconstruct the URL

		if (buf.length() > 0)
		{
			buf.insert(0, '?');
			buf.insert(0, urlWithoutParameters);

			return buf.toString();
		}

		return urlWithoutParameters;
	}

	/** Add parameters from a map */

	public UrlBuilder addParameters(Map<String,String> parameters)
	{
		// Nothing to do?

		if (parameters == null)
			return this;

		for (Iterator<String> i = parameters.keySet().iterator(); i.hasNext();)
		{
			String name = i.next();
			String value = parameters.get(name);

			addParameter(name, value);
		}

		return this;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public UrlBuilder setEncoding(String encoding) throws UnsupportedEncodingException
	{
		this.encoding = encoding;
		return this;
	}

	/** Add parameters defined by a bean */
	public UrlBuilder addBeanParameters(Object bean)
	{
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);

		for (int i = 0; i < descriptors.length; i++)
		{
			try
			{
				String name = descriptors[i].getName();
				String value = PropertyUtils.getSimpleProperty(bean, name).toString();

				addParameter(name, value);
			}
			catch (Throwable t)
			{
				// If we can't retrieve a property, no biggie
			}
		}

		return this;
	}

	/** Add a single parameter
	 * @noinspection deprecation*/
	public UrlBuilder addParameter(String name, String value)
	{
		if (name == null || value == null)
			return this;

		try
		{
			parametersMap.put(name, URLEncoder.encode(value, encoding));
		}
		catch (UnsupportedEncodingException e)
		{
			// check encoding in setEncoding
			log.error("Не удалось добавить параметр name = " + name, e);
		}
		return this;
	}

	public UrlBuilder removeParameter(String name)
	{
		parametersMap.remove(name);
		return this;
	}

	public String getParameter(String name)
	{
		try
		{
			String value = parametersMap.get(name);
			return value == null ? null : URLDecoder.decode(value, encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			// check encoding in setEncoding
			log.error("Не удалось получить параметр name = " + name, e);
			return null;
		}
	}

	/*Возвращает Map всех параметров URL-а*/
	public Map<String, String> getDecodedParametersMap()
	{
		Map<String, String> decodedParameters = new HashMap<String, String>();
		try
		{
			for (Iterator<String> iterator = parametersMap.keySet().iterator(); iterator.hasNext();)
			{
				String key = iterator.next();
				String value = parametersMap.get(key);
				decodedParameters.put(key,value == null ? null : URLDecoder.decode(value,encoding));
			}
		}
		catch (UnsupportedEncodingException e)
		{
			// check encoding in setEncoding
			log.error(e.getMessage(), e);
		}
		return decodedParameters;
	}

	public String toString()
	{
		return getUrl();
	}
}