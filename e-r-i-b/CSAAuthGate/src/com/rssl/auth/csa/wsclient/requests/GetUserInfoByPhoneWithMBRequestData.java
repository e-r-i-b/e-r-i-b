package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение информации о пользователе по номеру телефона с запросом в МБК,
 * если данных ЦСА не окажется достаточных
 */
public class GetUserInfoByPhoneWithMBRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "getUserInfoByPhoneWithMBRq";

	private String phoneNumber;

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 */
	public GetUserInfoByPhoneWithMBRequestData(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.PHONE_NUMBER_PARAM_NAME, phoneNumber);

		return document;
	}
}
