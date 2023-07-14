package com.rssl.phizic.business.forms.expressions.xpath;

import java.util.Iterator;
import javax.xml.XMLConstants;


/**
 * @author Krenev
 * @ created 18.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class NamespaceContext implements javax.xml.namespace.NamespaceContext
{
	public static final String PHIZIC_PREFIX = "phiz";
	public static final String PHIZIC_URI = "http://softlab.ru";

	/**
	 * Return the namespace uri for a given prefix
	 */
	public String getNamespaceURI(String prefix)
	{
		if (prefix == null)
			throw new IllegalArgumentException();

		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
			return XMLConstants.NULL_NS_URI;
		else if (prefix.equals(XMLConstants.XML_NS_PREFIX))
			return XMLConstants.XML_NS_URI;
		else if (prefix.equals(XMLConstants.XMLNS_ATTRIBUTE))
			return XMLConstants.XMLNS_ATTRIBUTE_NS_URI;
		else if (prefix.equals(PHIZIC_PREFIX))
			return PHIZIC_URI;
		else
			return XMLConstants.NULL_NS_URI;
	}

	/**
	 * Return the prefix for a given namespace uri.
	 */
	public String getPrefix(String namespace)
	{
		if (namespace == null)
			throw new IllegalArgumentException();

		if (namespace.equals(XMLConstants.XML_NS_URI))
			return XMLConstants.XML_NS_PREFIX;
		else if (namespace.equals(XMLConstants.XMLNS_ATTRIBUTE_NS_URI))
			return XMLConstants.XMLNS_ATTRIBUTE;
		else if (namespace.equals(PHIZIC_URI))
			return PHIZIC_PREFIX;
		else
			return null;
	}

	public Iterator getPrefixes(String namespace)
	{
		final String result = getPrefix(namespace);

		return new Iterator()
		{

			private boolean isFirstIteration = (result != null);

			public boolean hasNext()
			{
				return isFirstIteration;
			}

			public Object next()
			{
				if (isFirstIteration)
				{
					isFirstIteration = false;
					return result;
				}
				else
					return null;
			}

			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
}
