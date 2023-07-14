package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.operations.GetConfirmationInfoOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author akrenev
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение данных о способах подтверждения операций клиентом
 *
 * Пармерты запроса:
 * authToken		       токен аутентификации. 	[1]
 *
 * Параметры ответа:
 * OUID                         идентификатор операции                                      [1]
 * сonfirmationInfo             информация о способах подтверждения операций клиентом       [1]
 *      preferredConfirmType    предпочтительный способ подтверждения                       [1]
 *      cardConfirmationSource  информация о картах для подтверждения                       [0..1]
 *          cardNumber          номер карты                                                 [1..n]
 */

public class GetConfirmationInfoRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "getConfirmationInfoRq";
	public static final String RESPONCE_TYPE = "getConfirmationInfoRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String authToken = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.AUTH_TOKEN_TAG);
		return getConfirmationInfo(authToken);
	}

	private ResponseInfo getConfirmationInfo(String authToken) throws Exception
	{
		//собираем контекст
		final IdentificationContext identificationContext = IdentificationContext.createByAuthToken(authToken);
		//проверяем контекст на валидность
		identificationContext.checkAuthToken(authToken);
		//делаем свои дела
		GetConfirmationInfoOperation operation = new GetConfirmationInfoOperation(identificationContext);
		operation.initialize();
		return getSuccessResponseBuilder().addOUID(operation).addConfirmationInfo(operation).end().getResponceInfo();
	}
}
