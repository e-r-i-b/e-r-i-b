package com.rssl.auth.csa.wsclient.requests.verification;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на инициацию процесса верификации данных в деловой среде
 */

public class InitializeVerifyBusinessEnvironmentRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "initializeVerifyBusinessEnvironmentRq";
	
	private final String authToken;
	private final String clientExternalId;
	private final ConfirmStrategyType confirmType;
	private final String cardNumber;

	/**
	 * конструктор
	 * @param authToken токен аутнтификации
	 * @param clientExternalId внешний чдентификатор клиента
	 * @param confirmType тип подтверждения
	 * @param cardNumber номер карты подтверждения
	 */
	public InitializeVerifyBusinessEnvironmentRequestData(String authToken, String clientExternalId, ConfirmStrategyType confirmType, String cardNumber)
	{
		this.authToken = authToken;
		this.clientExternalId = clientExternalId;
		this.confirmType = confirmType;
		this.cardNumber = cardNumber;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, RequestConstants.AUTH_TOKEN_TAG,          authToken));
		root.appendChild(createTag(request, RequestConstants.EXTERNAL_ID_PARAM_NAME,  clientExternalId));
		root.appendChild(createTag(request, RequestConstants.CONFIRMATION_PARAM_NAME, confirmType.name()));
		root.appendChild(createTag(request, RequestConstants.CARD_NUMBER_PARAM_NAME,  cardNumber));

		return request;
	}
}
