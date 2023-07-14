package com.rssl.auth.csa.back.protocol.handlers.profile;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.ResponseBuilderHelper;
import com.rssl.auth.csa.back.servises.Profile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author mihaylov
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор запроса поиска номеров карт профиля
 */
public class FindProfileCardNumberListRequestProcessor extends FindOrCreateProfileInformationRequestProcessorBase
{
	private static final String REQUEST_TYPE  = "findProfileCardNumberListRq";
	private static final String RESPONSE_TYPE = "findProfileCardNumberListRs";

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
		Profile profileTemplate = fillProfileTemplate(element);
		List<String> cardNumberList = Profile.getCardNumberList(profileTemplate);
		ResponseBuilderHelper responseBuilder = getSuccessResponseBuilder();
		responseBuilder.openTag(Constants.CARD_NUMBER_LIST_TAG);
		for(String cardNumber: cardNumberList)
		{
			responseBuilder.addParameter(Constants.CARD_NUMBER_TAG, cardNumber);
		}
		responseBuilder.closeTag();
		return responseBuilder.end().getResponceInfo();
	}
}
