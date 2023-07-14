package com.rssl.phizic.operations.fund.response;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.source.NewFundPaymentSource;
import com.rssl.phizic.business.fund.ClosedFundRequestException;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.gate.fund.RequestInfo;

/**
 * @author osminin
 * @ created 19.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Операция удовлетворения входящего запроса на сбор средств
 */
public class SetSuccessStateFundOperation extends ChangeStateFundOperation
{
	@Override
	public String getStateMethodName()
	{
		return FundResponseState.SUCCESS.name().toLowerCase();
	}

	@Override
	public void execute() throws BusinessException, BusinessLogicException
	{
		if (FundResponseState.READ != getResponse().getState())
		{
			throw new BusinessException("Операция удовлетворения не может быть выполнена для статуса " + getResponse().getState().getDescription());
		}
		if (!getCloseConfirm() && isFundRequestIsClosed())
		{
			throw new ClosedFundRequestException("Денег набралось достаточно. Все равно отправить?");
		}

		initialize(new NewFundPaymentSource(getResponse(), getSum(), getMessage(), getFromResource(), !getCloseConfirm()));
	}

	private boolean isFundRequestIsClosed() throws BusinessLogicException, BusinessException
	{
		return isFundRequestStateActuallyClosed(getResponse().getViewRequestState())
				|| isInitiatorRequestClosed();
	}

	private boolean isInitiatorRequestClosed() throws BusinessException, BusinessLogicException
	{
		try
		{
			FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			RequestInfo requestInfo = fundMultiNodeService.getRequestInfo(getResponse().getExternalId());

			if (requestInfo == null || requestInfo.getState() == null)
			{
				throw new BusinessException("Запрос на сбор средств по идентификатору ответа " + getResponse().getExternalId() + " не найден.");
			}

			return isFundRequestStateActuallyClosed(requestInfo.getState());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e.getMessage(), e);
		}
	}

	private boolean isFundRequestStateActuallyClosed(FundRequestState state)
	{
		return FundRequestState.CLOSED == state || FundRequestState.SYNC_CLOSED == state;
	}
}
