package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.ConfirmOperationBase;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import org.w3c.dom.Document;

/**
 * �������� ����������� ������ � ������� �����
 *
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmVerifyBusinessEnvironmentOperation extends ConfirmOperationBase
{
	@Override
	protected Document doRequest() throws BackLogicException, BackException
	{
		//�������� ������ �� �������������
		try
		{
			doConfirm();
		}
		catch (InvalidCodeConfirmException e)
		{
			// ��� ��������, ��������� ��������� �������������
			updateConfirmParams(e.getAttempts(), e.getTime());
			// ������� ����� ������� ��� �������� ����� �� ������� ��� ��������� �������� ����
			if(e.getAttempts() <= 0)
				throw new NoMoreAttemptsCodeConfirmException(e);

			throw e;
		}
		//��� ������, �������� �����������
		return doVerify();
	}

	private void setVerificationState(VerificationState verificationState)
	{
		((BusinessEnvironmentOperationInfo) info).setVerificationState(verificationState);
	}

	private Document doVerify() throws BackLogicException, BackException
	{
		try
		{
			Document result = CSABackRequestHelper.sendVerifyBusinessEnvironmentRq(info.getOUID());
			setVerificationState(VerificationState.SUCCESS);
			return result;
		}
		catch (FailVerifyBusinessEnvironmentException ignore)
		{
			setVerificationState(VerificationState.FAIL);
			return null;
		}
	}
}
