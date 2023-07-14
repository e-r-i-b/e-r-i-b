package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 19.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение информации о блоке пользователя по телефону
 */
public class FindProfileNodeByPhoneRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "findProfileNodeByPhoneRq";

	private String phoneNumber;

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 */
	public FindProfileNodeByPhoneRequestData(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.PHONE_NUMBER_PARAM_NAME, phoneNumber);
		return document;
	}
}
