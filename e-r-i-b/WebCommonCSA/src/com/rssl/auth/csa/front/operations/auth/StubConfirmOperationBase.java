package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.*;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Операция-заглушка для подтверждения
 * @author niculichev
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class StubConfirmOperationBase extends InterchangeCSABackOperationBase
{
	public static final String TIME_RECEIVE_ANSWER_CONFIRM_PARAM_NAME = "timeReceiveAnswer";
	private OperationInfo info;

	public StubConfirmOperationBase(OperationInfo info)
	{
		this.info = info;
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		Map<String, Object> confirmParams = info.getConfirmParams();
		Long attempts = (Long) confirmParams.get(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME);
		Long timeout = (Long) confirmParams.get(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME);
		Long currentMilliseconds = (Long) confirmParams.get(TIME_RECEIVE_ANSWER_CONFIRM_PARAM_NAME);

		if(confirmParams.isEmpty())
			throw new IllegalOperationStateException();

		long lastSecond = (System.currentTimeMillis() - currentMilliseconds)/1000;

		if(attempts <= 1)
		{
			confirmParams.clear();
			throw new IllegalOperationStateByInvalidCodeException();
		}

		if(lastSecond >= timeout)
		{
			confirmParams.clear();
			throw new IllegalOperationStateException();
		}

		// уменьшаем время и попытки
		confirmParams.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME, attempts-1);
		confirmParams.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME, timeout - lastSecond);

		throw new InvalidCodeConfirmException(ConfirmStrategyType.sms, attempts-1, timeout - lastSecond);
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
	}

	protected OperationInfo getInfo()
	{
		return info;
	}
}
