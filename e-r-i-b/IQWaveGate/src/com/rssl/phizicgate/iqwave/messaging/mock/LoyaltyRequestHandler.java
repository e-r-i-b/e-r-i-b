package com.rssl.phizicgate.iqwave.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.loyalty.LoyaltyProgramRequestHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Random;

/**
 * @author gladishev
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyRequestHandler extends IQWFilenameRequestHandler
{
	public LoyaltyRequestHandler(String filename) throws GateException
	{
		super(filename);
	}


	public Document proccessRequest(Document request) throws GateException
	{
		Document document = super.proccessRequest(request);
		Element root = document.getDocumentElement();
		try
		{
			Element requestRoot = request.getDocumentElement();
			String hash = LoyaltyProgramRequestHelper.generateHash(XmlHelper.getSimpleElementValue(requestRoot, Constants.PAN_TAG_NAME));

			Element hashElement = XmlHelper.selectSingleNode(root, "//" + Constants.HASH_TAG_NAME);
			hashElement.setTextContent(hash.substring(0, 10));

			Element amountElement = XmlHelper.selectSingleNode(root, "//" + Constants.LOY_BNS_TAG_NAME);
			if (amountElement != null)
				amountElement.setTextContent(Integer.toString(new Random().nextInt(99999999)));

			/** добавление ошибки в ответ
			Element errorCode = XmlHelper.selectSingleNode(root, "//Code");
			errorCode.setTextContent("10");
			Element body = XmlHelper.selectSingleNode(root, Constants.BODY_TAG);
			XmlHelper.appendSimpleElement(body, Constants.DMESSAGE_TAG_NAME, "Текстовое сообщение для разработчика");
//			XmlHelper.appendSimpleElement(body, Constants.CMESSAGE_TAG_NAME, "Текстовое сообщение для клиента");
//			XmlHelper.appendSimpleElement(body, Constants.OMESSAGE_TAG_NAME, "Текстовое сообщение для оператора");
//			XmlHelper.appendSimpleElement(body, Constants.EMESSAGE_TAG_NAME, "Текстовое сообщение об ошибке");
			*/

			return document;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
