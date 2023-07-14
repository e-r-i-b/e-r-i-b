package com.rssl.auth.csa.back.protocol.handlers.profile.lock;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на установку лока на профиль для исполнения документа сотрудником
 */

public class LockProfileForExecuteDocumentRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE  = "lockProfileForExecuteDocumentRq";
	private static final String RESPONSE_TYPE = "lockProfileForExecuteDocumentRs";

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
		Element userInfoElement = XmlHelper.selectSingleNode(element, Constants.USER_INFO_TAG);
		CSAUserInfo userInfo = fillUserInfo(userInfoElement);
		Profile profile = Profile.getByUserInfo(userInfo, true);
		ResponseBuilderHelper responseBuilder = getSuccessResponseBuilder();
		responseBuilder.addParameter("lock", profile.lockForExecuteDocument());
		return responseBuilder.end().getResponceInfo();
	}
}
