package com.rssl.auth.csa.back.protocol.handlers.verification;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.handlers.LogableProcessorBase;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.verification.VerifyBusinessEnvironmentOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InitializeVerifyBusinessEnvironmentRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "initializeVerifyBusinessEnvironmentRq";
	public static final String RESPONCE_TYPE = "initializeVerifyBusinessEnvironmentRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		String clientExternalId = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.EXTERNAL_ID_PARAM_NAME);
		String confirmationType = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CONFIRMATION_PARAM_NAME);
		String authToken        = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.AUTH_TOKEN_TAG);
		String cardNumber       = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.CARD_NUMBER_TAG);
		return initializeVerifyBusinessEnvironmentOperation(context, authToken, clientExternalId, confirmationType, cardNumber);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String authToken = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.AUTH_TOKEN_TAG);
		return LogIdentificationContext.createByAuthToken(authToken);
	}

	private LogableResponseInfo initializeVerifyBusinessEnvironmentOperation(final IdentificationContext identificationContext, String authToken, String clientExternalId, String confirmationType, String cardNumber) throws Exception
	{
		//проверяем контекст на валидность
		identificationContext.checkAuthToken(authToken);
		//делаем свои дела
		VerifyBusinessEnvironmentOperation operation = new VerifyBusinessEnvironmentOperation(identificationContext);
		operation.initialize(clientExternalId, ConfirmStrategyType.valueOf(confirmationType), cardNumber);
		return new LogableResponseInfo(getSuccessResponseBuilder().addOUID(operation).addConfirmParameters(operation).end().getResponceInfo());
	}
}
