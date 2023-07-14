package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 *
 * запрос на начало входа пользователя через АТМ
 */
public class StartCreateATMSessionRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "startCreateATMSessionRq";

	private String cardNumber;

	/**
	 * ctor
	 * @param cardNumber номер карты
	 */
	public StartCreateATMSessionRequestData(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.CARD_NUMBER_PARAM_NAME, cardNumber);

		return document;
	}
}
