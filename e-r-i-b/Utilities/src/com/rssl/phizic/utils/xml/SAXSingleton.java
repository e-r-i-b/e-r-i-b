package com.rssl.phizic.utils.xml;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;

import javax.xml.parsers.SAXParserFactory;

/**
 * @author Erkin
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class SAXSingleton
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final SAXParserFactory parserFactory;

	static
	{
		try
		{
			parserFactory = SAXParserFactory.newInstance();
		}
		catch (RuntimeException e)
		{
			log.error("Не удалось создать SAX-фабрику", e);
			throw e;
		}
	}

	/**
	 * @return Фабрика SAX-парсера
	 */
	public static SAXParserFactory getParserFactory()
	{
		return parserFactory;
	}
}
