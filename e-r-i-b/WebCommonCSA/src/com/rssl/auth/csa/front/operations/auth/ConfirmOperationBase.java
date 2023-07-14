package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author niculichev
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmOperationBase extends InterchangeCSABackOperationBase
{
	protected OperationInfo info;
	protected String confirmationCode;

	public void initialize(OperationInfo info, String operationCode)
	{
		this.info = info;
		this.confirmationCode = operationCode;
	}

	protected Document doConfirm() throws BackLogicException, BackException
	{
		return CSABackRequestHelper.sendConfirmOperationRq(info.getOUID(), confirmationCode);
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		try
		{
			return doConfirm();
		}
		catch (InvalidCodeConfirmException e)
		{
			// заранее можем сказать что операция будет не активна при следующей проверке кода
			if(e.getAttempts() <= 0)
			{
				resetConfirmParams();
				throw new IllegalOperationStateByInvalidCodeException(e);
			}

			// код неверный, обновляем параметры подтверждения
			updateConfirmParams(e.getAttempts(), e.getTime());
			throw e;
		}
		catch (IllegalOperationStateException e)
		{
			resetConfirmParams();
			throw e;
		}
	}

	protected void updateConfirmParams(Long attempts, Long time)
	{
		Map<String, Object> confirmParams = info.getConfirmParams();
		confirmParams.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, attempts);
		confirmParams.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME, time);
	}

	protected void resetConfirmParams()
	{
		Map<String, Object> confirmParams = info.getConfirmParams();
		confirmParams.remove(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME);
		confirmParams.remove(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME);
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
	}
}
