package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.limits.exceptions.UnknownRequestException;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор неизвестных запросов
 */
public class UnknownRequestProcessor implements TransactionProcessor
{
	private static final String ERROR_MESSAGE = "Неизвестный тип запроса: ";

	public void process(Document request) throws Exception
	{
		throw new UnknownRequestException(ERROR_MESSAGE + request.getDocumentElement().getTagName());
	}
}
