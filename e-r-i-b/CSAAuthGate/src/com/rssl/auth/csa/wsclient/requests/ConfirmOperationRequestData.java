package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.CONFIRM_CODE_PARAM_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmOperationRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "confirmOperationRq";

	private String ouid;
	private String confirmationCode;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public ConfirmOperationRequestData(String ouid, String confirmationCode)
	{
		this.ouid = ouid;
		this.confirmationCode = confirmationCode;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, CONFIRM_CODE_PARAM_NAME, confirmationCode));

		return request;
	}
}
