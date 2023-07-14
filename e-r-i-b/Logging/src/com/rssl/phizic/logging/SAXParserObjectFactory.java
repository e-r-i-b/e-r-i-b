package com.rssl.phizic.logging;

import org.apache.commons.pool.KeyedPoolableObjectFactory;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

/**
 * @author Barinov
 * @ created 20.01.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика парсеров для пула.
 */
class SAXParserObjectFactory implements KeyedPoolableObjectFactory
{
	private final SAXParserFactory parserFactory = SAXParserFactory.newInstance();

	public Object makeObject(Object key) throws Exception
	{
		return parserFactory.newSAXParser();
	}

	public void destroyObject(Object key, Object obj) {}

	public boolean validateObject(Object key, Object obj)
	{
		return true;
	}

	public void activateObject(Object key, Object obj)
	{
		((SAXParser) obj).reset();
	}

	public void passivateObject(Object key, Object obj) {}
}
