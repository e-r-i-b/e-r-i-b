package com.rssl.phizic.business.payments.forms;

import javax.xml.transform.URIResolver;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 20.02.2006
 * @ $Author: erkin $
 * @ $Revision: 69392 $
 */

public class CompositeURIResolver implements URIResolver
{
	private URIResolver[] resolvers;

	public CompositeURIResolver(URIResolver... resolvers)
	{
		if(resolvers == null)
			throw new NullPointerException("resolvers can't be null");
		this.resolvers = resolvers;
	}

	public Source resolve(String href, String base) throws TransformerException
	{
		Source source = null;
		for (int i = 0; i < resolvers.length; i++)
		{
			URIResolver resolver = resolvers[i];
			source = resolver.resolve(href, base);
			if(source != null)
			{
				break;
			}
		}
		return source;
	}
}