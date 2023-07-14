package com.rssl.auth.csa.back.protocol.handlers.profile;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author mihaylov
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */
public class FindProfileInformationRequestProcessor extends FindOrCreateProfileInformationRequestProcessorBase
{
	private static final String REQUEST_TYPE  = "findProfileInformationRq";
	private static final String RESPONSE_TYPE = "findProfileInformationRs";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
		Profile template = fillProfileTemplate(element);
		Profile profile = findProfileByTemplate(template);

		if(profile == null)
		{
			return getFailureResponseBuilder().buildProfileNotFoundResponse(template);
		}

		return fillProfileWithHistoryResponseInfo(profile);
	}
}
