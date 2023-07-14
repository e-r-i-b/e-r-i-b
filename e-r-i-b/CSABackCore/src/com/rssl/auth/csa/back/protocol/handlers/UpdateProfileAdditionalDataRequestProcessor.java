package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на обновление доп. параметров профиля
 */

public class UpdateProfileAdditionalDataRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE  = "updateProfileAdditionalDataRq";
	private static final String RESPONSE_TYPE = "updateProfileAdditionalDataRs";

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
		Element clientAdditionalData = XmlHelper.selectSingleNode(element, Constants.CLIENT_ADDITIONAL_DATA_TAG);

		String phone = XmlHelper.getSimpleElementValue(clientAdditionalData, Constants.PHONE_NUMBER_TAG);
		String agreementNumber = XmlHelper.getSimpleElementValue(clientAdditionalData, Constants.AGREEMENT_NUMBER_TAG);
		String creationType = XmlHelper.getSimpleElementValue(clientAdditionalData, Constants.CREATION_TYPE_TAG);

		CSAUserInfo userInfo = fillUserInfo(userInfoElement);

		Profile profile = Profile.getByUserInfo(userInfo, true);
		if (profile == null)
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);

		profile.setCreationType(StringHelper.isEmpty(creationType) ? null : CreationType.valueOf(creationType));
		profile.setAgreementNumber(agreementNumber);
		profile.setPhone(phone);

		profile.save();

		return buildSuccessResponse();
	}
}
