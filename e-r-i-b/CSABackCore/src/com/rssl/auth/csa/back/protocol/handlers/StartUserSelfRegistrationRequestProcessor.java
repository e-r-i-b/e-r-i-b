package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserSelfRegistrationOperation;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * @author osminin
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 *  ���������� ������� �� ������ ��������������� �����������, ���������� �� �������� ������ ���������
 */
public class StartUserSelfRegistrationRequestProcessor extends StartUserRegistrationRequestProcessor
{
	private static final String REQUEST_TYPE =  "startUserSelfRegistrationRq";
	private static final String RESPONCE_TYPE = "startUserSelfRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected UserRegistrationOperation createRegistrationOperation(IdentificationContext identificationContext, String cardNumber, String confirmStrategyType) throws Exception
	{
		UserRegistrationOperation operation = new UserSelfRegistrationOperation(identificationContext);
		operation.initialize(cardNumber, ConfirmStrategyType.valueOf(confirmStrategyType));
		return operation;
	}
}
