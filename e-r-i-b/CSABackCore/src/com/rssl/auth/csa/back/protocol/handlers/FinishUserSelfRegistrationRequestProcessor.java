package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserSelfRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на завершение самостоятельной регистрации, отличается от родителя только операцией 
 */
public class FinishUserSelfRegistrationRequestProcessor extends FinishUserRegistrationRequestProcessor
{
	private static final String REQUEST_TYPE =  "finishUserSelfRegistrationRq";
	private static final String RESPONCE_TYPE = "finishUserSelfRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected UserRegistrationOperation findOperation(String ouid, final IdentificationContext identificationContext) throws Exception
	{
		return identificationContext.findOperation(UserSelfRegistrationOperation.class, ouid, UserSelfRegistrationOperation.getLifeTime());
	}

	protected void doFraudControl(UserRegistrationOperation operation, RequestInfo requestInfo) throws ProhibitionOperationFraudGateException {}
}
