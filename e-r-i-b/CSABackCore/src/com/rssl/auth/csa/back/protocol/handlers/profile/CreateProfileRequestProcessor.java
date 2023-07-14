package com.rssl.auth.csa.back.protocol.handlers.profile;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.common.types.security.SecurityType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author mihaylov
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 */
public class CreateProfileRequestProcessor extends FindOrCreateProfileInformationRequestProcessorBase
{
	private static final String REQUEST_TYPE  = "createProfileRq";
	private static final String RESPONSE_TYPE = "createProfileRs";

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
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
		Profile newProfile = fillProfileTemplate(element);

		//Сначала попробуем поискать профиль клиента.
		Profile profile = findProfileByTemplate(newProfile);
		if(profile != null)
		{
			return fillProfileWithHistoryResponseInfo(profile);
		}

		//Если профиль не найден, то сохраняем в качестве нового профиля данные по которым искали
		Profile savedProfile = Profile.create(newProfile.getFirstname(), newProfile.getPatrname(), newProfile.getSurname(), newProfile.getBirthdate(), newProfile.getPassport(), newProfile.getTb(), SecurityType.MIDDLE);
		ProfileNode profileNode = ProfileNode.create(savedProfile);

		ResponseBuilderHelper responseBuilder = getSuccessResponseBuilder();
		addProfileInformation(responseBuilder, savedProfile);
		responseBuilder.openTag(Constants.NODE_INFO_TAG)
			.addParameter(Constants.NODE_ID_TAG, profileNode.getNode().getId());
		responseBuilder.closeTag();
		return responseBuilder.end().getResponceInfo();
	}
}
