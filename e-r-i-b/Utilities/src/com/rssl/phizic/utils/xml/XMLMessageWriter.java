package com.rssl.phizic.utils.xml;

/**
 * @author Erkin
 * @ created 19.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Специализация XMLStringWriter для формирования XML-сообщений обмена между системами
 */
public class XMLMessageWriter extends XMLStringWriter
{
	/**
	 * ctor
	 * @param encoding - кодировка
	 */
	public XMLMessageWriter(String encoding)
	{
		super(XMLOutputFormats.getCompactXMLFormat(encoding));
	}
}
