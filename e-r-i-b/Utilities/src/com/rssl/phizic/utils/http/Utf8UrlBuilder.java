package com.rssl.phizic.utils.http;

import java.io.UnsupportedEncodingException;

/**
 * @author Erkin
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class Utf8UrlBuilder extends UrlBuilder
{
	private static final String URL_ENCODING = "utf-8";

	public Utf8UrlBuilder()
	{
		setUrlEncoding();
	}

	public Utf8UrlBuilder(String url)
	{
		super(url);

		setUrlEncoding();
	}

	private void setUrlEncoding()
	{
		try
		{
			setEncoding(URL_ENCODING);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("Кодировка " + URL_ENCODING + " не найдена в системе", e);
		}
	}
}
