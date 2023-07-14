package com.rssl.phizic.operations.fund.response;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.messaging.push.FundPushHelper;

/**
 * @author osminin
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция отклонения входящего запроса на сбор средств
 */
public class SetRejectStateFundOperation extends ChangeStateFundOperation
{
	private static final FundResponseState STATE = FundResponseState.REJECT;

	@Override
	public String getStateMethodName()
	{
		return STATE.name().toLowerCase();
	}

	@Override
	public void execute() throws BusinessException, BusinessLogicException
	{
		if (FundResponseState.READ != getResponse().getState())
		{
			throw new BusinessException("Операция чтения не может быть выполнена для статуса " + getResponse().getState().getDescription());
		}
		if (updateState() == 1)
		{
			updateResponse();
			updateInitiatorState();
			sendPush();
		}
	}

	private int updateState() throws BusinessException
	{
		return fundSenderResponseService.setRejectState(getResponse().getExternalId(), getMessage());
	}

	private void updateResponse()
	{
		getResponse().setState(STATE);
		getResponse().setMessage(getMessage());
	}

	private void updateInitiatorState()
	{
		try
		{
			FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			fundMultiNodeService.updateResponseInfo(getResponse());
		}
		catch (GateException e)
		{
			log.error("Ошибка при обновлении статуса ответа с externalId " + getResponse().getExternalId() + " в блоке инициатора.", e);
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void sendPush()
	{
		try
		{
			FundPushHelper.sendRejectPush(getResponse());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при отправке пуш-уведомления по отклонению входящего запроса с externalId " + getResponse().getExternalId(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
