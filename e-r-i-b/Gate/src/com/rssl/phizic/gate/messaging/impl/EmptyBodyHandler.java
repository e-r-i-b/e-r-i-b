package com.rssl.phizic.gate.messaging.impl;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author Evgrafov
 * @ created 01.09.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3724 $
 */

public class EmptyBodyHandler extends DefaultHandler
{
	private int tags = 0;

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		tags++;
		if(tags > 1)
			throw new SAXException("Body MUST be empty");
	}
}