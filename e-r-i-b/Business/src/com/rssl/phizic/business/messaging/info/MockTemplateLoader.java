package com.rssl.phizic.business.messaging.info;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Evgrafov
 * @ created 05.07.2006
 * @ $Author: khudyakov $
 * @ $Revision: 32865 $
 */

public class MockTemplateLoader implements TemplateLoader
{

	public Object findTemplateSource(String name) throws IOException
	{
		return "";
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException
	{
		return new StringReader((String) templateSource);
	}

	public void closeTemplateSource(Object templateSource) throws IOException
	{
	}
}