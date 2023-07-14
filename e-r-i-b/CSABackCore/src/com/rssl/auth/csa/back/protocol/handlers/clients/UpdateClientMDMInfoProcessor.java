package com.rssl.auth.csa.back.protocol.handlers.clients;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author komarov
 * @ created 13.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запросов на обновление профиля данными из мдм
 */
public class UpdateClientMDMInfoProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE  = "updateClientMDMInfoRq";
	private static final String RESPONSE_TYPE = "updateClientMDMInfoRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
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
		Long profileId = Long.parseLong(XmlHelper.getSimpleElementValue(element, Constants.PROFILE_ID_TAG));
		String mdmId =   XmlHelper.getSimpleElementValue(element, Constants.MDM_ID_TAG);
		Profile.updateClientMDMInfo(profileId, mdmId);
		return buildSuccessResponse();
	}
}
