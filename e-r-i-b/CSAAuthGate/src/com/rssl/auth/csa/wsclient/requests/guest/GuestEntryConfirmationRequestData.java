package com.rssl.auth.csa.wsclient.requests.guest;

import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PASSWORD_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.PHONE_NUMBER_PARAM_NAME;

/**
 * @author tisov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Данные запроса по подтверждению смс-пароля
 */
public class GuestEntryConfirmationRequestData extends RequestDataBase
{
	private String password;      //смс-пароль
	private String ouid;          //идентификатор операции

	public GuestEntryConfirmationRequestData(String ouid,String password)
	{
		this.ouid = ouid;
		this.password = password;
	}

	public String getName()
	{
		return "guestEntryConfirmationRq";
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));
		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));

		return request;
	}
}
