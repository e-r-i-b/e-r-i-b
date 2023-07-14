package com.rssl.phizic.utils.xml;

import org.dom4j.io.OutputFormat;

/**
 * @author Erkin
 * @ created 19.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сборник стандартов форматирования XML-документов
 */
public class XMLOutputFormats
{
	/**
	 * @param encoding - кодировка
	 * @return формат сжатого НЕпрезентабельного XML-документа в заданной кодировке
	 */
	public static OutputFormat getCompactXMLFormat(String encoding)
	{
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		outputFormat.setEncoding(encoding);
		return outputFormat;
	}

	/**
	 * @param encoding - кодировка
	 * @return формат НЕсжатого презентабельного XML-документа в заданной кодировке
	 */
	public static OutputFormat getPrettyXMLFormat(String encoding)
	{
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setEncoding(encoding);
		return outputFormat;
	}
}
