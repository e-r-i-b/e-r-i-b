package com.rssl.auth.csa.wsclient.requests.guest;

import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.CLAIM_TYPE_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PHONE_NUMBER_PARAM_NAME;

/**
 * @author tisov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Данные для первоначального запроса в ЦСА-Back
 */
public class GuestEntryInitialRequestData extends RequestDataBase
{
	private String phoneNumber;

	public GuestEntryInitialRequestData(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getName()
	{
		return "guestEntryInitialRq";
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, PHONE_NUMBER_PARAM_NAME, phoneNumber));

		return request;
	}
}
