package com.rssl.auth.csa.wsclient.requests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.CARD_NUMBER_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.SEND_SMS_PARAM_NAME;

/**
 * Запрос на получение одноразового логина и пароля через УС
 * @author Jatsky
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public class UserRegistrationDisposableRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "userRegistrationDisposableRq";

	private String cardNum;
	private String sendSMS;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public UserRegistrationDisposableRequestData(String cardNum, String sendSMS)
	{
		this.cardNum = cardNum;
		this.sendSMS = sendSMS;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, CARD_NUMBER_PARAM_NAME, cardNum));
		root.appendChild(createTag(request, SEND_SMS_PARAM_NAME, sendSMS));

		return request;
	}
}

