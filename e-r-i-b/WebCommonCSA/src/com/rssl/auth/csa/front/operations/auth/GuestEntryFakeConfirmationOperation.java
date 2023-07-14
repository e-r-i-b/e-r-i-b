package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.*;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.auth.csa.wsclient.exceptions.IllegalOperationStateByInvalidCodeException;
import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author tisov
 * @ created 26.12.14
 * @ $Author$
 * @ $Revision$
 * [�������� ����] �������� ������� ������������� (������ ��������)
 */
public class GuestEntryFakeConfirmationOperation implements Operation
{
	private static final String MESSAGE = "����� �������� ������. ���������� ��� ���";

	GuestEntryOperationInfo operationInfo;

	public GuestEntryFakeConfirmationOperation(GuestEntryOperationInfo operationInfo)
	{
		this.operationInfo = operationInfo;
	}

	public void execute() throws FrontLogicException, FrontException
	{
		if (operationInfo.getConfirmAttemptsCount() > 0)
		{
			operationInfo.decreaseConfirmAttempts();
			throw new InvalidCodeConfirmException(MESSAGE);
		}
		throw new SmsWasNotConfirmedInterruptStageException();
	}
}
