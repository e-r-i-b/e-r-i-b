package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import static com.rssl.auth.csa.wsclient.RequestConstants.CARD_NUMBER_PARAM_NAME;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author niculichev
 * @ created 29.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class StartUserRegistrationRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "startUserRegistrationRq";

	private String cardNumber;
	private ConfirmStrategyType confirmStrategyType;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public StartUserRegistrationRequestData(String cardNumber, ConfirmStrategyType confirmStrategyType)
	{
		this.cardNumber = cardNumber;
		this.confirmStrategyType = confirmStrategyType;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, CARD_NUMBER_PARAM_NAME, cardNumber));
		root.appendChild(createTag(request, RequestConstants.CONFIRMATION_PARAM_NAME, confirmStrategyType.name()));

		return request;
	}
}
