package com.rssl.auth.csa.front.operations.auth.verification;

import com.rssl.auth.csa.front.operations.auth.ConfirmOperationBase;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.*;
import org.w3c.dom.Document;

/**
 * Операция верификации данных в деловой среде
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
		//посылаем запрос на подтверждение
		try
		{
			doConfirm();
		}
		catch (InvalidCodeConfirmException e)
		{
			// код неверный, обновляем параметры подтверждения
			updateConfirmParams(e.getAttempts(), e.getTime());
			// заранее можем сказать что операция будет не активна при следующей проверке кода
			if(e.getAttempts() <= 0)
				throw new NoMoreAttemptsCodeConfirmException(e);

			throw e;
		}
		//все хорошо, начинаем верификацию
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
