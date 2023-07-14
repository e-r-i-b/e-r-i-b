package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.OUID_PARAM_NAME;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class FinishCreateSessionRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "finishCreateSessionRq";

	private String ouid;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public FinishCreateSessionRequestData(String ouid)
	{
		this.ouid = ouid;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));

		return request;
	}
}
