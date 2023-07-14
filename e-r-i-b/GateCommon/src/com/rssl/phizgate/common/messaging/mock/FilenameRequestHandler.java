package com.rssl.phizgate.common.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 29.05.2010
 * @ $Author$
 * @ $Revision$
 * Хендлер возвращающий возвращающий в качестве ответа содежимое файла,
 * имя которого переданно в кострукторе
 */
public class FilenameRequestHandler implements MockRequestHandler
{
	private Document document;

	public FilenameRequestHandler(String filename) throws GateException
	{
		try
		{
			document = XmlHelper.loadDocumentFromResource(filename);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Document proccessRequest(Document request) throws GateException
	{
		return (Document) document.cloneNode(true);
	}
}
