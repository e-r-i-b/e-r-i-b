package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение информации о пользователе по номеру телефона
 */
public class GetUserInfoByPhoneRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "getUserInfoByPhoneRq";

	private String phoneNumber;
	private boolean usingCardByPhone;

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 * @param usingCardByPhone использовать ли карту, найденную по телефону в МБ для получения информации
	 */
	public GetUserInfoByPhoneRequestData(String phoneNumber, boolean usingCardByPhone)
	{
		this.phoneNumber = phoneNumber;
		this.usingCardByPhone = usingCardByPhone;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element element = document.getDocumentElement();

		XmlHelper.appendSimpleElement(element, RequestConstants.PHONE_NUMBER_PARAM_NAME, phoneNumber);
		XmlHelper.appendSimpleElement(element, RequestConstants.USING_CARD_BY_PHONE_PARAM_NAME, String.valueOf(usingCardByPhone));

		return document;
	}
}
