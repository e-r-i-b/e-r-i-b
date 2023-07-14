package com.rssl.phizic.utils.xml;

import org.w3c.dom.Document;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1315 $
 */

public class StaticResolver implements URIResolver
{
	private static String INCLUDE_ROOT = "com/rssl/phizic/utils/xml/include";

	public Source resolve(String href, String base) throws TransformerException
	{
		Source result = null;
		Document document = null;

		String resourcePath = getResourcePath(href);
		if(resourcePath != null)
		{
			document = loadDocument(resourcePath);
		}

		if(document != null)
		{
			result = new DOMSource(document);
		}

		return result;
	}

	private Document loadDocument(String resourcePath) throws TransformerException
	{
		Document document = null;

		try
		{
			document = XmlHelper.loadDocumentFromResource(resourcePath);
		}
		catch (FileNotFoundException e)
		{
			return null;
		}
		catch (Exception e)
		{
			throw new TransformerException(e);
		}

		return document;
	}

	private String getResourcePath(String href)
	{
		try
		{
			URI uri = new URI(href);
			if(!"static".equals(uri.getScheme()))
				return null;
			if(!"include".equals(uri.getHost()))
				return null;

			return INCLUDE_ROOT + uri.getPath();
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException(e);
		}

	}
}