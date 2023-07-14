package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import com.rssl.phizgate.common.messaging.mock.FilenameSelectorRequesHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * ќбработчик запроса списка организаци€-получателей(recipientListDemand_q).
 * User: Omeliyanchuk
 * Date: 22.09.2006
 * Time: 17:36:19
 */
public class RecipientListDemandHandler extends MockRequestHandlerBase
{
	private MockRequestHandler handler;
	private Document currentDoc;
	private long counter = 0;
	private MockRequestHandler bug030027Handler;

	public RecipientListDemandHandler() throws GateException
	{
		handler = new FilenameSelectorRequesHandler
				(
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/recipientList_a_0.xml",
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/recipientList_a_1.xml",
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/recipientList_a_5.xml",
						"com/rssl/phizicgate/cpfl/messaging/examples/xml/recipientList_a_BUG030027.xml"
				);
		bug030027Handler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/recipientList_a_BUG030027.xml");
	}

	public synchronized Document doProcessRequest(Document request) throws GateException
	{
		//посылаем подр€д 4 одинаковых ответа
		try
		{
			if (counter % 4 == 0)
			{
				String bic = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/message/recipientListDemand_q/bankBIC");
				if ("044583001".equals(bic))
				{
					return bug030027Handler.proccessRequest(request);
				}
				currentDoc = handler.proccessRequest(request);
			}
			counter++;
			return (Document) currentDoc.cloneNode(true);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}
